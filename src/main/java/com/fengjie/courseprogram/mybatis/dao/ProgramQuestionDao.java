package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.ProgramQuestion;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.ProgramQuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:03
 */
@Repository
public class ProgramQuestionDao extends BaseDao<ProgramQuestion, ProgramQuestionMapper> {

    @Autowired
    ProgramQuestionMapper programQuestionMapper;

    public List<ProgramQuestion> selectAllOrderByQuestionNo() {
        return programQuestionMapper.selectAllOrderByQuestionNo();
    }

}
