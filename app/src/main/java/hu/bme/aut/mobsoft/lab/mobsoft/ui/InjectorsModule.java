package hu.bme.aut.mobsoft.lab.mobsoft.ui;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsActivity;

@Module
abstract public class InjectorsModule {
    @ContributesAndroidInjector
    abstract QuestionsActivity contributeQuestionsActivityInjector();

    @ContributesAndroidInjector
    abstract CreateNewQuestionActivity contributeCreateNewQuestionActivityInjector();

    @ContributesAndroidInjector
    abstract AnswersActivity contributeAnswersActivityInjector();

    @ContributesAndroidInjector
    abstract CreateNewAnswerActivity contributeCreateNewAnswerActivityInjector();
}
