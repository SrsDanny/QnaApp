package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public interface Repository {

    void open(Context context);

    void close();

    Long saveQuestion(Question question);
    Question getQuestion(Long id);
    List<Question> getQuestions();
    List<Question> searchQuestions(String query);

    void saveAnswer(Answer answer);
    List<Answer> getAnswersForQuestion(Long id);
    void rateAnswer(boolean positiveRating);
}
