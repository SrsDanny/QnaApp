package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public interface AnswersScreen {
    void showQuestion(Question question);
    void showAnswers(List<Answer> answers);
    void ratingSuccessful(Rating rating);

    void showMessage(String message);
}
