package com.fengjie.courseprogram.mybatis.mappers;

import com.fengjie.courseprogram.model.entity.ProgramQuestion;
import com.fengjie.courseprogram.mybatis.tkbase.GenericMapper;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:03
 */
public interface ProgramQuestionMapper extends GenericMapper<ProgramQuestion> {

    List<ProgramQuestion> selectAllOrderByQuestionNo();

}
