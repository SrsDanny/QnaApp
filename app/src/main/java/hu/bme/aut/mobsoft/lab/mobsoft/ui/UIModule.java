package hu.bme.aut.mobsoft.lab.mobsoft.ui;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.login.LoginPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;

@Module
public class UIModule {
    private Context context;

    public UIModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }

    @Provides
    @Singleton
    public CreateNewQuestionPresenter provideCreateNewQuestionPresenter() {
        return new CreateNewQuestionPresenter();
    }

    @Provides
    @Singleton
    public QuestionsPresenter provideQuestionsPresenter() {
        return new QuestionsPresenter();
    }

    @Provides
    @Singleton
    public CreateNewAnswerPresenter provideCreateNewAnswerPresenter() {
        return new CreateNewAnswerPresenter();
    }

    @Provides
    @Singleton
    public AnswersPresenter provideAnswersPresenter() {
        return new AnswersPresenter();
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public Executor provideExecutor() {
        return Executors.newFixedThreadPool(1);
    }
}