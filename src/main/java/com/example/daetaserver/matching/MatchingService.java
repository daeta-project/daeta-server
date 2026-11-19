package com.example.daetaserver.matching;

import com.example.daetaserver.matching.presentation.dto.ReadQuestionDTO;
import com.example.daetaserver.matching.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final QuestionRepository questionRepository;

    public List<ReadQuestionDTO> getQuestion(){
        return questionRepository.findRandom10()
                .stream()
                .map((question)-> {
                        return new ReadQuestionDTO(question.getContent());
                    })
                .toList();
    }
}
