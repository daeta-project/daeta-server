package com.example.daetaserver.user;

import com.example.daetaserver.deleteUser.DeleteUserRepository;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.global.security.JWT.TokenManager;
import com.example.daetaserver.user.presentation.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final DeleteUserRepository deleteUserRepository;
    private final TokenManager tokenManager;

    public ReadUserDTO getMe(String token){
        User user=userRepository.findById(tokenManager.getSubject(token))
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER));
        if (deleteUserRepository.existsByUser(user)){
            throw new CustomException(ErrorCode.DELETE_USER);
        }
        return ReadUserDTO.builder()
                .name(user.getName())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
