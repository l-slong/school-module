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
 * 描述：登录
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-19  13:15
 */
@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login")
    public RespBean login(String account,String password,HttpServletRequest request) {
        User user = userMapper.selectByAccount(account);
        if(null == user){
            return new RespBean("failed","当前用户不存在，请注册");
        }
        if(password.equals(user.getPassword())){
            return new RespBean("success","欢迎用户"+user.getAccount());
        }
        return new RespBean("failed","密码错误，请重试");
    }


}
