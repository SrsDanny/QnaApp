package hu.bme.aut.mobsoft.lab.mobsoft.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import hu.bme.aut.mobsoft.lab.mobsoft.BuildConfig;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionScreen;
import hu.bme.aut.mobsoft.lab.mobsoft.utils.RobolectricDaggerTestRunner;

import static hu.bme.aut.mobsoft.lab.mobsoft.TestHelper.setTestInjector;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CreateNewQuestionTest {
    private CreateNewQuestionPresenter presenter;
    private CreateNewQuestionScreen screen;

    @Before
    public void setup() {
        setTestInjector();
        presenter = new CreateNewQuestionPresenter();
        screen = mock(CreateNewQuestionScreen.class);
        presenter.attachScreen(screen);
    }

    @Test
    public void testCreateNewQuestion(){
        final Question question = new Question();
        presenter.createNewQuestion(question);

        verify(screen, times(1)).questionCreated(42L);
    }

    @After
    public void tearDown(){
        presenter.detachScreen();
    }
}
