package hu.bme.aut.mobsoft.lab.mobsoft;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.UIModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.utils.UiExecutor;

@Module
public class TestModule {

	private final UIModule uIModule;

	public TestModule(Context context) {
		this.uIModule = new UIModule(context);
	}

	@Provides
	public Context provideContext() {
		return uIModule.provideContext();
	}


	@Provides
	public QuestionsPresenter provideQuestionsPresenter() {
		return uIModule.provideQuestionsPresenter();
	}

	@Provides
	public CreateNewQuestionPresenter provideCreateNewQuestionPresenter() {
		return uIModule.provideCreateNewQuestionPresenter();
	}

	@Provides
	public AnswersPresenter provideAnswersPresenter() {
		return uIModule.provideAnswersPresenter();
	}

	@Provides
	public CreateNewAnswerPresenter provideCreateNewAnswerPresenter() {
		return uIModule.provideCreateNewAnswerPresenter();
	}

	@Provides
	@Singleton
	public EventBus provideEventBus() {
		return EventBus.getDefault();
	}

	@Provides
	@Singleton
	public Executor provideNetworkExecutor() {
		return new UiExecutor();
	}


}
