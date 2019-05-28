package com.fengjie.courseprogram.controller;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.dto.StudentGradeExcelDTO;
import com.fengjie.courseprogram.model.entity.Course;
import com.fengjie.courseprogram.model.entity.Grade;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.queryvo.GradeAnalysisVO;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.model.queryvo.StudentVO;
import com.fengjie.courseprogram.server.GradeService;
import com.fengjie.courseprogram.server.OpeartionService;
import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Comparator;
import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:17
 */
@Controller
@RequestMapping("/teacher/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private OpeartionService opeartionService;

    //成绩管理
    @GetMapping("")
    public String teacherGrade(ModelMap map) {
        Course course = LoginUserContext.getCourse();
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "unFinishedOperations");
        List<OperationVO> operations = opeartionService.transferToVO(gradeService.getAllUnfinishedOperations(course.getId()));
        map.addAttribute("operations", operations);
        return "teacher/teacherGrade";
    }

    @PostMapping("/checkGrade")
    public RestResponse checkGrade(Operation operation) {
        gradeService.checkGrade(operation.getId());
        return RestResponse.success();
    }

    @GetMapping("/finishedOperations")
    public String finishedOperations(ModelMap map) {
        Course course = LoginUserContext.getCourse();

        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "finishedOperations");
        List<OperationVO> operations = opeartionService.transferToVO(gradeService.getAllFinishedOperations(course.getId()));
        map.addAttribute("operations", operations);
        return "teacher/teacherFinishedOperations";
    }

    @GetMapping("/studentsGrade")
    public String studentsGrade(String operationId, ModelMap map) {
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "finishedOperations");
        List<StudentVO> studentVOS = gradeService.getallStudentOfOperation(operationId);
        studentVOS.sort(Comparator.reverseOrder());
        GradeAnalysisVO gradeAnalysisVO = gradeService.buildAnalysisVO(studentVOS);
        map.addAttribute("gradeAnalysis", gradeAnalysisVO);
        map.addAttribute("students", studentVOS);
        map.addAttribute("operationId", operationId);
        return "teacher/teacherStudentsGrades";
    }

    @GetMapping("/studentGradeDetail")
    public String studentGradeDetail(ModelMap map, String operationId, String studentId) {
        map.addAttribute("active", "grade");
        map.addAttribute("sideActive", "finishedOperations");
        map.addAttribute("operationId", operationId);
        map.addAttribute("studentId", studentId);
        Operation operationById = opeartionService.getOperationById(operationId);
        OperationVO operationVO = opeartionService.transferToVO(operationById);
        gradeService.addAnswerDetailToOperationVO(operationVO, studentId);
        map.addAttribute("operation", operationVO);
        return "teacher/studentGradeDetail";
    }

    @PostMapping("/recheckGrade")
    @ResponseBody
    public RestResponse updateGrade(Grade grade) {
        int i = gradeService.recheckGrade(grade);
        if (i == 1) {
            return RestResponse.success();
        }
        return RestResponse.fail();
    }

    @GetMapping("/excelDownload")
    @ResponseBody
    public String excelDownload(HttpServletResponse response, String operationId) throws UnsupportedEncodingException {
        StudentGradeExcelDTO excel = gradeService.getExcel(operationId);
        File file = new File(excel.getFilepath());
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(excel.getFilename().getBytes("UTF-8"), "iso-8859-1"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                int total = 0;
                while (i != -1) {
                    total += i;
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("文件总大小: " + total);
//                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
