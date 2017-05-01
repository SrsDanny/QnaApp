package hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event;


public class SaveAnswerEvent {
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
