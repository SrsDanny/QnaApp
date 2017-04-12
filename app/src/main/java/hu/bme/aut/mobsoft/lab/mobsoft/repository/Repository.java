package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;

public interface Repository {

    void open(Context context);
    void close();

    long saveQuestion(Question question);
    Question getQuestion(long id);
    List<Question> getQuestions(String query, SortBy sortBy);

    long saveAnswer(Answer answer);
    List<Answer> getAnswersFor(long id);

    void rateAnswer(Rating rating);
}
