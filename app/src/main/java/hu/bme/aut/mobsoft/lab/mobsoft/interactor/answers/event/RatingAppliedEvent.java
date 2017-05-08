package hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event;


import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;

public class RatingAppliedEvent {
    private Rating rating;
    private Throwable throwable;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
