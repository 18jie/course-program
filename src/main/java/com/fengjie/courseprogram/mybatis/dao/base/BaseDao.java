package com.fengjie.courseprogram.mybatis.dao.base;

import com.fengjie.courseprogram.mybatis.tkbase.GenericMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author fengjie
 * @date 2019:04:11
 */
public abstract class BaseDao<B, M extends GenericMapper<B>> {
    @Autowired
    private M m;

    public int deleteByPrimaryKey(Object o) {
        return m.deleteByPrimaryKey(o);
    }

    public int delete(B b) {
        return m.delete(b);
    }

    public int insert(B b) {
        return m.insert(b);
    }

    public int insertSelective(Object o) {
        return 0;
    }

    public boolean existsWithPrimaryKey(Object o) {
        return m.existsWithPrimaryKey(o);
    }

    public List<B> selectAll() {
        return m.selectAll();
    }

    public B selectByPrimaryKey(Object o) {
        return m.selectByPrimaryKey(o);
    }

    public int selectCount(B b) {
        return m.selectCount(b);
    }

    public List<B> select(B b) {
        return m.select(b);
    }

    public B selectOne(B b) {
        return m.selectOne(b);
    }

    public int updateByPrimaryKey(B b) {
        return m.updateByPrimaryKey(b);
    }

    public int updateByPrimaryKeySelective(B b) {
        return m.updateByPrimaryKeySelective(b);
    }

    public int deleteByExample(Object o) {
        return m.deleteByExample(o);
    }

    public List<B> selectByExample(Object o) {
        return m.selectByExample(o);
    }

    public int selectCountByExample(Object o) {
        return m.selectCountByExample(o);
    }

    public B selectOneByExample(Object o) {
        return m.selectOneByExample(o);
    }

    public int updateByExample(B b, Object o) {
        return m.updateByExample(b, o);
    }

    public int updateByExampleSelective(B b, Object o) {
        return m.updateByExampleSelective(b, o);
    }

    public List<B> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return m.selectByExampleAndRowBounds(o, rowBounds);
    }

    public List<B> selectByRowBounds(B b, RowBounds rowBounds) {
        return m.selectByRowBounds(b, rowBounds);
    }

    public int insertList(List<? extends B> list) {
        return m.insertList(list);
    }

    public int insertUseGeneratedKeys(B b) {
        return m.insertUseGeneratedKeys(b);
    }
}
