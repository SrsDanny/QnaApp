package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;

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
}
