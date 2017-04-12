package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;

public class MemoryRepository implements Repository {
    @Override
    public void open(Context context) {

    }

    @Override
    public void close() {

    }

    @Override
    public long saveQuestion(Question question) {
        return 0;
    }

    @Override
    public Question getQuestion(long id) {
        return null;
    }

    @Override
    public List<Question> getQuestions(String query, SortBy sortBy) {
        return null;
    }

    @Override
    public long saveAnswer(Answer answer) {
        return 0;
    }

    @Override
    public List<Answer> getAnswersFor(Long id) {
        return null;
    }

    @Override
    public void rateAnswer(Rating positiveRating) {

    }
}
