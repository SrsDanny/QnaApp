package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public class QuestionsActivity extends AppCompatActivity implements QuestionsScreen {

    @Inject
    QuestionsPresenter questionsPresenter;

    @BindView(R.id.questionsRecyclerView)
    RecyclerView questionsRecyclerView;
    private QuestionsAdapter questionsAdapter;
    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        questionsRecyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(lm);

        questionList = new ArrayList<>();
        questionsAdapter = new QuestionsAdapter(questionList);
        questionsRecyclerView.setAdapter(questionsAdapter);


        MobSoftApplication.injector.inject(this);
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
