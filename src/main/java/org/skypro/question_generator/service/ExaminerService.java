package org.skypro.question_generator.service;

import org.skypro.question_generator.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(String subject, int amount);
}
