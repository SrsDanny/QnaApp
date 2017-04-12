package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public class AnswersActivity extends AppCompatActivity implements AnswersScreen {

    @Inject
    AnswersPresenter answersPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        MobSoftApplication.injector.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        answersPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        answersPresenter.detachScreen();
    }

    @Override
    public void showDetails(Question question, List<Answer> answers) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
