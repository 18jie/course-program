package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Class;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.ClassMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019/5/6 14:27
 */
@Repository
public class ClassDao extends BaseDao<Class, ClassMapper> {
}
