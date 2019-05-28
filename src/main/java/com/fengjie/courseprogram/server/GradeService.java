package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.exceptions.BusinessException;
import com.fengjie.courseprogram.model.dto.StudentGradeExcelDTO;
import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.StudentAnswer;
import com.fengjie.courseprogram.model.queryvo.GradeAnalysisVO;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.model.queryvo.StudentVO;
import com.fengjie.courseprogram.mybatis.dao.GradeDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.ObjectId;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Consumer;
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

    @Autowired
    private StudentAnswerService studentAnswerService;

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
        Map<String, String> answeredMap = grades.stream()
                .collect(Collectors.toMap(Grade::getStudentId
                        , Grade::getAnswered, (oldValue, newValue) -> newValue));

        Operation operation = opeartionService.getOperationById(operationId);
        List<Student> students = studentService.getStudentsByClassId(operation.getClassId());

        List<StudentVO> studentVOS = new ArrayList<>();
        students.forEach(stu -> {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(stu, studentVO);
            studentVO.setGrade(gradeMap.getOrDefault(stu.getId(), 0));
            studentVO.setIsSubmitOperation(answeredMap.get(stu.getId()) != null);
            studentVOS.add(studentVO);
        });
        return studentVOS;
    }

    public void addAnswerDetailToOperationVO(OperationVO operationVO, String studentId) {
        Student student = studentService.getStudentById(studentId);
        StudentAnswer queryAnswer = new StudentAnswer();
        queryAnswer.setStudentId(student.getId());
        queryAnswer.setOperationId(operationVO.getId());
        List<StudentAnswer> studentAnswers = studentAnswerService.getStudentAnswer(queryAnswer);
        Map<String, String> answerMap = studentAnswers.stream().
                collect(Collectors.toMap(StudentAnswer::getQuestionId, StudentAnswer::getAnswer, (oldValue, newValue) -> newValue));
        Map<String, String> answerStatusMap = studentAnswers.stream().
                collect(Collectors.toMap(StudentAnswer::getQuestionId, StudentAnswer::getAnswerStatus, (oldValue, newValue) -> newValue));

        Grade grade = new Grade();
        grade.setOperationId(operationVO.getId());
        grade.setStudentId(student.getId());
        grade.setDeleteFlag(Constants.UNDELETE);
        List<Grade> grades = gradeDao.select(grade);
        Map<String, Integer> gradeMap = this.getGradeMap(grades.get(0));

        operationVO.getQuestionList().forEach(p -> {
            p.setAnswered(false);
            if (answerMap.containsKey(p.getId())) {
                p.setAnswered(true);
                p.setSubmitAnswer(answerMap.get(p.getId()));
                p.setCurrentGrade(gradeMap.get(p.getId()));
                p.setAnswerStatus(answerStatusMap.get(p.getId()));
            }
        });
    }

    private Map<String, Integer> getGradeMap(Grade grade) {
        Map<String, Integer> oldGradeMap = new HashMap<>();
        String[] answeres = grade.getAnswered().split(";");
        for (String answere : answeres) {
            String[] split = answere.split(",");
            oldGradeMap.put(split[0], Integer.parseInt(split[1]));
        }
        return oldGradeMap;
    }

    public int recheckGrade(Grade grade) {
        Student student = studentService.getStudentById(grade.getStudentId());

        Grade queryGrade = new Grade();
        queryGrade.setStudentId(student.getId());
        queryGrade.setOperationId(grade.getOperationId());
        Grade oldGrade = gradeDao.selectOne(queryGrade);

        Map<String, Integer> oldGradeMap = this.getGradeMap(oldGrade);
        Map<String, Integer> newGradeMap = this.getGradeMap(grade);

        newGradeMap.forEach(oldGradeMap::put);

        StringBuilder newGradeStr = new StringBuilder();
        Integer total = 0;
        for (String k : oldGradeMap.keySet()) {
            Integer v = oldGradeMap.get(k);
            newGradeStr.append(k).append(",").append(v).append(";");
            total += v;
        }

        newGradeStr.deleteCharAt(newGradeStr.length() - 1);
        oldGrade.setAnswered(newGradeStr.toString());
        oldGrade.setGrade(total);
        DateKit.teacherUpdate(oldGrade);

        return gradeDao.updateByPrimaryKeySelective(oldGrade);
    }

    public GradeAnalysisVO buildAnalysisVO(List<StudentVO> students) {
        GradeAnalysisVO gradeAnalysisVO = new GradeAnalysisVO();
        gradeAnalysisVO.setHighestScore(students.get(0).getGrade());
        gradeAnalysisVO.setLowestScore(students.get(students.size() - 1).getGrade());

        int totalGrade = 0;
        int totalSubmit = 0;
        for (StudentVO student : students) {
            totalGrade += student.getGrade();
            totalSubmit += student.getIsSubmitOperation() ? 1 : 0;
        }
        double average = totalGrade / students.size();
        gradeAnalysisVO.setAverage(average);
        gradeAnalysisVO.setTotalSubmit(totalSubmit);
        return gradeAnalysisVO;
    }

    public StudentGradeExcelDTO getExcel(String operationId) {
        Operation operation = opeartionService.getOperationById(operationId);
        StudentGradeExcelDTO excel = new StudentGradeExcelDTO();
        if (!StringUtils.isEmpty(operation.getExcelPath())) {
            excel.setFilepath(operation.getExcelPath());
            excel.setFilename(operation.getTitle() + "_学生成绩.xlsx");
            return excel;
        }

        List<StudentVO> studentVOS = this.getallStudentOfOperation(operationId);
        String[] title = {"姓名", "学号", "分数"};
        String path = "D:\\program_project\\idea_workplace\\excelPath";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }

        for (int i = 1; i <= studentVOS.size(); i++) {
            XSSFRow row1 = sheet.createRow(i);
            StudentVO studentVO = studentVOS.get(i - 1);

            XSSFCell cell = row1.createCell(0);
            cell.setCellValue(studentVO.getName());

            XSSFCell cell1 = row1.createCell(1);
            cell1.setCellValue(studentVO.getStudentCode());

            XSSFCell cell2 = row1.createCell(2);
            cell2.setCellValue(studentVO.getGrade());
        }
        String filePath = path + "\\" + UUID.randomUUID().toString() + ".xlsx";
        File file = new File(filePath);
        try {
            boolean newFile = file.createNewFile();
            if (!newFile) {
                throw new BusinessException("Excel文件创建失败");
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            throw new BusinessException("Excel文件生成失败");
        }

        operation.setExcelPath(filePath);
        int i = opeartionService.updateOperation(operation);
        if (i == 0) {
            throw new BusinessException("路径写入失败");
        }
        excel.setFilepath(filePath);
        excel.setFilename(operation.getTitle() + "_学生成绩.xlsx");
        return excel;
    }

}
