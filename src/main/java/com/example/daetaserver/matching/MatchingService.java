package com.example.daetaserver.matching;

import com.example.daetaserver.chatbot.Chatbot;
import com.example.daetaserver.chatbot.ChatbotRepository;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.matching.presentation.dto.MatchingResultDTO;
import com.example.daetaserver.matching.presentation.dto.ReadQuestionDTO;
import com.example.daetaserver.matching.presentation.dto.RecommendChatbotDTO;
import com.example.daetaserver.matching.presentation.dto.ResultDTO;
import com.example.daetaserver.matching.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final QuestionRepository questionRepository;
    private final ChatbotRepository chatbotRepository;
    private final WebClient webClient;

    public List<ReadQuestionDTO> getQuestion(){
        return questionRepository.findRandom10()
                .stream()
                .map((question)-> {
                        return new ReadQuestionDTO(question.getContent());
                    })
                .toList();
    }

    public List<RecommendChatbotDTO> recommendChatbot(
            List<ResultDTO> resultDTOList
    ){
        List<RecommendChatbotDTO> recommendChatbotDTOList = new ArrayList<>();

        webClient.post()
                .uri("/api/ai/stream")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "results", resultDTOList
                ))
                .retrieve()
                .bodyToMono(MatchingResultDTO.class)
                .subscribe(result ->{
                    //데이터 가공
                    Chatbot chatbot = chatbotRepository.findById(result.id())
                            .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_CHATBOT));
                    recommendChatbotDTOList.add(
                            new RecommendChatbotDTO(
                                    result.id(),
                                    chatbot.getProfileURL(),
                                    chatbot.getName(),
                                    result.ranking()
                            )
                    );
                });

        return recommendChatbotDTOList;

    }
}
