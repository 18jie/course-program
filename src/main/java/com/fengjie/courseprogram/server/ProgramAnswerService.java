package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import com.fengjie.courseprogram.mybatis.dao.ProgramAnswerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Service
public class ProgramAnswerService {

    @Autowired
    private ProgramAnswerDao programAnswerDao;

    public ProgramAnswer getAnswerByQuestionId(String questionId) {
        ProgramAnswer programAnswer = new ProgramAnswer();
        programAnswer.setQuestionId(questionId);
        return programAnswerDao.selectOne(programAnswer);
    }

    public List<ProgramAnswer> getAnswersByQuestionId(String questionId) {
        ProgramAnswer programAnswer = new ProgramAnswer();
        programAnswer.setQuestionId(questionId);
        programAnswer.setDeleteFlag(Constants.UNDELETE);
        return programAnswerDao.select(programAnswer);
    }

}
