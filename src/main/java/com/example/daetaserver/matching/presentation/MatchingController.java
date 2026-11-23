package com.example.daetaserver.matching.presentation;

import com.example.daetaserver.global.common.ResponseJson;
import com.example.daetaserver.matching.MatchingService;
import com.example.daetaserver.matching.presentation.dto.ReadQuestionDTO;
import com.example.daetaserver.matching.presentation.dto.RecommendChatbotDTO;
import com.example.daetaserver.matching.presentation.dto.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/recommend")
    public ResponseJson<List<RecommendChatbotDTO>> getRecommendChatbot(
            @RequestBody List<ResultDTO> resultsDTO
    ){
        return ResponseJson.success(matchingService.recommendChatbot(resultsDTO));
    }
}
