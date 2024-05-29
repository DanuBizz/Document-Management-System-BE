package org.fh.documentmanagementservice.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * This ensures that a request to the UserControl web service is processed before each REST request
 * to check whether the user is (still) authorized or not.
 */

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Value("${user.control.url}")
    private String userControlUrl;
    /**
     * This method is called before each REST request.
     * It sends a request to the UserControl web service to check whether the user is authorized or not.
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("X-Csrf-Token", request.getHeader("X-Csrf-Token"));
        try {
            ResponseEntity<String> userControlResponse = restTemplate.exchange(userControlUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            if (userControlResponse.getStatusCode().is2xxSuccessful()) {
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

}
