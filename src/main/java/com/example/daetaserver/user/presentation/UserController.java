package com.example.daetaserver.user.presentation;

import com.example.daetaserver.global.common.ResponseJson;
import com.example.daetaserver.user.UserService;
import com.example.daetaserver.user.presentation.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseJson<ReadUserDTO> getMe(
            @RequestHeader("Authorization") String token
    ){
        return ResponseJson.success(userService.getMe(token));
    }
}
