package hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.DetailsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.RatingAppliedEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.SaveAnswerEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.network.AnswerApi;
import hu.bme.aut.mobsoft.lab.mobsoft.network.QuestionApi;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import retrofit2.Response;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class AnswersInteractor {

    @Inject
    Repository repository;

    @Inject
    EventBus bus;

    @Inject
    AnswerApi answerApi;

    @Inject
    QuestionApi questionApi;

    public AnswersInteractor() {
        injector.inject(this);
    }

    public void saveAnswer(Answer answer){
        final SaveAnswerEvent event = new SaveAnswerEvent();
        try {
            final Response<Long> saveAnswerResponse = answerApi.createAnswer(answer).execute();
            if(!saveAnswerResponse.isSuccessful()){
                throw new Exception("Answer could not be created");
            }

            answer.setId(saveAnswerResponse.body());
            repository.saveAnswer(answer);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void rateAnswer(Rating rating) {
        final RatingAppliedEvent event = new RatingAppliedEvent();
        try {
            final Response<Void> rateAnswerResponse = answerApi.rateAnswer(rating).execute();
            if(!rateAnswerResponse.isSuccessful()) {
                if(rateAnswerResponse.code() == 404)
                    throw new IllegalArgumentException("Answer not found");
                else
                    throw new Exception("Could not rate answer, request unsuccessful");
            }
            repository.rateAnswer(rating);
            event.setRating(rating);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }

    public void getDetailsFor(long id) {
        final DetailsEvent event = new DetailsEvent();
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

    public void updateDetailsFor(long id){
        DetailsEvent event = new DetailsEvent();
        try{
            final Response<List<Answer>> answersResponse = answerApi.getAnswersForId(id).execute();
            if(!answersResponse.isSuccessful()) {
                if(answersResponse.code() == 404)
                    throw new IllegalArgumentException("Question not found for ID");
                else
                    throw new Exception("Failed to get answers, request unsuccessful");
            }
            final List<Answer> answers = answersResponse.body();

            final Response<Question> questionResponse = questionApi.getQuestionById(id).execute();
            if(!questionResponse.isSuccessful()){
                if(answersResponse.code() == 404)
                    throw new IllegalArgumentException("Question not found for ID");
                else
                    throw new Exception("Failed to get question, request unsuccessful");
            }
            final Question question = questionResponse.body();

            repository.saveAnswers(answers);
            repository.saveQuestion(question);

            event.setAnswers(answers);
            event.setQuestion(question);
        } catch (Exception e) {
            event.setThrowable(e);
        }
        bus.post(event);
    }
}
