package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.AnswersInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.SaveAnswerEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class CreateNewAnswerPresenter extends Presenter<CreateNewAnswerScreen> {

    @Inject
    EventBus bus;

    @Inject
    Executor executor;

    @Inject
    AnswersInteractor answersInteractor;

    @Override
    public void attachScreen(CreateNewAnswerScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
    }

    public void createNewAnswer(final Answer answer){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                answersInteractor.saveAnswer(answer);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveAnswerEvent(SaveAnswerEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();;
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getLocalizedMessage());
            }
        } else if(screen != null) {
            screen.answerCreated();
        }
    }
}
