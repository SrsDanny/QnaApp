package hu.bme.aut.mobsoft.lab.mobsoft.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import hu.bme.aut.mobsoft.lab.mobsoft.BuildConfig;
import hu.bme.aut.mobsoft.lab.mobsoft.DaggerTestComponent;
import hu.bme.aut.mobsoft.lab.mobsoft.TestComponent;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.repository.Repository;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.PresentersModule;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.create.CreateNewQuestionScreen;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CreateNewQuestionTest {
    private CreateNewQuestionPresenter presenter;
    private Repository repository;
    @Mock CreateNewQuestionScreen screen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestComponent testComponent = DaggerTestComponent.builder()
                .presentersModule(new PresentersModule(null)).build();
        repository = testComponent.repository();
        presenter = testComponent.createNewQuestionPresenter();
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
