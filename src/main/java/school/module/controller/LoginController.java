package school.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import school.module.Service.UserService;
import school.module.config.ResponseCode;
import school.module.entity.User;
import school.module.utils.JWTUtils;
import school.module.bean.RespBean;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述：登录
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-19  13:15
 */
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespBean login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.selectByAccount(account);
        if(null == user){
            return new RespBean(ResponseCode.FAILED,"当前用户不存在，请注册");
        }
        if(password.equals(user.getPassword())){
            String token = jwtUtils.createJWT(account);

            response.setHeader(JWTUtils.AUTH_HEADER_KEY, JWTUtils.TOKEN_PREFIX + token);

            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);

            return new RespBean(ResponseCode.SUCCESS,"欢迎用户"+user.getAccount(),user);
        }
        return new RespBean(ResponseCode.FAILED,"密码错误，请重试");
    }
}
