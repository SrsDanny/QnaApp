package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;

public class MemoryRepository implements Repository {

    private List<Question> questions;
    private List<Answer> answers;

    @Override
    public void open(Context context) {
        questions = new ArrayList<>();
        answers = new ArrayList<>();

        questions.add(new Question(1L, "How to Java?", "I need to know how to Java! Pls help!"));
        answers.add(new Answer(1L, 1L, "Why would you?", "It's a #$%@ language, learn C!", 22));
        answers.add(new Answer(2L, 1L, "Lol, just don't...", "Learn Scala or some other real language you moron!", -3));

        questions.add(new Question(2L, "How to Android?", "I literally have no idea how to make apps. Can you tell me please how do I begin?"));
        answers.add(new Answer(3L, 2L, "Learn Java first", "Learn to code in Java then move on to Android and...", -1));
        answers.add(new Answer(4L, 2L, "One does not simply...", "One does not simply learn to make Android apps.", 42));
    }

    @Override
    public void close() {

    }

    @Override
    public long saveQuestion(Question question) {
        final long maxId = Collections.max(questions, new Comparator<Question>() {
            @Override
            public int compare(Question left, Question right) {
                return (int)(left.getId() - right.getId());
            }
        }).getId();
        final long newId = maxId + 1;

        question.setId(newId);
        return newId;
    }

    @Override
    public Question getQuestion(long id) {
        for(Question question : questions) {
            if(question.getId() == id)
                return question;
        }
        throw new IllegalArgumentException("Non-existent question ID");
    }

    @Override
    public List<Question> getQuestions(String query, final SortBy sortBy) {
        List<Question> returnQuestions;

        if(query != null) {
            returnQuestions = new ArrayList<>();
            for(Question question : questions) {
                if(question.getTitle().toLowerCase().contains(query.toLowerCase())
                        || question.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    returnQuestions.add(question);
                }
            }
        } else {
            returnQuestions = new ArrayList<>(questions);
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
    public long saveAnswer(Answer answer) {
        final long maxId = Collections.max(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer left, Answer right) {
                return (int)(left.getId() - right.getId());
            }
        }).getId();
        final long newId = maxId + 1;

        answer.setId(newId);
        return newId;
    }

    @Override
    public List<Answer> getAnswersFor(long id) {
        List<Answer> returnAnswers = new ArrayList<>();
        for(Answer answer : answers) {
            if(answer.getQuestionId() == id) {
                returnAnswers.add(answer);
            }
        }
        return returnAnswers;
    }

    @Override
    public void rateAnswer(Rating rating) {
        Answer answer = null;
        for(Answer a : answers) {
            if(a.getId() == rating.getAnswerId()) {
                answer = a;
                break;
            }
        }

        if(answer == null)
            throw new IllegalArgumentException("Answer with given ID does not exist");

        int newRating = answer.getRating();
        switch (rating.getVote()) {
            case UPVOTE:
                newRating = newRating + 1;
                break;
            case DOWNVOTE:
                newRating = newRating - 1;
                break;
        }
        answer.setRating(newRating);
    }
}
