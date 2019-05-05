package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.context.LoginUserContext;
import com.fengjie.courseprogram.model.entity.User;
import com.fengjie.courseprogram.model.param.LoginParam;
import com.fengjie.courseprogram.model.param.RegisterParam;
import com.fengjie.courseprogram.model.param.UserModifyParam;
import com.fengjie.courseprogram.mybatis.dao.UserDao;
import com.fengjie.courseprogram.util.DateKit;
import com.fengjie.courseprogram.util.MD5Kit;
import com.fengjie.courseprogram.util.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author fengjie
 * @date 2019:04:14
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public boolean isExitUser(User user) {
        return userDao.selectCount(user) == 1;
    }

    public void registerUser(RegisterParam registerParam) throws Exception {
        registerParam.setPassword(MD5Kit.convertMD5(registerParam.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(registerParam, user);
        user.setId(ObjectId.get().toString());
        userDao.insert(user);
    }

    public User loginCheck(LoginParam loginParam) {
        User user = new User();
        user.setEmail(loginParam.getEmail());
        if (userDao.selectCount(user) == 0) {
            return null;
        }
        user.setPassword(MD5Kit.convertMD5(loginParam.getPassword()));
        return userDao.selectOne(user);
    }

    public User getUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return userDao.selectOne(user);
    }

    public int updateUser(UserModifyParam userModifyParam) {
        if (userModifyParam.getPassword() != null) {
            userModifyParam.setPassword(MD5Kit.convertMD5(userModifyParam.getPassword()));
        }
        User user = LoginUserContext.getUser();
        if(!StringUtils.isEmpty(userModifyParam.getPassword())){
            user.setPassword(userModifyParam.getPassword());
        }
        if(!StringUtils.isEmpty(userModifyParam.getUsername())){
            user.setUsername(userModifyParam.getUsername());
        }
        if(!StringUtils.isEmpty(userModifyParam.getInfo())){
            user.setInfo(userModifyParam.getInfo());
        }
        DateKit.updateObject(user);
        return userDao.updateByPrimaryKeySelective(user);
    }

}
