package com.eternal.interceptor;


import com.eternal.common.annotation.PassToken;
import com.eternal.common.utils.StringUtils;
import com.eternal.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * token验证拦截
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
//-----BEGIN PUBLIC KEY-----
//    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3SnGP/P3zWa/3StsrZpq
//    Lm7KbS8QlDwafV+GlaUIO2wxkiLhdGPC4VRrzj15FW6im1D9ZiyaGMbxxm1vnRfB
//    vigI+/ZxR2h/ry1L/SzINOzQO/foY3G7gdkp1hDnhAqO7ghrF4SMS1iPc6cjOqqa
//    XWrEatEPCGz62t+NZb1vPuvQEHBIvEDAYT86Brzl5dR/19AHL8eT1TqA9/uybicz
//    NCZa0jchOTEgICLN8zSsUw79Efn2dR2/+s9qItW5JH3VSAfRncszBzTWsrjcij6I
//    acfG3o9WODQNeQzrkE0jtlyhGzN/+H1my8mr5NT3qdrABTUw3T1qqMNtPqXYKoNT
//            kwIDAQAB
//-----END PUBLIC KEY-----

//    -----BEGIN PRIVATE KEY-----
//    MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDdKcY/8/fNZr/d
//            K2ytmmoubsptLxCUPBp9X4aVpQg7bDGSIuF0Y8LhVGvOPXkVbqKbUP1mLJoYxvHG
//    bW+dF8G+KAj79nFHaH+vLUv9LMg07NA79+hjcbuB2SnWEOeECo7uCGsXhIxLWI9z
//    pyM6qppdasRq0Q8IbPra341lvW8+69AQcEi8QMBhPzoGvOXl1H/X0Acvx5PVOoD3
//+7JuJzM0JlrSNyE5MSAgIs3zNKxTDv0R+fZ1Hb/6z2oi1bkkfdVIB9GdyzMHNNay
//    uNyKPohpx8bej1Y4NA15DOuQTSO2XKEbM3/4fWbLyavk1Pep2sAFNTDdPWqow20+
//    pdgqg1OTAgMBAAECggEAPgFmSavWv8mc5R81mv594oLBUhct6MFpE9liXDYmtrJA
//    u53346/MhWwrxWjKt82K3Uy5npnGoke0pVMtRbfRY8TJsAqJ++E6Wxyx/0s83km+
//    n/GDP841KdR+vDuFDSoApI/Lpo5M3xr3LQ+g+uO135b9do0iT9RuCEtA01NnCxaA
//    f5z/utO4aY0m3ALjdD8iHj4Ws38BQw/uC5foXNCCaUuYoqB1oQOz0XjeAfkYcPlH
//    eCj6v6/PFka1aM202EmYm4SGJWs6CnUfXwuje605zzjp5s2QjDx1AJfvLX4IxbaL
//            iYypM0k90DoMeFpSNNlxlPcuSswjbJPMOschIi9FMQKBgQD2dh3WoDfWo2LKjcDq
//87cZOV5Ek0Lw/0XdmOb2biFSOInXexjCU/NU0MOcWtZM7qf4OAzHNpO25YRNIojF
///Xx3++W2DOXrFJMExpbEKRhjhmfhIfVD9YC/EDn8bOy2FmRUpMQWeqNabyYne6q/
//            +qg1w5ajxrYECw8GZCVrnxYQrwKBgQDluQJJNJeKAY1nPG+oQMx5dvTFPI21rABh
//    yGKec+Aao6PXtrzWh3vBX6K/Mh1plwmw++qGUIhTLR8I1VgoGMmtQXkNd4TYoTFp
//            wksFsPlCjwQJhRntV3I5IiwF8KwpsUGKIKmc0g4V7ViTyXT5BsiPy7zhDASDgKbg
//    MxhKwN38XQKBgQCWIemXPk4Fb83UOXmVJhUZRnsIJvSWPcpfbK702q1tmodNPe0d
//    I2qM9gbSjfsRLigf6nTOPY7J0RJAspLCmOFjbYzG0yCTjRrpw9c/cxEqP0u0Nq1j
//    Un+047uO6wNfAFDpxty0HMy5YA/L6WdgPt6MsbJn3bouyFXEvOiWoHgefQKBgHwP
//    kyV3ypMUTYpAQEjMXeEHFh9imWFQd5BXSLMr/w5D65r2rDrrRjFjdZ/t76ZzlB9J
//    nRlWapkI2CbMGOTmphv0LOzR7COVwfrMm/mB3stMMzD0+dCMljo2szGuDTwi5zMy
//    eocfhc3ftSNy+8H1IOFPdTzQYaunEfgof+j3CatdAoGAQh42RBYUZOu+n+X7I2yX
//    E5Zj5tBRbP4BI9X80VKRs1WJq2Ee8HSBUBvD4Cps3aw7HI1acrJD4OuS/jno4+zF
//            H9J5dbPWkuIW1YDkIYnQGpeOW4NN693qq1JiJ6rU1JWZTopkuObJtPB5QACBpdMq
//    Wr37+WYwGCAcrOJHSjjS860=
//            -----END PRIVATE KEY-----


    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (tokenHeader == null || "".equals(tokenHeader)) {
            log.info("token not found");
        }
        assert tokenHeader != null;
        if (!tokenHeader.startsWith("Bearer")) {
            log.info("invalid token");
        }
        String token = tokenHeader.replace("Bearer", "");
        String userCatchId = (String) redisUtils.get(token);
        if (StringUtils.isEmpty(userCatchId)) {
            return false;

        }
        System.out.println(userCatchId);
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

}
