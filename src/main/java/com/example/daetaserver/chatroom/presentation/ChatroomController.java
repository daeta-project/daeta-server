package com.example.daetaserver.chatroom.presentation;

import com.example.daetaserver.chatroom.ChatroomService;
import com.example.daetaserver.chatroom.presentation.dto.ReadChatroomDTO;
import com.example.daetaserver.global.common.ResponseJson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {
    private final ChatroomService chatroomService;

    @GetMapping
    public ResponseJson<List<ReadChatroomDTO>> getByUser(
            @RequestHeader("Authorization") String token
    ){
        return ResponseJson.success(chatroomService.getByUser(token));
    }
}
