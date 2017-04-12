package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.createnew;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.SaveQuestionEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class CreateNewQuestionPresenter extends Presenter<CreateNewQuestionScreen> {

    @Inject
    QuestionsInteractor questionsInteractor;

    @Inject
    EventBus bus;

    @Inject
    Executor executor;

    @Override
    public void attachScreen(CreateNewQuestionScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
    }

    public void createNewQuestion(final Question question) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.saveQuestion(question);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveQuestionEvent(SaveQuestionEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getLocalizedMessage());
            }
        } else if(screen != null) {
           screen.questionCreated(event.getQuestionId());
        }
    }
}
