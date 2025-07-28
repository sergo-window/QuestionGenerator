package org.skypro.question_generator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.question_generator.domain.Question;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    @Test
    void add_shouldAddQuestionAndReturnIt() {
        String questionText = "What is Java?";
        String answer = "Programming language";

        Question result = javaQuestionService.add(questionText, answer);

        assertEquals(questionText, result.getQuestion());
        assertEquals(answer, result.getAnswer());
        assertTrue(javaQuestionService.getAll().contains(result));
    }

    @Test
    void remove_shouldRemoveQuestionAndReturnIt() {
        Question question = javaQuestionService.add("Test question", "Test answer");

        Question result = javaQuestionService.remove(question.getQuestion(), question.getAnswer());

        assertEquals(question, result);
        assertFalse(javaQuestionService.getAll().contains(question));
    }

    @Test
    void remove_shouldReturnNullWhenQuestionNotExists() {
        Question result = javaQuestionService.remove("Non-existent", "Question");

        assertNull(result);
    }

    @Test
    void getAll_shouldReturnCopyOfCollection() {
        Question question = javaQuestionService.add("Q1", "A1");
        Collection<Question> firstCall = javaQuestionService.getAll();

        javaQuestionService.add("Q2", "A2");
        Collection<Question> secondCall = javaQuestionService.getAll();

        assertEquals(1, firstCall.size());
        assertEquals(2, secondCall.size());
        assertNotSame(firstCall, secondCall);
    }

    @Test
    void getRandomQuestion_shouldReturnQuestionWhenNotEmpty() {
        Question question = javaQuestionService.add("Q", "A");

        Question result = javaQuestionService.getRandomQuestion();

        assertNotNull(result);
    }

    @Test
    void getRandomQuestion_shouldReturnNullWhenEmpty() {
        assertNull(javaQuestionService.getRandomQuestion());
    }
}
