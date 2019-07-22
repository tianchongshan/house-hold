package com.tcs.household.mgr.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tcs.household.enums.MessageCode;
import com.tcs.household.enums.RedisConstant;
import com.tcs.household.exception.BizException;
import com.tcs.household.mgr.security.MyUserDetailsServiceImpl;
import com.tcs.household.model.response.JsonResponse;
import com.tcs.household.util.JwtTokenUtil;
import com.tcs.household.util.RedisUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * token过滤器来验证token有效性
 * Created by chongshan.tian01.
 */
@Slf4j
public class MyAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${auth.header}")
    private String tokenHeader;

    @Value("${admin.login.url}")
    private String loginPath;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        boolean isLogin = request.getRequestURI().indexOf(loginPath) >= 0;
        if (isLogin) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (authToken != null) {
                Claims userInfo = null;
                try {
                    userInfo = jwtTokenUtil.getUserinfoFromToken(authToken);
                } catch (BizException e) {
                    tokenExpired(response);
                    return;
                }
                if (userInfo == null) {
                    tokenExpired(response);
                    return;
                }
                String username = userInfo.get(JwtTokenUtil.CLAIM_KEY_USERNAME, String.class);
                Object sessionToken = RedisUtils.get(RedisConstant.REDIS_USER_LOGIN_TOKEN.getKey() + "_" + username);

                if (StringUtils.isEmpty(sessionToken)
                        || !sessionToken.toString().equals(authToken)) {
                    // Token 无效
                    tokenExpired(response);
                    return;
                }
                if (username != null) {

                    // 如果相信token中的数据，也就是签名token的secret的机制足够好
                    // 这种情况下，可以不用再查询数据库，而直接采用token中的数据
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error("服务器异常", e);
            error(response);
            return;
        }
    }

        /**
         *
         * @param response
         * @throws IOException
         * @throws UnsupportedEncodingException
         */
        private void tokenExpired(HttpServletResponse response) throws IOException, UnsupportedEncodingException {
            JsonResponse<Void> resp = new JsonResponse<>();
            resp.setCode(MessageCode.TOKEN_INVALID.getCode());
            resp.setMsg(MessageCode.TOKEN_INVALID.getMessage());
            responseMessage(response, resp, HttpStatus.UNAUTHORIZED.value());
        }

        private void error(HttpServletResponse response) throws IOException, UnsupportedEncodingException {
            JsonResponse<Void> resp = new JsonResponse<>();
            resp.setCode(MessageCode.FAIL.getCode());
            resp.setMsg(MessageCode.FAIL.getMessage());
            responseMessage(response, resp, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        private void responseMessage(HttpServletResponse response, JsonResponse<Void> resp, int responseStatus)
			throws IOException {
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(responseStatus);
            String userJson = JSON.toJSONString(resp, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        }




}
