package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.model.entity.CourseQuestion;
import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.model.param.OperationQuestionParam;
import com.fengjie.courseprogram.model.param.SubmitQuestionParam;
import com.fengjie.courseprogram.model.queryvo.OperationVO;
import com.fengjie.courseprogram.mybatis.dao.OperationDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengjie
 * @date 2019/5/8 17:34
 */
@Service
@Slf4j
public class OpeartionService {

    @Autowired
    private OperationDao operationDao;

    @Autowired
    @Qualifier("emailCache")
    private CaffeineCache cache;

    @Autowired
    private CourseQuestionService courseQuestionService;

    public List<Operation> listOperations(String courseId, int deleteFlag) {
        Operation operation = new Operation();
        operation.setCourseId(courseId);
        operation.setDeleteFlag(deleteFlag);
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

    public int saveOpeartion(Operation operation) {
        if (StringUtils.isEmpty(operation.getId())) {
            return addOperation(operation);
        }
        return updateOperation(operation);
    }

    public int addOperation(Operation operation) {
        DateKit.teacherAdd(operation);
        operation.setStatus(0);
        return operationDao.insertSelective(operation);
    }

    public Operation getOperationById(String id) {
        Operation operation = new Operation();
        operation.setDeleteFlag(Constants.UNDELETE);
        operation.setId(id);
        return operationDao.selectOne(operation);
    }

    public OperationVO transferToVO(Operation operation) {
        OperationVO operationVO = new OperationVO();
        BeanUtils.copyProperties(operation, operationVO);
        if (null != operation.getStartTime()) {
            operationVO.setStartTimeStr(operation.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (null != operation.getEndTime()) {
            operationVO.setEndTimeStr(operation.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (!StringUtils.isEmpty(operation.getQuestions())) {
            //处理作业
        }
        return operationVO;
    }

    public List<OperationVO> transferToVO(List<Operation> operation) {
        List<OperationVO> operationVOS = new ArrayList<>();
        operation.forEach(operation1 -> operationVOS.add(this.transferToVO(operation1)));
        return operationVOS;
    }

    /**
     * 更新作业
     *
     * @param operation
     * @return
     */
    public int updateOperation(Operation operation) {
        DateKit.teacherUpdate(operation);
        return operationDao.updateByPrimaryKeySelective(operation);
    }

    public List<CourseQuestion> getAllCourseQuestionByCourseId(String courseId) {
        return courseQuestionService.getAllQuestion(courseId);
    }

    /**
     * 暂时缓存用户提交的题目信息，不进入数据库
     *
     * @param operationQuestionParam
     * @return
     */
    public boolean saveOperationQuestionsTemp(OperationQuestionParam operationQuestionParam) {
        try {
            cache.put(operationQuestionParam.getUuid(), operationQuestionParam);
        } catch (Exception e) {
            log.error("缓存出错", e);
            return false;
        }
        return true;
    }

    /**
     * 从缓存中获取用户刚刚选择的题目信息
     *
     * @param uuid
     * @return
     */
    public List<CourseQuestion> getCheckedQuestions(String uuid) {
        OperationQuestionParam value = (OperationQuestionParam) cache.get(uuid).get();
        return courseQuestionService.getQuestionsByIds(value.getQuestionIds());
    }

    /**
     * 处理从具体的添加作业的信息
     *
     * @param param
     * @return
     */
    public int submitQuestionMsg(SubmitQuestionParam param) {
        Operation operation = new Operation();
        operation.setId(ObjectId.get().toString());
        operation.setTitle(param.getTitle());
        operation.setStartTime(param.getStartTime());
        operation.setEndTime(param.getEndTime());
        DateKit.teacherAdd(operation);
        return operationDao.insert(operation);
    }

}
