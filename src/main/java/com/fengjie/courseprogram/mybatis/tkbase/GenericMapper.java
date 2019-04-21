package com.fengjie.courseprogram.mybatis.tkbase;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@RegisterMapper
public interface GenericMapper<T> extends Mapper<T>, MySqlMapper<T>{
}