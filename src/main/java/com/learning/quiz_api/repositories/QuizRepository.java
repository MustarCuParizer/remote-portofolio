package com.learning.quiz_api.repositories;

import com.learning.quiz_api.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT q FROM Quiz q WHERE q.markedForDeletion = false")
    List<Quiz> findAllNotMarkedForDeletion();

    @Query("SELECT q FROM Quiz q WHERE q.markedForDeletion = true")
    List<Quiz> findAllMarkedForDeletion();

    @Query("SELECT q FROM Quiz q WHERE q.user.id = :userId AND q.markedForDeletion = false")
    List<Quiz> findByUserId(Long userId);

    @Query("SELECT q FROM Quiz q WHERE q.user.id = :userId AND q.markedForDeletion = false AND q.id = :id")
    Optional<Quiz> findByIdAndUserId(Long id, Long userId);

}
