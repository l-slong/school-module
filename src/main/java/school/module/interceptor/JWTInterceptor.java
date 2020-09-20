package school.module.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import school.module.dao.UserMapper;
import school.module.entity.User;
import school.module.utils.JWTUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Limgmk
 * @time 20-9-19 下午8:29
 * @description
 */

public class JWTInterceptor extends HandlerInterceptorAdapter{
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 先遍历 cookie 查询 token
        String token = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie: cookies) {
                if(cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // cookie 中无 token 时再尝试从 Header 中获取
        if(token.equals("")) {
            String authHeader = request.getHeader(JWTUtils.AUTH_HEADER_KEY);
            if (authHeader == null || !authHeader.startsWith(JWTUtils.TOKEN_PREFIX)) {
                responseLoginErr(response, "Unauthorized");
                return false;
            }
            token = authHeader.substring(JWTUtils.TOKEN_PREFIX.length());
        }

        Claims claims = jwtUtils.parseJWT(token);

        if (claims == null) {
            responseLoginErr(response, "Token is invalid");
            return false;
        }

        User user = userMapper.selectByAccount((String) claims.get("account"));
        if (!user.getTokenVersion().equals((String) claims.get("token_version"))) {
            responseLoginErr(response, "your password is updated, please login again");
            return false;
        }
        return true;
    }

    private void responseLoginErr(HttpServletResponse response, String info) throws JSONException, IOException {
        JSONObject body = new JSONObject();
        body.put("status", "err");
        body.put("msg", info);
        response.getWriter().append(body.toString());
        response.setHeader("Content-type", "application/json");
    }
}