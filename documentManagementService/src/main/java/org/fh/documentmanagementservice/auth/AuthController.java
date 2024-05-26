package org.fh.documentmanagementservice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(path = "/usercontrol")
public class AuthController {

    @GetMapping
    public String getUserControl() {
        return "User accepted!";
    }
    @PostMapping
    public HttpStatus postCredentials() {
        return HttpStatus.OK;
    }
}
