package hu.bme.aut.mobsoft.lab.mobsoft.interactor;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.QuestionComparator;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;
import hu.bme.aut.mobsoft.lab.mobsoft.network.QuestionApi;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import io.reactivex.Completable;
import io.reactivex.Observable;

import static hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication.injector;

public class QuestionsInteractor {

    @Inject Repository repository;
    @Inject QuestionApi questionApi;

    public QuestionsInteractor() {
        injector.inject(this);
    }

    public Completable saveQuestion(final Question question) {
        return questionApi.createQuestion(question)
                .doOnNext(question::setId)
                .doOnComplete(() -> repository.saveQuestion(question))
                .ignoreElements();
    }

    public Observable<Question> getQuestions(String query, SortBy sortBy, boolean forceUpdate) {
        final Observable<Question> observable;

        if(forceUpdate) {
            observable = questionApi.getQuestions()
                    .doOnNext(questions -> repository.saveQuestions(questions))
                    .flatMap(Observable::fromIterable)
                    .filter(question -> question.getTitle().toLowerCase().contains(query)
                        || question.getDescription().toLowerCase().contains(query))
                    .sorted(new QuestionComparator(sortBy));
        } else {
            observable = Observable.defer(() ->
                    Observable.fromIterable(repository.getQuestions(query, sortBy)));
        }

        return observable;
    }
}
