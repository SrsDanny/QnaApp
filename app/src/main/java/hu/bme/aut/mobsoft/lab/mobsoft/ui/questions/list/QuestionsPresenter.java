package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.GetQuestionsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class QuestionsPresenter extends Presenter<QuestionsScreen> {
    @Inject
    Executor executor;

    @Inject
    EventBus bus;

    @Inject
    QuestionsInteractor questionsInteractor;

    private String query = "";
    private SortBy sortBy = null;

    @Override
    public void attachScreen(QuestionsScreen screen) {
        super.attachScreen(screen);
        injector.inject(this);
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
    }

    public void questionSelected(int questionId) {
        screen.showAnswersFor(questionId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetQuestionsEvent(GetQuestionsEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getMessage());
            }
        } else if(screen != null) {
            List<Question> questions = event.getQuestions();
            screen.showQuestions(questions);
        }
    }

    public void getQuestions() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.getQuestions(query, sortBy);
            }
        });
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }
}
