package school.module.controller;

import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import school.module.dao.UserMapper;
import school.module.entity.User;
import school.module.utils.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.Date;

/**
 * 描述：
 *
 * @Author: Lsl_Mercury
 * @Date: 2020-09-18  22:15
 */
@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public RespBean index(HttpServletRequest request) {
        try {
//            // 显示服务器IP、Name、SessionID
//            User user = new User();
//            user.setAccount("lsl");
//            user.setPassword("123456");
//            userMapper.insert(user);
//            InetAddress address = InetAddress.getLocalHost();
//            request.setAttribute("ip",address.getHostAddress().toString());
//            request.setAttribute("host",address.getHostName().toString());
//            HttpSession session = request.getSession();
//            session.setAttribute("sessionID",session.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RespBean("success","success");

    }

}
