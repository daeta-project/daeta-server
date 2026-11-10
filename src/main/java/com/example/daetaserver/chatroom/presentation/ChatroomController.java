package com.example.daetaserver.chatroom.presentation;

import com.example.daetaserver.chat.presentation.dto.ReadChatDTO;
import com.example.daetaserver.chatroom.ChatroomService;
import com.example.daetaserver.chatroom.presentation.dto.DeleteChatDTO;
import com.example.daetaserver.chatroom.presentation.dto.SendMessageDTO;
import com.example.daetaserver.global.common.ResponseJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatroomController {
    private final ChatroomService chatroomService;

    @GetMapping("{id}")
    public ResponseJson<List<ReadChatDTO>> getChat(
            @PathVariable String id,
            @RequestParam LocalDateTime time,
            @RequestHeader("Authorization") String token
    ){
        return ResponseJson.success(chatroomService.getChat(token, time, id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChat(
            @PathVariable String id,
            @RequestBody DeleteChatDTO deleteChatDTO,
            @RequestHeader("Authorization") String token
    ){
        chatroomService.deleteChats(deleteChatDTO, token, id);
    }

    @PostMapping("{id}")
    public void sendChat(
            @PathVariable String id,
            @RequestBody SendMessageDTO sendMessageDTO,
            @RequestHeader("Authorization") String token
    ){
        chatroomService.sendChat(token, id, sendMessageDTO);
    }
}
