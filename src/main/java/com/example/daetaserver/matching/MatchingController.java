package com.example.daetaserver.matching;

import com.example.daetaserver.global.common.ResponseJson;
import com.example.daetaserver.matching.presentation.dto.ReadQuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
    private final MatchingService matchingService;

    @GetMapping("/question")
    public ResponseJson<List<ReadQuestionDTO>> getQuestion(){
        return ResponseJson.success(matchingService.getQuestion());
    }
}
