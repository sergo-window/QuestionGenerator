package org.skypro.question_generator.controller;

import org.skypro.question_generator.domain.Question;
import org.skypro.question_generator.service.MathQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {
    private final MathQuestionService mathQuestionService;

    public MathQuestionController(MathQuestionService mathQuestionService) {
        this.mathQuestionService = mathQuestionService;
    }

    @GetMapping
    public Collection<Question> getAll() {
        return mathQuestionService.getAll();
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam String question, @RequestParam String answer) {
        return mathQuestionService.add(question, answer);
    }

    @GetMapping("/remove")
    public Question removeQuestion(@RequestParam String question, @RequestParam String answer) {
        return mathQuestionService.remove(question, answer);
    }
}
