package hu.bme.aut.mobsoft.lab.mobsoft;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.InteractorModule;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.RepositoryModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.UIModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.login.LoginActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;

@Singleton
@Component(modules = {UIModule.class, RepositoryModule.class, InteractorModule.class})
public interface MobSoftApplicationComponent {
    void inject(MobSoftApplication mobSoftApplication);

    void inject(LoginActivity loginActivity);
    void inject(CreateNewAnswerActivity createNewAnswerActivity);
    void inject(AnswersActivity answersActivity);
    void inject(CreateNewQuestionActivity createNewQuestionActivity);
    void inject(QuestionsActivity questionsActivity);

    void inject(QuestionsPresenter questionsPresenter);
    void inject(CreateNewQuestionPresenter createNewQuestionPresenter);

    void inject(QuestionsInteractor questionsInteractor);
}