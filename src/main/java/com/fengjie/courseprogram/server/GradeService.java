package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.queryvo.StudentVO;
import com.fengjie.courseprogram.mybatis.dao.GradeDao;
import com.fengjie.courseprogram.util.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fengjie
 * @date 2019:05:15
 */
@Service
public class GradeService {

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private OpeartionService opeartionService;

    @Autowired
    private StudentService studentService;

    public Grade getGradeById(String gradeId) {
        return gradeDao.selectByPrimaryKey(gradeId);
    }

    public Grade getGradeByOperationAndStudent(String operationId, String studentId) {
        Grade grade = new Grade();
        grade.setOperationId(operationId);
        grade.setStudentId(studentId);
        List<Grade> grades = gradeDao.select(grade);
        return grades.size() == 0 ? null : grades.get(0);
    }

    public int saveGrade(Grade grade) {
        if (StringUtils.isEmpty(grade.getId())) {
            grade.setId(ObjectId.get().toString());
            return insertGrade(grade);
        }
        return updateGrade(grade);
    }

    private int insertGrade(Grade grade) {
        return gradeDao.insertSelective(grade);
    }

    private int updateGrade(Grade grade) {
        return gradeDao.updateByPrimaryKeySelective(grade);
    }

    public List<Operation> getAllUnfinishedOperations(String courseId) {
        return opeartionService.getAllUnfinishedOperations(courseId);
    }

    public List<Operation> getAllFinishedOperations(String courseId) {
        return opeartionService.getAllFinishedOperations(courseId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Async("asyncServiceExecutor")
    public void checkGrade(String operationId) {
        Grade grade = new Grade();
        grade.setOperationId(operationId);
        List<Grade> grades = gradeDao.select(grade);
        for (Grade gra : grades) {
            String[] split = gra.getAnswered().split(";");
            int total = 0;
            for (String s : split) {
                //下面分别是id和分数
                String[] split1 = s.split(",");
                total += Integer.parseInt(split1[1]);
            }
            gra.setGrade(total);
        }
        for (Grade gra : grades) {
            gradeDao.updateByPrimaryKeySelective(gra);
        }
        Operation operationById = opeartionService.getOperationById(operationId);
        operationById.setFinishedCondition(0);
        opeartionService.updateOperation(operationById);
    }

    public List<StudentVO> getallStudentOfOperation(String operationId) {
        Grade grade = new Grade();
        grade.setOperationId(operationId);
        List<Grade> grades = gradeDao.select(grade);
        Map<String, Integer> gradeMap = grades.stream()
                .collect(Collectors.toMap(Grade::getStudentId
                        , Grade::getGrade, (oldValue, newValue) -> newValue));

        Operation operation = opeartionService.getOperationById(operationId);
        List<Student> students = studentService.getStudentsByClassId(operation.getClassId());

        List<StudentVO> studentVOS = new ArrayList<>();
        students.forEach(stu -> {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(stu, studentVO);
            studentVO.setGrade(gradeMap.getOrDefault(stu.getId(), 0));
            studentVOS.add(studentVO);
        });
        return studentVOS;
    }

}
