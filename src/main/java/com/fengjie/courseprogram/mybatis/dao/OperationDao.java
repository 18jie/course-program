package com.fengjie.courseprogram.mybatis.dao;

import com.fengjie.courseprogram.model.entity.Operation;
import com.fengjie.courseprogram.mybatis.dao.base.BaseDao;
import com.fengjie.courseprogram.mybatis.mappers.OperationMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fengjie
 * @date 2019/5/8 17:30
 */
@Repository
public class OperationDao extends BaseDao<Operation, OperationMapper> {
}
