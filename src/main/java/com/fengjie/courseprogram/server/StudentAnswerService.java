package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Student;
import com.fengjie.courseprogram.model.entity.StudentAnswer;
import com.fengjie.courseprogram.mybatis.dao.StudentAnswerDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswerDao studentAnswerDao;

    public int saveStudentAnswer(CourseQuestion courseQuestion, String operationId, String answerStatus) {
        Student student = LoginUserContext.getStudent();

        StudentAnswer queryStudent = new StudentAnswer();
        queryStudent.setOperationId(operationId);
        queryStudent.setQuestionId(courseQuestion.getId());
        queryStudent.setStudentId(student.getId());
        List<StudentAnswer> answers = studentAnswerDao.select(queryStudent);

        if (answers.size() == 0) {
            //新增答案
            StudentAnswer insertAnswer = new StudentAnswer();
            BeanUtils.copyProperties(queryStudent, insertAnswer);
            insertAnswer.setAnswer(courseQuestion.getAnswer());
            insertAnswer.setAnswerStatus(answerStatus);
            insertAnswer.setId(ObjectId.get().toString());
            DateKit.studentAdd(insertAnswer);
            return studentAnswerDao.insertSelective(insertAnswer);
        }

        //修改答案
        StudentAnswer studentAnswer = answers.get(0);
        studentAnswer.setAnswer(courseQuestion.getAnswer());
        studentAnswer.setAnswerStatus(answerStatus);
        DateKit.studentUpdate(studentAnswer);
        return studentAnswerDao.updateByPrimaryKeySelective(studentAnswer);
    }

    public List<StudentAnswer> getStudentAnswer(StudentAnswer studentAnswer) {
        return studentAnswerDao.select(studentAnswer);
    }

}
