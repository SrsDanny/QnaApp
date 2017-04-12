package hu.bme.aut.mobsoft.lab.mobsoft.interactor.question;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.GetQuestionsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.SaveQuestionEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class QuestionsInteractor {

    @Inject
    Repository repository;

    @Inject
    EventBus bus;

    public QuestionsInteractor() {
        injector.inject(this);
    }

    public void saveQuestion(Question question) {
        SaveQuestionEvent event = new SaveQuestionEvent();
        try {
            final Long questionId = repository.saveQuestion(question);
            event.setQuestionId(questionId);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void getQuestions(String query, SortBy sortBy) {
        GetQuestionsEvent event = new GetQuestionsEvent();
        try {
            final List<Question> questions = repository.getQuestions(query, sortBy);
            event.setQuestions(questions);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }
}
