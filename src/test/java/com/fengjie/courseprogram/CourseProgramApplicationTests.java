package com.fengjie.courseprogram;

import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.mybatis.dao.UserDao;
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
        UserModifyParam userModifyParam = new UserModifyParam();
        userModifyParam.setUsername("张三");

        User user = new User();
        user.setUsername("fengjie");
        user.setEmail("111@11.com");
        user.setAccount("12312321");

        BeanUtils.copyProperties(userModifyParam,user);

        System.out.println(user);

//        User user = userDao.selectByPrimaryKey("5cbd5474a652fa4e989642df");
//        System.out.println(user.getId());

    }

}
