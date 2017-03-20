package hu.bme.aut.mobsoft.lab.mobsoft.ui;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.createnew.CreateNewAnswerPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.login.LoginPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.createnew.CreateNewQuestionPresenter;
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

}