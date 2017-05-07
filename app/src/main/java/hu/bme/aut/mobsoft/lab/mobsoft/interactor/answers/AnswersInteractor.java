package hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.GetDetailsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.RatingAppliedEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.SaveAnswerEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class AnswersInteractor {

    @Inject
    Repository repository;

    @Inject
    EventBus bus;

    public AnswersInteractor() {
        injector.inject(this);
    }

    public void saveAnswer(Answer answer){
        SaveAnswerEvent event = new SaveAnswerEvent();
        try {
            repository.saveAnswer(answer);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void rateAnswer(Rating rating) {
        RatingAppliedEvent event = new RatingAppliedEvent();
        try {
            repository.rateAnswer(rating);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void getDetailsFor(long id) {
        GetDetailsEvent event = new GetDetailsEvent();
        try {
            final Question question = repository.getQuestion(id);
            final List<Answer> answers = repository.getAnswersForId(id);
            event.setQuestion(question);
            event.setAnswers(answers);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }
}
