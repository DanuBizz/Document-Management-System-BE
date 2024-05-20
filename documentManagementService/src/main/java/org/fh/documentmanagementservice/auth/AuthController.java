package org.fh.documentmanagementservice.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/usercontrol")
public class AuthController {

    @GetMapping
    public String getUserControl() {
        return "User accepted!";
    }
}
