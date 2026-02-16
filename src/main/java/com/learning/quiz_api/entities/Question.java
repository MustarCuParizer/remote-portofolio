package com.learning.quiz_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "questions")
@SQLDelete(sql = "UPDATE questions SET marked_for_deletion = true WHERE id = ?")
@Where(clause = "marked_for_deletion = false")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option", columnDefinition = "TEXT")
    private Set<String> options = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "correct_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "correct_option")
    private Set<Integer> correctOptions = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "marked_for_deletion", nullable = false)
    private boolean markedForDeletion = false;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    private Set<Quiz> quizzes = new HashSet<>();

    public enum QuestionType {
        SINGLE_ANSWER, MULTIPLE_ANSWERS
    }
}
