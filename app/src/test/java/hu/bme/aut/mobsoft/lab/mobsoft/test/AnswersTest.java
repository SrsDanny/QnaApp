package hu.bme.aut.mobsoft.lab.mobsoft.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.List;

import hu.bme.aut.mobsoft.lab.mobsoft.BuildConfig;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list.AnswersScreen;
import hu.bme.aut.mobsoft.lab.mobsoft.utils.RobolectricDaggerTestRunner;

import static hu.bme.aut.mobsoft.lab.mobsoft.TestHelper.setTestInjector;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AnswersTest {
    private AnswersPresenter presenter;
    private AnswersScreen screen;

    @Captor
    ArgumentCaptor<List<Answer>> answersCaptor;

    @Captor
    ArgumentCaptor<Question> questionCaptor;

    @Captor
    ArgumentCaptor<Rating> ratingCaptor;

    @Before
    public void setup() {
        setTestInjector();
        presenter = new AnswersPresenter();
        screen = mock(AnswersScreen.class);
        presenter.attachScreen(screen);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRateAnswer(){
        final Rating rating = new Rating(Rating.VoteType.UPVOTE, 11);
        presenter.rateAnswer(rating);
        verify(screen, times(1)).ratingSuccessful(ratingCaptor.capture());
        assertEquals(rating, ratingCaptor.getValue());
        verify(screen, never()).showMessage(anyString());
    }

    @Test
    public void testGetDetails(){
        presenter.getDetails(0);
        verify(screen, times(1)).showAnswers(answersCaptor.capture());
        verify(screen, times(1)).showQuestion(questionCaptor.capture());
        verify(screen, never()).showMessage(anyString());

        assertEquals(2, answersCaptor.getValue().size());
        assertEquals(Long.valueOf(0), questionCaptor.getValue().getId());
    }

    @After
    public void tearDown(){
        presenter.detachScreen();
    }
}
