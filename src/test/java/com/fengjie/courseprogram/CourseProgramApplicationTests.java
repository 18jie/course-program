package com.fengjie.courseprogram;

import com.fengjie.courseprogram.model.entity.ProgramQuestion;
import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.mybatis.dao.ProgramQuestionDao;
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
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseProgramApplicationTests {
    @Autowired
    UserDao userDao;

    @Autowired
    ProgramQuestionDao programQuestionDao;

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

    @Test
    public void testProgramQuestionDao(){
        List<ProgramQuestion> programQuestions = programQuestionDao.selectAllOrderByQuestionNo();
        System.out.println(programQuestions);
    }

    @Test
    public void testExample(){
        Example example = new Example(ProgramQuestion.class);
        example.setOrderByClause("question_no");
        List<ProgramQuestion> programQuestions = programQuestionDao.selectByExample(example);
        System.out.println(programQuestions);
    }

    @Test
    public void getMD5Str(){
        String password = "123456";
        System.out.println(MD5Kit.convertMD5(password));
    }


}
