package com.example.daetaserver.matching.repository;

import com.example.daetaserver.matching.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM Question ORDER BY RAND() LIMIT 10",
            nativeQuery = true)
    List<Question> findRandom10();
}
