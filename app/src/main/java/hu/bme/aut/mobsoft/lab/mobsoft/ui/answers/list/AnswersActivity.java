package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public class AnswersActivity extends AppCompatActivity implements AnswersScreen {

    public static final String QUESTION_ID_KEY = "questionId";

    long questionId;

    @Inject
    AnswersPresenter answersPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.answers_recyclerview) RecyclerView answersRecyclerView;
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_description) TextView questionDescription;
    @BindView(R.id.answers_count) TextView answersCountText;

    private List<Answer> answerList;
    private AnswersAdapter answersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        ButterKnife.bind(this);
        MobSoftApplication.injector.inject(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(!intent.hasExtra(QUESTION_ID_KEY))
            throw new IllegalArgumentException("Question ID missing from Intent extras.");

        questionId = intent.getLongExtra(QUESTION_ID_KEY, 0);

        answersRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        answersRecyclerView.setLayoutManager(linearLayoutManager);
        answerList = new ArrayList<>();
        answersAdapter = new AnswersAdapter(answerList, null);
        answersRecyclerView.setAdapter(answersAdapter);

        final DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(answersRecyclerView.getContext(),
                        linearLayoutManager.getOrientation());
        answersRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        answersPresenter.attachScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        answersPresenter.getDetails(questionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        answersPresenter.detachScreen();
    }

    @Override
    public void showQuestion(Question question) {
        questionTitle.setText(question.getTitle());
        questionDescription.setText(question.getDescription());
        final int count = question.getNumberOfAnswers();
        if(count == 1) {
            answersCountText.setText(String.format(Locale.getDefault(), "%d Answer", count));
        } else {
            answersCountText.setText(String.format(Locale.getDefault(), "%d Answers", count));
        }
    }

    @Override
    public void showAnswers(List<Answer> answers) {
        answerList.clear();
        answerList.addAll(answers);
        answersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
