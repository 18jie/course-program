package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.ProgramAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Repository
public class ProgramAnswerDao extends BaseDao<ProgramAnswer, ProgramAnswerMapper> {

    @Autowired
    private ProgramAnswerMapper programAnswerMapper;

    public void batchInsert(List<ProgramAnswer> answers) {
        programAnswerMapper.batchInsert(answers);
    }

}
