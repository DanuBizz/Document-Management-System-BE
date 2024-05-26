package org.fh.documentmanagementservice.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/usercontrol")
public class AuthController {
    /**
     * Get user control.
     * @return
     */
    @GetMapping
    public String getUserControl() {
        return "User accepted!";
    }

    /**
     * Post credentials.
     * @return
     */
    @PostMapping
    public HttpStatus postCredentials() {
        return HttpStatus.OK;
    }
    /**
     * Logout.
     * @param request
     * @param response
     * @param authentication
     * @return
     */
    @GetMapping(path = "/logout")
    public HttpStatus logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return HttpStatus.OK;
    }
}
