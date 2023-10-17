package com.yourchessboook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yourchessboook.api.LichessCodeAndVerifier;
import com.yourchessboook.oauth.PKCEUtil;
import com.yourchessboook.service.LichessOAuthService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final LichessOAuthService lichessOAuthService;

    @Autowired
    public AuthController(LichessOAuthService lichessOAuthService) {
        this.lichessOAuthService = lichessOAuthService;
    }

    @GetMapping("/params")
    public String[] getParams() {
        PKCEUtil pkceUtil = new PKCEUtil();
        return new String[] {
                pkceUtil.getCode_challenge(),
                pkceUtil.getCode_challenge_method(),
                pkceUtil.getState(),
                pkceUtil.getCode_verifier()
        };
    }

    @PostMapping("/access-token")
    public String createToken(@RequestBody LichessCodeAndVerifier codeAndVerifier) {
        return lichessOAuthService.authenticate(codeAndVerifier.getCode(), codeAndVerifier.getVerifier());
    }
}
