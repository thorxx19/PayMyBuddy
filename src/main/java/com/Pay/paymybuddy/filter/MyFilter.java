package com.Pay.paymybuddy.filter;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.NoTransactionException;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class MyFilter implements Filter {

    /**
     * method pour loguer les requete et response dans le programme
     * @param request la requéte
     * @param response la réponse
     * @param filterChain le filtre
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper((HttpServletResponse) response);

        filterChain.doFilter(request, responseCacheWrapperObject);

        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
        responseCacheWrapperObject.copyBodyToResponse();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!httpServletRequest.getServletPath().contains("swagger")
                && !httpServletRequest.getServletPath().contains("api-docs")
                && !httpServletRequest.getServletPath().contains("actuator")
                && !httpServletRequest.getServletPath().isBlank()
                && !httpServletRequest.getServletPath().contains("favicon")
        )
        {
            log.info("Protocol :{} Method :{} Server :{} Port:{} End point:{}", request.getProtocol(), httpServletRequest.getMethod()
                    , request.getServerName(), request.getLocalPort(), httpServletRequest.getServletPath());
            if (httpServletRequest.getQueryString() != null) {
                log.info("Parametre :{}", URLDecoder.decode(httpServletRequest.getQueryString(), StandardCharsets.UTF_8));
                filterChain.doFilter(request, response);
            }



            log.info("code Response :{}", httpServletResponse.getStatus());
            log.info("Response Json :{}", responseStr);
        }
    }
}
