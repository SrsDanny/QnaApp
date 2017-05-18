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
		//TODO
		//return uIModule.provideQuestionsPresenter();
		return null;
	}

	@Provides
	public CreateNewQuestionPresenter provideCreateNewQuestionPresenter() {
		//TODO
		// return uIModule.provideCreateNewQuestionPresenter();
		return null;
	}

	@Provides
	public AnswersPresenter provideAnswersPresenter() {
		//TODO
		// return uIModule.provideAnswersPresenter();
		return null;
	}

	@Provides
	public CreateNewAnswerPresenter provideCreateNewAnswerPresenter() {
		//TODO
		//return uIModule.provideCreateNewAnswerPresenter();\
		return null;
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
