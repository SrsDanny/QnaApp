package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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

    BiMap<Long, Answer> answers;
    BiMap<Long, Question> questions;

    @Override
    public void open(Context context) {
        questions = HashBiMap.create();
        answers = HashBiMap.create();

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
        Long id = question.getId();
        if(id == null)
            id = questions.inverse().get(question);
        if(id == null) {
            try {
                id = Collections.max(questions.keySet()) + 1;
            } catch (NoSuchElementException e) {
                id = 0L;
            }
        }
        question.setId(id);
        //noinspection ResultOfMethodCallIgnored
        questions.forcePut(id, question);
        return id;
    }

    @Override
    public Question getQuestion(long id) {
        return questions.get(id);
    }

    @Override
    public List<Question> getQuestions(String query, final SortBy sortBy) {
        List<Question> returnQuestions;

        if(query != null && !query.isEmpty()) {
            returnQuestions = new ArrayList<>();
            for(Question question : questions.values()) {
                if(question.getTitle().toLowerCase().contains(query.toLowerCase())
                        || question.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    returnQuestions.add(question);
                }
            }
        } else {
            returnQuestions = new ArrayList<>(questions.values());
        }

        if(sortBy != null) {
            Collections.sort(returnQuestions, new Comparator<Question>() {
                @Override
                public int compare(Question o1, Question o2) {
                    switch (sortBy.getWhat()) {
                        case TITLE:
                            if(sortBy.isAscending()) {
                                return o1.getTitle().compareTo(o2.getTitle());
                            } else {
                                return o2.getTitle().compareTo(o1.getTitle());
                            }
                        case NUMBER_OF_ANSWERS:
                            if(sortBy.isAscending()) {
                                return o1.getNumberOfAnswers() - o2.getNumberOfAnswers();
                            } else {
                                return o2.getNumberOfAnswers() - o1.getNumberOfAnswers();
                            }
                    }
                    return 0;
                }
            });
        }

        return returnQuestions;
    }

    @Override
    public void saveQuestions(List<Question> newQuestions) {
        for(Question newQuestion : newQuestions) {
            //noinspection ResultOfMethodCallIgnored
            questions.forcePut(newQuestion.getId(), newQuestion);
        }
    }

    @Override
    public void saveAnswer(Answer answer) {
        Question question = questions.get(answer.getQuestionId());
        if(question == null)
            throw new IllegalArgumentException("Question could not be found for answer!");
        question.increaseNumberOfAnswers();

        Long id = answer.getId();
        if(id == null)
            id = answers.inverse().get(answer);
        if(id == null){
            try {
                id = Collections.max(answers.keySet()) + 1;
            } catch (NoSuchElementException e) {
                id = 0L;
            }
        }
        answer.setId(id);
        //noinspection ResultOfMethodCallIgnored
        answers.forcePut(id, answer);
    }

    @Override
    public List<Answer> getAnswersForId(long id) {
        List<Answer> returnAnswers = new ArrayList<>();
        for(Answer answer : answers.values()) {
            if(answer.getQuestionId() == id) {
                returnAnswers.add(answer);
            }
        }
        return returnAnswers;
    }

    @Override
    public void saveAnswers(List<Answer> newAnswers) {
        for(Answer newAnswer : newAnswers){
            //noinspection ResultOfMethodCallIgnored
            answers.forcePut(newAnswer.getId(), newAnswer);
        }
    }

    @Override
    public void rateAnswer(Rating rating) {
        Answer answer = answers.get(rating.getAnswerId());
        if(answer == null)
            throw new IllegalArgumentException("Answer with given ID does not exist");

        answer.addRating(rating.getVote());
    }
}
