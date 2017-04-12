package hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event;


public class SaveAnswerEvent {
    private long answerId;
    private Throwable throwable;

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
