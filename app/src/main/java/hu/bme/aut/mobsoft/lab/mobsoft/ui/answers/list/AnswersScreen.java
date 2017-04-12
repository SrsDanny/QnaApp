package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public interface AnswersScreen {
    void showDetails(Question question, List<Answer> answers);

    void showMessage(String message);
}
