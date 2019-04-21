package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019:04:14
 */
@Repository
public class UserDao extends BaseDao<User, UserMapper> {
}
