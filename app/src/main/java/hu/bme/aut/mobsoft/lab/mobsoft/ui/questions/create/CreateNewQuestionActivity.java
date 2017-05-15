package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersActivity;

public class CreateNewQuestionActivity extends AppCompatActivity implements CreateNewQuestionScreen {

    private Tracker mTracker;
    private String name = "CreateNewQuestion";

    @Inject
    CreateNewQuestionPresenter createNewQuestionPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.create_button) Button createButton;
    @BindView(R.id.title_edittext) EditText titleEditText;
    @BindView(R.id.description_edittext) EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        ButterKnife.bind(this);
        MobSoftApplication.injector.inject(this);

        setSupportActionBar(toolbar);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                Question question = new Question(title, description);
                createNewQuestionPresenter.createNewQuestion(question);
                createButton.setEnabled(false);
            }
        });

        final MobSoftApplication application = (MobSoftApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNewQuestionPresenter.attachScreen(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onStop() {
        super.onStop();
        createNewQuestionPresenter.detachScreen();
    }

    @Override
    public void questionCreated(Long id) {
        Intent intent = new Intent(CreateNewQuestionActivity.this, AnswersActivity.class);
        intent.putExtra(AnswersActivity.QUESTION_ID_KEY, id);
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
