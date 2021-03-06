package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.exceptions.BusinessException;
import com.fengjie.courseprogram.model.entity.*;
import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.model.param.StudentBatchAddParam;
import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.model.queryvo.CourseQuestionOperationVO;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.mybatis.dao.StudentDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.MD5Kit;
import com.fengjie.courseprogram.util.ObjectId;
import com.fengjie.courseprogram.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fengjie
 * @date 2019/5/6 14:29
 */
@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ClassService classService;

    @Autowired
    private OpeartionService opeartionService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private ProgramAnswerService programAnswerService;

    @Autowired
    private ProgramQuestionService programQuestionService;

    @Autowired
    private StudentAnswerService studentAnswerService;


    public Student loginCheck(LoginParam loginParam) {
        Student student = new Student();
        student.setEmail(loginParam.getEmail());
        if (studentDao.selectCount(student) == 0) {
            return null;
        }
        student.setPassword(MD5Kit.convertMD5(loginParam.getPassword()));
        return studentDao.selectOne(student);
    }

    public List<Student> getStudentsByClassId(String classId) {
        Student student = new Student();
        student.setClassId(classId);
        return studentDao.select(student);
    }

    public int deleteStudentsByClassId(String classId) {
        Student student = new Student();
        student.setClassId(classId);
        return studentDao.delete(student);
    }

    public Student getStudentById(String studentId) {
        return studentDao.selectByPrimaryKey(studentId);
    }

    public int updateStudentById(Student student) {
        if (!StringUtils.isEmpty(student.getPassword())) {
            student.setPassword(MD5Kit.convertMD5(student.getPassword()));
        }
        return studentDao.updateByPrimaryKeySelective(student);
    }

    public int deleteStudentById(String id) {
        return studentDao.deleteByPrimaryKey(id);
    }

    public int addStudent(Student student) {
        Example example = new Example(Student.class);
        example.createCriteria()
                .andEqualTo("studentCode", student.getStudentCode())
                .orEqualTo("email", student.getEmail());
        List<Student> students = studentDao.selectByExample(example);
        if (CollectionUtils.isNotEmpty(students)) {
            return 0;
        }
        student.setPassword(MD5Kit.convertMD5(Constants.DEFAULT_PASSWORD));
        student.setId(ObjectId.get().toString());
        DateKit.addObject(student);
        return studentDao.insertSelective(student);
    }

    public void batchAddStudents(StudentBatchAddParam batchAddParam) throws Exception {
        List<Student> students = this.readStudents(batchAddParam);
        studentDao.batchInsert(students);
    }

    private List<Student> readStudents(StudentBatchAddParam batchAddParam) throws Exception {
        List<Student> students = new ArrayList<>();
        MultipartFile file = batchAddParam.getFile();
        String fielName = file.getOriginalFilename();
        Workbook workbook;
        if (fielName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (fielName.endsWith("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            throw new BusinessException("文件格式上传错误");
        }

        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            String name = row.getCell(0).getStringCellValue();
            String studentCode = row.getCell(1).getStringCellValue();
            String email = row.getCell(2).getStringCellValue();
            String password;
            if (row.getCell(3) == null || row.getCell(3).getStringCellValue().trim().equals("")) {
                password = "123456";
            } else {
                password = row.getCell(3).getStringCellValue();
            }

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(studentCode) || StringUtils.isEmpty(email)) {
                continue;
            }
            Student student = new Student();
            student.setId(ObjectId.get().toString());
            student.setClassId(batchAddParam.getClassId());
            student.setName(name);
            student.setStudentCode(studentCode);
            student.setEmail(email);
            student.setDeleteFlag(Constants.UNDELETE);
            if (StringUtils.isEmpty(password)) {
                student.setPassword(MD5Kit.convertMD5("123456"));
            } else {
                student.setPassword(MD5Kit.convertMD5(password));
            }
            DateKit.teacherAdd(student);
            students.add(student);
        }
        return students;
    }

    public List<Operation> getStudentOperations(Student student) {
        Class classById = classService.getClassById(student.getClassId());
        Example example = new Example(Operation.class);
        example.createCriteria()
                .andEqualTo("courseId", classById.getCourseId())
                .andEqualTo("classId", classById.getId())
                .andGreaterThanOrEqualTo("endTime", LocalDateTime.now())
                .andLessThanOrEqualTo("startTime", LocalDateTime.now())
                .andEqualTo("status", 1)
                .andEqualTo("deleteFlag", Constants.UNDELETE)
                .andEqualTo("finishedCondition", 1);
        return opeartionService.getOperationsByExample(example);
    }

    public OperationVO getOperationDetail(String operationId) {
        Operation operation = opeartionService.getOperationById(operationId);
        return opeartionService.transferToVO(operation);
    }

    public Grade getGradeOfStudent(String studentId, String operationId) {
        return gradeService.getGradeByOperationAndStudent(operationId, studentId);
    }

    public String getAnsweredOfStudent(String studentId, String operationId) {
        Grade grade = this.getGradeOfStudent(studentId, operationId);
        if (grade == null) {
            return "";
        }
        return grade.getAnswered() == null ? "" : grade.getAnswered();
    }

    public CourseQuestion getQuestionById(String questionId) {
        return courseQuestionService.getQuestionById(questionId);
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse handleAnswer(CourseQuestion courseQuestion, String operationId) {
        Student student = LoginUserContext.getStudent();
        Grade grade = gradeService.getGradeByOperationAndStudent(operationId, student.getId());

        if (null == grade) {
            grade = new Grade();
            grade.setStudentId(student.getId());
            grade.setOperationId(operationId);
        }

        OperationVO operationVO = opeartionService.transferToVO(opeartionService.getOperationById(operationId));
        Map<String, Integer> gradeMap = operationVO.getQuestionList().stream()
                .collect(Collectors.toMap(CourseQuestionOperationVO::getId
                        , CourseQuestionOperationVO::getSingleGrade, (oldValue, newValue) -> newValue));

        RestResponse restResponse = checkAnswer(courseQuestion);
        int i = studentAnswerService.saveStudentAnswer(courseQuestion, operationId, restResponse.getMsg());
        if (i == 0) {
            throw new BusinessException("保存答案失败");
        }
        if (restResponse.isSuccess()) {
            courseQuestionService.updatePassRate(courseQuestion, true);
            int i1 = this.updateGrade(grade, courseQuestion.getId(), gradeMap.get(courseQuestion.getId()));
            if (i1 != 1) {
                throw new BusinessException("成绩更新失败");
            }
        } else {
            courseQuestionService.updatePassRate(courseQuestion, false);
            int i1 = this.updateGrade(grade, courseQuestion.getId(), 0);
            if (i1 != 1) {
                throw new BusinessException("成绩更新失败");
            }
        }
        return restResponse;
    }

    private int updateGrade(Grade grade, String questionId, int score) {
        if (StringUtils.isEmpty(grade.getId())) {
            //新增的成绩
            grade.setAnswered(questionId + "," + score);
            return gradeService.saveGrade(grade);
        } else {
            //更新成绩
            //将原来的提交的题目和分数换成一个map
            String[] split = grade.getAnswered().split(";");
            Map<String, Integer> answeredMap = new HashMap<>();
            for (String s : split) {
                //下面分别是id和分数
                String[] split1 = s.split(",");
                answeredMap.put(split1[0], Integer.parseInt(split1[1]));
            }
            answeredMap.put(questionId, score);
            //将map转成字符串
            StringBuilder answered = new StringBuilder();
            for (String key : answeredMap.keySet()) {
                answered.append(";").append(key).append(",").append(answeredMap.get(key));
            }
            answered.deleteCharAt(0);
            grade.setAnswered(answered.toString());
            return gradeService.saveGrade(grade);
        }
    }

    private RestResponse checkAnswer(CourseQuestion courseQuestion) {
        if (courseQuestion.getType() == 1) {
            CourseQuestion question = courseQuestionService.getQuestionById(courseQuestion.getId());
            if (question.getAnswer().equalsIgnoreCase(courseQuestion.getAnswer().trim())) {
                return RestResponse.success("正确");
            }
            return RestResponse.fail("错误");
        } else {
            List<ProgramAnswer> answers = programAnswerService.getAnswersByQuestionId(courseQuestion.getId());
            int passCount = 0;
            for (ProgramAnswer answer : answers) {
                RestResponse restResponse = programQuestionService.handleProgram(courseQuestion.getAnswer(), answer.getSystemIn(), answer.getSystemOut());
                if (restResponse.isSuccess()) {
                    passCount++;
                }
            }
            if (passCount == answers.size()) {
                return RestResponse.success("通过所有测试用例");
            }
            return RestResponse.fail("测试样例通过情况：" + passCount + "/" + answers.size());
        }
    }

    public List<Operation> getStudentOperationGrades(Student student) {
        Class classById = classService.getClassById(student.getClassId());
        Example example = new Example(Operation.class);
        example.createCriteria()
                .andEqualTo("classId", classById.getId())
                .andEqualTo("courseId", classById.getCourseId())
                .andEqualTo("finishedCondition", 0)
                .andEqualTo("status", 1);
        return opeartionService.getOperationsByExample(example);
    }

    public int updateUser(UserModifyParam userModifyParam) {
        if (userModifyParam.getPassword() != null) {
            userModifyParam.setPassword(MD5Kit.convertMD5(userModifyParam.getPassword()));
        }
        Student user = LoginUserContext.getStudent();
        if (!org.springframework.util.StringUtils.isEmpty(userModifyParam.getPassword())) {
            user.setPassword(userModifyParam.getPassword());
        }
        if (!org.springframework.util.StringUtils.isEmpty(userModifyParam.getUsername())) {
            user.setName(userModifyParam.getUsername());
        }
        if (!org.springframework.util.StringUtils.isEmpty(userModifyParam.getInfo())) {
            user.setInfo(userModifyParam.getInfo());
        }
        DateKit.studentUpdate(user);
        return studentDao.updateByPrimaryKeySelective(user);
    }

}
