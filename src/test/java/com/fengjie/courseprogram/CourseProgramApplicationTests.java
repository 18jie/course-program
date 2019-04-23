package com.fengjie.courseprogram;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.mybatis.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
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

}
