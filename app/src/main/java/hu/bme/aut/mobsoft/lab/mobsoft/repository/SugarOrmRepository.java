package hu.bme.aut.mobsoft.lab.mobsoft.repository;

import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;

public class SugarOrmRepository implements Repository {

    @Override
    public void open(Context context) {
        SugarContext.init(context);
    }

    @Override
    public void close() {
        SugarContext.terminate();
    }

    @Override
    public long saveQuestion(Question question) {
        return SugarRecord.save(question);
    }

    @Override
    public Question getQuestion(long id) {
        return SugarRecord.findById(Question.class, id);
    }

    @Override
    public List<Question> getQuestions(String query, SortBy sortBy) {
        Select<Question> select = Select.from(Question.class);
        if(query != null && !query.isEmpty()) {
            query = "%" + query + "%";
            select.where(Condition.prop("title").like(query))
                    .or(Condition.prop("description").like(query));
        }
        if(sortBy != null) {
            String orderByString = sortBy.getWhat().toString();
            if(!sortBy.isAscending()) {
                orderByString = orderByString + " DESC";
            }
            select.orderBy(orderByString);
        }
        return select.list();
    }

    @Override
    public void saveOrReplaceQuestions(List<Question> questions) {
        SugarRecord.saveInTx(questions);
    }

    @Override
    public void saveAnswer(Answer answer) {
        Question question = SugarRecord.findById(Question.class, answer.getQuestionId());
        question.increaseNumberOfAnswers();
        ArrayList<Object> objectsToSave = new ArrayList<>(2);
        objectsToSave.add(question);
        objectsToSave.add(answer);
        SugarRecord.saveInTx(objectsToSave);
    }

    @Override
    public List<Answer> getAnswersFor(long id) {
        return Select.from(Answer.class)
                .where(Condition.prop("questionId").eq(id))
                .list();
    }

    @Override
    public void saveOrReplaceAnswers(List<Answer> answers) {
        SugarRecord.saveInTx(answers);
    }

    @Override
    public void rateAnswer(Rating rating) {
        Answer answer = SugarRecord.findById(Answer.class, rating.getAnswerId());
        int newRating = answer.getRating();
        switch (rating.getVote()) {
            case UPVOTE:
                newRating = newRating + 1;
                break;
            case DOWNVOTE:
                newRating = newRating - 1;
                break;
        }
        answer.setRating(newRating);
        SugarRecord.save(answer);
    }
}
