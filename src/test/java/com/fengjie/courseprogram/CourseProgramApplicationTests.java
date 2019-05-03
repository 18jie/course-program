package com.fengjie.courseprogram;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.mybatis.dao.UserDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fengjie.courseprogram.util.MD5Kit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseProgramApplicationTests {
    @Autowired
    UserDao userDao;

    @Test
    public void contextLoads() {
        User test = userDao.selectByPrimaryKey("test");
        test.setUsername("测试");
        int i = userDao.updateByPrimaryKeySelective(test);
        System.out.println(i);
//        System.out.println(test);


    }

    @Test
    public void testPage() {
        PageInfo<User> pageinfo = PageHelper.startPage(1, 2).doSelectPageInfo(() -> userDao.selectAll());
        System.out.println(pageinfo.getTotal());
        System.out.println(pageinfo.getList());
    }

}
