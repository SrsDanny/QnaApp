package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;

public class QuestionsActivity extends AppCompatActivity implements QuestionsScreen {

    @Inject
    QuestionsPresenter questionsPresenter;

    @BindView(R.id.questionsRecyclerView) RecyclerView questionsRecyclerView;
    private QuestionsAdapter questionsAdapter;
    private List<Question> questionList;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private Spinner sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        MobSoftApplication.injector.inject(this);

        setSupportActionBar(toolbar);

        questionsRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(linearLayoutManager);
        questionList = new ArrayList<>();
        questionsAdapter = new QuestionsAdapter(questionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int itemPosition = questionsRecyclerView.getChildLayoutPosition(v);
                final long questionId = questionList.get(itemPosition).getId();
                Intent intent = new Intent(QuestionsActivity.this, AnswersActivity.class);
                intent.putExtra(AnswersActivity.QUESTION_ID_KEY, questionId);
                startActivity(intent);
            }
        });

        questionsRecyclerView.setAdapter(questionsAdapter);
        final DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(questionsRecyclerView.getContext(),
                        linearLayoutManager.getOrientation());
        questionsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        questionsPresenter.getQuestions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        questionsPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        questionsPresenter.detachScreen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.questions_menu, menu);
        final View actionView = menu.findItem(R.id.sortAction).getActionView();
        sortSpinner = (Spinner) actionView.findViewById(R.id.sort_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sort_spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        return true;
    }

    @Override
    public void showQuestions(List<Question> questions) {
        questionList.clear();
        questionList.addAll(questions);
        questionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
