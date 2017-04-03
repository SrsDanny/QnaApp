package hu.bme.aut.mobsoft.lab.mobsoft.interactor.question;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.GetQuestionsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.question.event.SaveQuestionEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class QuestionsInteractor {

    @Inject
    private Repository repository;

    @Inject
    private EventBus bus;

    public QuestionsInteractor() {
        injector.inject(this);
    }

    public void getQuestions() {
        GetQuestionsEvent event = new GetQuestionsEvent();
        try {
            final List<Question> questions = repository.getQuestions();
            event.setQuestions(questions);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void searchQuestions(String query) {
        GetQuestionsEvent event = new GetQuestionsEvent();
        try {
            final List<Question> questions = repository.searchQuestions(query);
            event.setQuestions(questions);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
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
}
