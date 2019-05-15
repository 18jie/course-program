package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.mybatis.dao.GradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:15
 */
@Service
public class GradeService {

    @Autowired
    private GradeDao gradeDao;

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

}
