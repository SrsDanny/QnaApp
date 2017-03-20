package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.createnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;

public class CreateNewQuestionActivity extends AppCompatActivity implements CreateNewQuestionScreen {

    @Inject
    CreateNewQuestionPresenter createNewQuestionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_question);

        MobSoftApplication.injector.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNewQuestionPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        createNewQuestionPresenter.detachScreen();
    }
}
