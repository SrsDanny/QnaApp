package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.GetQuestionsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class QuestionsPresenter extends Presenter<QuestionsScreen> {
    private Executor executor;
    private EventBus bus;
    private QuestionsInteractor questionsInteractor;

    private String query = "";
    private SortBy sortBy = null;

    public QuestionsPresenter(Executor executor, EventBus bus,
                              QuestionsInteractor questionsInteractor) {
        this.executor = executor;
        this.bus = bus;
        this.questionsInteractor = questionsInteractor;
    }

    @Override
    public void attachScreen(QuestionsScreen screen) {
        super.attachScreen(screen);
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
    }

    public void getQuestions() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.getQuestions(query, sortBy);
            }
        });
    }

    public void updateQuestions() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.updateQuestions(query, sortBy);
            }
        });
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
