package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.QuestionsInteractor;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.GetQuestionsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;
import static hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter.SortBy.ANSWERS;

public class QuestionsPresenter extends Presenter<QuestionsScreen> {

    enum SortBy {
        TITLE,
        ANSWERS
    }

    @Inject
    private Executor executor;

    @Inject
    private EventBus bus;

    @Inject
    private QuestionsInteractor questionsInteractor;

    SortBy sortBy;
    boolean ascending;

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

    public void searchQuestions(final String query) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.searchQuestions(query);
            }
        });
    }

    public void getQuestions() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                questionsInteractor.getQuestions();
            }
        });
    }

    // Sort by ascending order
    public void sortQuestions(SortBy sortBy) {
        sortQuestions(sortBy, true);
    }

    public void sortQuestions(SortBy sortBy, boolean ascending) {
        this.sortBy = sortBy;
        this.ascending = ascending;
        getQuestions();
    }

    public void questionSelected(int questionId) {
        screen.showAnswersFor(questionId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GetQuestionsEvent event) {
        if(event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if(screen != null) {
                screen.showMessage("error: " + event.getThrowable().getMessage());
            }
        } else if(screen != null) {
            List<Question> questions = event.getQuestions();
            if(sortBy != null) {
                Collections.sort(questions, new Comparator<Question>() {
                    @Override
                    public int compare(Question o1, Question o2) {
                        switch (sortBy) {
                            case TITLE:
                                if(ascending) {
                                    return o1.getTitle().compareTo(o2.getTitle());
                                } else {
                                    return o2.getTitle().compareTo(o1.getTitle());
                                }
                            case ANSWERS:
                                if(ascending) {
                                    return o1.getNumberOfAnswers() - o2.getNumberOfAnswers();
                                } else {
                                    return o2.getNumberOfAnswers() - o1.getNumberOfAnswers();
                                }
                        }
                        return 0;
                    }
                });
            }
            screen.showQuestions(questions);
        }
    }
}
