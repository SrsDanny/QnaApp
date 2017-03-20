package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;

public class QuestionsActivity extends AppCompatActivity implements QuestionsScreen {

    @Inject
    QuestionsPresenter questionsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        MobSoftApplication.injector.inject(this);
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
    public void showAnswersFor(int questionId) {
        // Navigate to Answers view
    }

    @Override
    public void showQuestions(/*List<Question> questions*/) {
        // Display questions
    }
}
