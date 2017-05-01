package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public interface QuestionsScreen {
    void showQuestions(List<Question> questions);
    void showMessage(String message);
}
