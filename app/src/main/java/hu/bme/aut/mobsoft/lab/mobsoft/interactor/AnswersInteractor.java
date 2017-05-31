package hu.bme.aut.mobsoft.lab.mobsoft.interactor;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.interactor.answers.event.DetailsEvent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.network.AnswerApi;
import hu.bme.aut.mobsoft.lab.mobsoft.network.QuestionApi;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import io.reactivex.Completable;
import retrofit2.Response;

public class AnswersInteractor {

    private Repository repository;
    private AnswerApi answerApi;
    private QuestionApi questionApi;

    public AnswersInteractor(Repository repository, AnswerApi answerApi, QuestionApi questionApi) {
        this.repository = repository;
        this.answerApi = answerApi;
        this.questionApi = questionApi;
    }

    public Completable saveAnswer(Answer answer) {
        return answerApi.createAnswer(answer)
                .doOnNext(answer::setId)
                .doOnComplete(() -> repository.saveAnswer(answer))
                .ignoreElements();
    }

    public Completable rateAnswer(Rating rating) {
        return answerApi.rateAnswer(rating)
                .doOnComplete(() -> repository.rateAnswer(rating))
                .ignoreElements();
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

    public void updateDetailsFor(long id) {
        DetailsEvent event = new DetailsEvent();
        try {
            final Response<List<Answer>> answersResponse = answerApi.getAnswersForId(id).execute();
            if (!answersResponse.isSuccessful()) {
                if (answersResponse.code() == 404)
                    throw new IllegalArgumentException("Question not found for ID");
                else
                    throw new Exception("Failed to get answers, request unsuccessful");
            }
            final List<Answer> answers = answersResponse.body();

            final Response<Question> questionResponse = questionApi.getQuestionById(id).execute();
            if (!questionResponse.isSuccessful()) {
                if (answersResponse.code() == 404)
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
