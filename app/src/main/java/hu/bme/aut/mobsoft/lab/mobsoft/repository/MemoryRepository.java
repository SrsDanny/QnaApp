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

        Question question = new Question(1L, "How to Java?", "I need to know how to Java! Pls help!");
        questions.add(question);
        answers.add(new Answer(1L, 1L, "Why would you?", "It's a #$%@ language, learn C!", 22));
        answers.add(new Answer(2L, 1L, "Lol, just don't...", "Learn Scala or some other real language you moron!", -3));
        question.setNumberOfAnswers(2);

        question = new Question(2L, "How to Android?", "I literally have no idea how to make apps. Can you tell me please how do I begin?");
        questions.add(question);
        answers.add(new Answer(3L, 2L, "Learn Java first", "Learn to code in Java then move on to Android and...", -1));
        answers.add(new Answer(4L, 2L, "One does not simply...", "One does not simply learn to make Android apps.", 42));
        question.setNumberOfAnswers(2);

        questions.add(new Question(3L, "Sample question with an unreasonably long title, that should be cut down to fit in a single line, so this part should not even be visible", "Sample description"));
        questions.add(new Question(4L, "Sample question", "Sample description"));
        questions.add(new Question(5L, "Sample question", "Sample description"));
        questions.add(new Question(6L, "Sample question", "Sample description"));
        questions.add(new Question(7L, "Sample question", "Sample description"));
        questions.add(new Question(8L, "Sample question", "Sample description"));
        questions.add(new Question(9L, "Sample question", "Sample description"));
        questions.add(new Question(10L, "Sample question", "Sample description"));
        questions.add(new Question(11L, "Sample question", "Sample description"));
        questions.add(new Question(12L, "Sample question", "Sample description"));
        questions.add(new Question(13L, "Sample question", "Sample description"));
        questions.add(new Question(14L, "Sample question", "Sample description"));
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
        questions.add(question);
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
    public void saveOrReplaceQuestions(List<Question> newQuestions) {
        for(Question newQuestion : newQuestions) {
            boolean inserted = false;
            for (int i = 0; i < questions.size(); ++i) {
                if(questions.get(i).equals(newQuestion)){
                    questions.set(i, newQuestion);
                    inserted = true;
                    break;
                }
            }
            if(!inserted){
                questions.add(newQuestion);
            }
        }
    }

    @Override
    public void saveAnswer(Answer answer) {
        boolean found = false;
        for(Question question : questions){
            if(question.getId() == answer.getQuestionId()){
                question.increaseNumberOfAnswers();
                found = true;
                break;
            }
        }
        if(!found)
            throw new IllegalArgumentException("Question could not be found for answer!");

        final long maxId = Collections.max(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer left, Answer right) {
                return (int)(left.getId() - right.getId());
            }
        }).getId();
        final long newId = maxId + 1;

        answer.setId(newId);
        answers.add(answer);
    }

    @Override
    public List<Answer> getAnswersForId(long id) {
        List<Answer> returnAnswers = new ArrayList<>();
        for(Answer answer : answers) {
            if(answer.getQuestionId() == id) {
                returnAnswers.add(answer);
            }
        }
        return returnAnswers;
    }

    @Override
    public void saveOrReplaceAnswers(List<Answer> newAnswers) {
        for(Answer newAnswer : newAnswers){
            boolean inserted = false;
            for (int i = 0; i < answers.size(); i++) {
                if(answers.get(i).equals(newAnswer)){
                    answers.set(i, newAnswer);
                    inserted = true;
                    break;
                }
            }
            if(!inserted){
                answers.add(newAnswer);
            }
        }
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
