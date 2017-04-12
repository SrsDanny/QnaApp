package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.AnswersInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.GetDetailsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.RatingAppliedEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

public class AnswersPresenter extends Presenter<AnswersScreen> {

    @Inject
    Executor executor;

    @Inject
    AnswersInteractor answersInteractor;

    @Inject
    EventBus bus;

    @Override
    public void attachScreen(AnswersScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void rateAnswer(final Rating rating) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                answersInteractor.rateAnswer(rating);
            }
        });
    }
    public void getDetails(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                answersInteractor.getDetailsFor(id);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRatingAppliedEvent(RatingAppliedEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getLocalizedMessage());
            }
        } else if(screen != null) {
            screen.showMessage("Answer rated");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetDetailsEvent(GetDetailsEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getLocalizedMessage());
            }
        } else if(screen != null) {
            screen.showDetails(event.getQuestion(), event.getAnswers());
        }
    }
}
