package com.eternal.interceptor;


import com.eternal.common.annotation.NoAuth;
import com.eternal.common.utils.ClientIpUtils;
import com.eternal.service.ISystemService;
import com.eternal.vo.UserLoginVo;
import com.eternal.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


/**
 * token验证拦截
 * @author jiajunmei
 */
@Slf4j

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final String TOKEN_HEADER = "Bearer";

    @Autowired
    private IUserService userService;

    @Autowired
    private ISystemService systemService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        System.out.println(method.getName());
        log.debug("Method: " + method.getName() + ", NoAuth: " + method.isAnnotationPresent(NoAuth.class));
        if (method.isAnnotationPresent(NoAuth.class)) {
            return true;
        }

        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (tokenHeader == null || "".equals(tokenHeader)) {
            log.info("token not found");
            //throw new Exception("Error : token not found.");
            result(response,
                   " {\"code\":\"400\",\"msg\":\"Error : token not found.\"}"
                    );
            return false;
        }

        if (!tokenHeader.startsWith(TOKEN_HEADER)) {
            log.info("invalid token");
            result(response,
                    " {\"code\":\"400\",\"msg\":\"Error : invalid token.\"}"
            );
            return false;
        }
        String token = tokenHeader.replace("Bearer", "").trim();
        String clientIp = ClientIpUtils.getIpAddress(request);
        UserLoginVo user = userService.getUserByToken(token);
        if(user == null){
            Boolean limit = systemService.isIpLimit(clientIp);
            if(limit){
                result(response,
                        " {\"code\":\"400\",\"msg\":\"Error :  action from "+clientIp+"  is too ofen.\"}"
                );
            }else{
                result(response,
                        " {\"code\":\"400\",\"msg\":\"Error : The token has expired. \"}"
                );
            }

            return false;
        }
        user.setIpaddr(clientIp);
        request.setAttribute("currentUser", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // TODO Auto-generated method stub
    }

    private void result(HttpServletResponse response, String json)  {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException e) {
            log.error("response error", e);
        }
    }
}
