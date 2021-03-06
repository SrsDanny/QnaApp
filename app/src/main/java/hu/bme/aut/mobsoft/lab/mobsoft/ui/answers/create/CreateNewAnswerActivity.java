package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;

public class CreateNewAnswerActivity extends AppCompatActivity implements CreateNewAnswerScreen {
    public static final String QUESTION_ID_KEY = "questionId";

    private long questionId;

    @Inject
    CreateNewAnswerPresenter createNewAnswerPresenter;

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

        Intent intent = getIntent();
        if(!intent.hasExtra(QUESTION_ID_KEY))
            throw new IllegalArgumentException("Question ID missing from Intent extras.");
        questionId = intent.getLongExtra(QUESTION_ID_KEY, 0);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                Answer answer = new Answer(questionId, title, description);
                createNewAnswerPresenter.createNewAnswer(answer);
                createButton.setEnabled(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNewAnswerPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        createNewAnswerPresenter.detachScreen();
    }

    @Override
    public void answerCreated() {
        finish();
    }

    @Override
    public void showMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
