package hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event;


import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public class GetQuestionsEvent {
    private List<Question> questions;
    private Throwable throwable;

    public GetQuestionsEvent() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
