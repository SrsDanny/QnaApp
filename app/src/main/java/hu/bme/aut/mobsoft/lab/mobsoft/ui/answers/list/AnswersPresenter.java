package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.AnswersInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.DetailsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.RatingAppliedEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

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
        injector.inject(this);
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
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

    public void updateDetails(final long id) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                answersInteractor.updateDetailsFor(id);
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
            screen.ratingSuccessful(event.getRating());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetDetailsEvent(DetailsEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getLocalizedMessage());
            }
        } else if(screen != null) {
            screen.showQuestion(event.getQuestion());
            screen.showAnswers(event.getAnswers());
        }
    }
}
