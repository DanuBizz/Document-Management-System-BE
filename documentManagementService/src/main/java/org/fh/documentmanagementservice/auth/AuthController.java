package org.fh.documentmanagementservice.auth;

import org.springframework.http.HttpStatus;
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
}
