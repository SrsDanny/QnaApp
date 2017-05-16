package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerActivity;

public class AnswersActivity extends AppCompatActivity implements AnswersScreen,
        RateDialogFragment.RateDialogListener {

    private Tracker mTracker;
    private String name = "QuestionDetails";

    public static final String QUESTION_ID_KEY = "questionId";

    private long questionId;

    @Inject
    AnswersPresenter answersPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.answers_recyclerview) RecyclerView answersRecyclerView;
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_description) TextView questionDescription;
    @BindView(R.id.answers_count) TextView answersCountText;
    @BindView(R.id.create_new_answer_fab) FloatingActionButton createNewAnswerButton;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

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
        answersAdapter = new AnswersAdapter(answerList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = answersRecyclerView.getChildLayoutPosition(view);
                final long answerId = answerList.get(position).getId();
                DialogFragment dialog = new RateDialogFragment();
                Bundle args = new Bundle();
                args.putLong(RateDialogFragment.ANSWER_ID_KEY, answerId);
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "rating");
            }
        });
        answersRecyclerView.setAdapter(answersAdapter);

        final DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(answersRecyclerView.getContext(),
                        linearLayoutManager.getOrientation());
        answersRecyclerView.addItemDecoration(dividerItemDecoration);

        answersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    createNewAnswerButton.show();
                } else {
                    createNewAnswerButton.hide();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        createNewAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswersActivity.this, CreateNewAnswerActivity.class);
                intent.putExtra(CreateNewAnswerActivity.QUESTION_ID_KEY, questionId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                answersPresenter.updateDetails(questionId);
            }
        });

        final MobSoftApplication application = (MobSoftApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onStart() {
        super.onStart();
        answersPresenter.attachScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        answerList.clear();
        answerList.addAll(answers);
        answersAdapter.notifyDataSetChanged();
    }

    @Override
    public void ratingSuccessful(Rating rating) {
        int pos;
        for (pos = 0; pos < answerList.size(); pos++) {
            Answer answer = answerList.get(pos);
            if (answer.getId() == rating.getAnswerId()) {
                answer.addRating(rating.getVote());
                break;
            }
        }
        answersAdapter.notifyItemChanged(pos);
        showMessage("Answer rated");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ratingReceived(Rating rating) {
        answersPresenter.rateAnswer(rating);
    }
}
