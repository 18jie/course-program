package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.mybatis.dao.OperationDao;
import com.fengjie.courseprogram.util.DateKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/8 17:34
 */
@Service
public class OpeartionService {

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private CourseQuestionService courseQuestionService;

    public List<Operation> listOperations(String courseId, int deleteFlag) {
        Operation operation = new Operation();
        operation.setCourseId(courseId);
        operation.setDeleteFlag(0);
        return operationDao.select(operation);
    }

    public int logicDeleteOperation(String operationId) {
        Operation operation = new Operation();
        operation.setId(operationId);
        operation.setDeleteFlag(Constants.DELETEED);
        DateKit.teacherUpdate(operation);
        return operationDao.updateByPrimaryKeySelective(operation);
    }

    public int deleteOperation(String operationId) {
        return operationDao.deleteByPrimaryKey(operationDao);
    }

    public int rebirthOperation(String operationId) {
        Operation operation = new Operation();
        operation.setId(operationId);
        operation.setDeleteFlag(Constants.UNDELETE);
        DateKit.teacherUpdate(operation);
        return operationDao.updateByPrimaryKeySelective(operation);
    }

    public int updateOperation(Operation operation) {
        DateKit.teacherUpdate(operation);
        return operationDao.updateByPrimaryKeySelective(operation);
    }

    public List<CourseQuestion> getAllCourseQuestionByCourseId(String courseId) {
        return courseQuestionService.getAllQuestion(courseId);
    }

}
