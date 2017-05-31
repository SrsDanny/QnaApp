package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;
import android.support.v4.util.LongSparseArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;

public class MemoryRepository implements Repository {

    private List<Answer> answers;
    private List<Question> questions;

    public MemoryRepository() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    @Override
    public void open(Context context) {
        fillWithTestData();
    }

    private void fillWithTestData() {
        long id;
        Answer answer;

        id = saveQuestion(new Question("How to Java?", "I need to know how to Java! Pls help!"));
        answer = new Answer(id, "Why would you?", "It's a #$%@ language, learn C!");
        answer.setRating(23);
        saveAnswer(answer);
        answer = new Answer(id, "Lol, just don't...", "Learn Scala or some other real language you moron!");
        answer.setRating(-3);
        saveAnswer(answer);

        id = saveQuestion(new Question("How to Android?", "I literally have no idea how to make apps. Can you tell me please how do I begin?"));
        saveAnswer(new Answer(id, "Learn Java first", "Learn to code in Java then move on to Android and..."));
        saveAnswer(new Answer(id, "One does not simply...", "One does not simply learn to make Android apps."));

        id = saveQuestion(new Question("Sample question with an unreasonably long title, that should be cut down to fit in a single line, so this part should not even be visible", "Sample description"));
        for(int i = 0; i < 50; ++i)
            saveAnswer(new Answer(id, "Sample answer title.", "Sample answer description."));

        for(int i = 0; i < 50; ++i)
            saveQuestion(new Question("Sample question", "Sample description"));
    }

    @Override
    public void close() {
    }

    @Override
    public long saveQuestion(Question question) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Question getQuestion(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Question> getQuestions(String query, final SortBy sortBy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveQuestions(List<Question> newQuestions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long saveAnswer(Answer answer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Answer> getAnswersForId(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveAnswers(List<Answer> newAnswers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void rateAnswer(Rating rating) {
        throw new UnsupportedOperationException();
    }
}
