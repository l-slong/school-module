package school.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import school.module.dao.UserMapper;
import school.module.entity.User;
import school.module.utils.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：用户的注册、登录、修改等操作
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-19  13:01
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/api/user", method = RequestMethod.PUT)
    public RespBean register(String account,String password,HttpServletRequest request) {

        if(null != userMapper.selectByAccount(account)){
            return new RespBean("failed","该账户已存在");
        }
        User user = new User();
        user.setPassword(password);
        user.setAccount(account);
        int boo = userMapper.insert(user);
        if(boo > 0){
            return new RespBean("success","注册成功");
        }
        return new RespBean("failed","注册失败");
    }
}
