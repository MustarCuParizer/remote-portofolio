package com.learning.quiz_api.repositories;

import com.learning.quiz_api.entities.Category;
import com.learning.quiz_api.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.markedForDeletion = false")
    List<Question> findAllNotMarkedForDeletion();

    @Query("SELECT q FROM Question q WHERE q.markedForDeletion = true")
    List<Question> findAllMarkedForDeletion();

    List<Question> findByCategoryIdIn(Set<Long> categories);

    @Query("SELECT q FROM Question q WHERE q.markedForDeletion = false")
    List<Question> findAllActive();

   //  @Query("DELETE FROM Question q WHERE q.markedForDeletion = true")
   // void deleteAllMarkedForDeletion();
}
