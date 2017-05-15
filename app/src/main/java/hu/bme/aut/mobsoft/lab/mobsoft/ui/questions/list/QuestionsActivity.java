package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.SortBy;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionActivity;

public class QuestionsActivity extends AppCompatActivity implements QuestionsScreen,
        SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private Tracker mTracker;
    private String name = "QuestionsList";

    @Inject
    QuestionsPresenter questionsPresenter;

    @BindView(R.id.questions_recyclerview) RecyclerView questionsRecyclerView;
    private QuestionsAdapter questionsAdapter;
    private List<Question> questionList;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private Spinner sortSpinner;
    private CheckBox ascendingCheckBox;
    private SearchView searchView;

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.create_new_question_fab) FloatingActionButton createNewQuestionButton;

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

        questionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    createNewQuestionButton.show();
                } else {
                    createNewQuestionButton.hide();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        createNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Create new activity")
                        .build());

                Intent intent = new Intent(QuestionsActivity.this, CreateNewQuestionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                questionsPresenter.updateQuestions();
            }
        });

        final MobSoftApplication application = (MobSoftApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
                getSupportActionBar().getThemedContext(), R.array.sort_spinner_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        ascendingCheckBox = (CheckBox) actionView.findViewById(R.id.ascending_checkbox);

        SortBy sortBy = questionsPresenter.getSortBy();
        if(sortBy != null)
        {
            final int pos = adapter.getPosition(sortBy.getWhat().toCustomString());
            sortSpinner.setSelection(pos);
            ascendingCheckBox.setSelected(sortBy.isAscending());
        }
        sortSpinner.setOnItemSelectedListener(this);
        ascendingCheckBox.setOnCheckedChangeListener(this);

        searchView = (SearchView) menu.findItem(R.id.searchAction).getActionView();
        searchView.setOnQueryTextListener(QuestionsActivity.this);
        final String query = questionsPresenter.getQuery();
        if(query != null)
        {
            searchView.setQuery(query, false);
        }

        MenuItem crashButton = menu.findItem(R.id.crashMeButton);
        crashButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                throw new RuntimeException("This is a crash!");
            }
        });

        return true;
    }

    @Override
    public void showQuestions(List<Question> questions) {
        if(swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }

        questionList.clear();
        questionList.addAll(questions);
        questionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        questionsPresenter.setQuery(query);
        questionsPresenter.getQuestions();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        questionsPresenter.setQuery(newText);
        questionsPresenter.getQuestions();

        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SortBy sortBy = questionsPresenter.getSortBy();
        if(sortBy == null) sortBy = new SortBy(SortBy.What.TITLE, false);
        if(i == 0)
            sortBy.setWhat(SortBy.What.TITLE);
        if(i == 1)
            sortBy.setWhat(SortBy.What.NUMBER_OF_ANSWERS);
        questionsPresenter.setSortBy(sortBy);
        questionsPresenter.getQuestions();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SortBy sortBy = questionsPresenter.getSortBy();
        if(sortBy == null) sortBy = new SortBy(SortBy.What.TITLE, false);
        sortBy.setAscending(b);
        questionsPresenter.setSortBy(sortBy);
        questionsPresenter.getQuestions();
    }
}
