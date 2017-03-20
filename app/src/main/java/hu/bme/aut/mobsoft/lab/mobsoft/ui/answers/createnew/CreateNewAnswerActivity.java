package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.createnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import hu.bme.aut.mobsoft.lab.mobsoft.MobSoftApplication;
import hu.bme.aut.mobsoft.lab.mobsoft.R;

public class CreateNewAnswerActivity extends AppCompatActivity implements CreateNewAnswerScreen {

    @Inject
    CreateNewAnswerPresenter createNewAnswerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_answer);

        MobSoftApplication.injector.inject(this);
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
}
