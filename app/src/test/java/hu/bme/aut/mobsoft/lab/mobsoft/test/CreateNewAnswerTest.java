package hu.bme.aut.mobsoft.lab.mobsoft.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.create.CreateNewAnswerScreen;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateNewAnswerTest {
    private CreateNewAnswerPresenter presenter;
    private CreateNewAnswerScreen screen;

    @Before
    public void setup(){
        //TODO
        // presenter = new CreateNewAnswerPresenter();
        screen = mock(CreateNewAnswerScreen.class);
        presenter.attachScreen(screen);
    }

    @Test
    public void testCreateNewAnswer(){
        Answer answer = new Answer();
        presenter.createNewAnswer(answer);
        verify(screen, times(1)).answerCreated();
        verify(screen, never()).showMessage(anyString());
    }

    @After
    public void tearDown(){
        presenter.detachScreen();
    }
}
