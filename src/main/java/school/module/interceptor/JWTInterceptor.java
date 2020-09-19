package school.module.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import school.module.config.JWTConfig;
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
    private JWTConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 先遍历 cookie 查询 token
        String token = "";
        for(Cookie cookie: request.getCookies()) {
            if(cookie.getName().equals("token")) {
                token = cookie.getValue();
                break;
            }
        };

        // cookie 中无 token 时再尝试从 Header 中获取
        if(token.equals("")) {
            // 获取请求头信息authorization信息
            String authHeader = request.getHeader(JWTUtils.AUTH_HEADER_KEY);
            if (authHeader == null || !authHeader.startsWith(JWTUtils.TOKEN_PREFIX)) {
                responseLoginErr(response, "Unauthorized");
                return false;
            }
            token = authHeader.substring(JWTUtils.TOKEN_PREFIX.length());
        }

        Claims claims = JWTUtils.parseJWT(token, jwtConfig);

        if (claims == null) {
            responseLoginErr(response, "Token is invalid");
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