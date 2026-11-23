package com.example.daetaserver.matching;

import com.example.daetaserver.chatbot.Chatbot;
import com.example.daetaserver.chatbot.ChatbotRepository;
import com.example.daetaserver.global.common.ClientProperties;
import com.example.daetaserver.global.error.CustomException;
import com.example.daetaserver.global.error.ErrorCode;
import com.example.daetaserver.matching.presentation.dto.MatchingResultDTO;
import com.example.daetaserver.matching.presentation.dto.ReadQuestionDTO;
import com.example.daetaserver.matching.presentation.dto.RecommendChatbotDTO;
import com.example.daetaserver.matching.presentation.dto.ResultDTO;
import com.example.daetaserver.matching.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        List<MatchingResultDTO> matchingResultDTOList = webClient.post()
                .uri(ClientProperties.RECOMMEND_URL.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "results", resultDTOList
                ))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> Mono.error(
                                new CustomException(ErrorCode.ERROR_AI_API)
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<MatchingResultDTO>>() {})
                .block();


        if (matchingResultDTOList!=null) {
            //챗봇에서 id만 모으기
            List<String> chatbotIds = matchingResultDTOList
                    .stream()
                    .map(MatchingResultDTO::id)
                    .toList();
            //위에서 모은 id를 기반으로 모든 챗봇 조회
            Map<String, Chatbot> chatbotMap = chatbotRepository.findAllById(chatbotIds)
                    .stream()
                    .collect(Collectors.toMap(Chatbot::getId, Function.identity()));

            return matchingResultDTOList.stream()
                    .map(result -> {
                        //데이터 가공
                        Chatbot chatbot = chatbotMap.get(result.id());
                        if (chatbot == null) {
                            throw new CustomException(ErrorCode.NOT_FOUND_CHATBOT);
                        }
                        return new RecommendChatbotDTO(
                                result.id(),
                                chatbot.getProfileURL(),
                                chatbot.getName(),
                                result.ranking()
                        );
                    })
                    .toList();
        }
        return List.of();
    }
}
