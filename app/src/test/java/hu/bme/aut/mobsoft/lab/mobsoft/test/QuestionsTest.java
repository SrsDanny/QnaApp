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
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsPresenter;
import hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list.QuestionsScreen;
import hu.bme.aut.mobsoft.lab.mobsoft.utils.RobolectricDaggerTestRunner;

import static hu.bme.aut.mobsoft.lab.mobsoft.TestHelper.setTestInjector;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class QuestionsTest {

    private QuestionsPresenter questionsPresenter;
    private QuestionsScreen questionsScreen;

    @Captor
    ArgumentCaptor<List<Question>> questionsCaptor;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        questionsPresenter = new QuestionsPresenter();
        questionsScreen = mock(QuestionsScreen.class);
        questionsPresenter.attachScreen(questionsScreen);
        MockitoAnnotations.initMocks(this);
    }

    private void verifyQuestions() {
        verify(questionsScreen, times(1)).showQuestions(questionsCaptor.capture());
        List<Question> capturedQuestions = questionsCaptor.getValue();
        assertEquals(53, capturedQuestions.size());

        Question question = new Question("How to Android?", "I literally have no idea how to make apps. Can you tell me please how do I begin?");
        question.setId(1L);
        question.setNumberOfAnswers(2);
        assertTrue(question.sameContent(capturedQuestions.get(1)));

        verify(questionsScreen, never()).showMessage(anyString());
    }

    @Test
    public void testGetQuestions() {
        questionsPresenter.getQuestions();
        verifyQuestions();
    }

    @Test
    public void testUpdateQuestions() {
        questionsPresenter.updateQuestions();
        verifyQuestions();
    }

    @After
    public void tearDown() {
        questionsPresenter.detachScreen();
    }
}