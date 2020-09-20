package school.module.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import school.module.Service.UserService;
import school.module.config.ResponseCode;
import school.module.dao.UserMapper;
import school.module.entity.User;
import school.module.bean.RespBean;
import school.module.utils.JWTUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 描述：用户的注册、登录、修改等操作
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-19  13:01
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RespBean register(@RequestBody User user, HttpServletRequest request) {
        if(null != userService.selectByAccount(user.getAccount())){
            return new RespBean(ResponseCode.FAILED,"该账户已存在");
        }
        int boo = userService.insert(user);
        if(boo > 0){
            return new RespBean(ResponseCode.SUCCESS,"注册成功");
        }
        return new RespBean(ResponseCode.FAILED,"注册失败");
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object findByAccount(HttpServletRequest request) {

        String account = "";
        String token = "";
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        Claims claims = jwtUtils.parseJWT(token);
        if(claims != null) {
            account = (String) claims.get("account");
        } else {
            return new RespBean(ResponseCode.FAILED,"查询参数错误");
        }

        User user = userService.selectByAccount(account);
        if(user == null) {
            return new RespBean(ResponseCode.FAILED,"用户不存在");
        }
        return new RespBean(ResponseCode.SUCCESS,"", user);
    }
}
