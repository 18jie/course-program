package com.fengjie.courseprogram.mybatis.mappers;

import com.fengjie.courseprogram.model.entity.ProgramAnswer;
import com.fengjie.courseprogram.mybatis.tkbase.GenericMapper;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:05:03
 */
public interface ProgramAnswerMapper extends GenericMapper<ProgramAnswer> {

    void batchInsert(List<ProgramAnswer> answers);

}
