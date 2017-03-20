package hu.bme.aut.mobsoft.lab.mobsoft;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.UIModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.createnew.CreateNewAnswerActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.login.LoginActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.createnew.CreateNewQuestionActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsActivity;

@Singleton
@Component(modules = {UIModule.class})
public interface MobSoftApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(CreateNewAnswerActivity createNewAnswerActivity);
    void inject(AnswersActivity answersActivity);
    void inject(CreateNewQuestionActivity createNewQuestionActivity);
    void inject(QuestionsActivity questionsActivity);
}