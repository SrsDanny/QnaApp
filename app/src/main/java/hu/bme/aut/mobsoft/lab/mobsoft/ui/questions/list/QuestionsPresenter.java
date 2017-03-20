package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import hu.bme.aut.mobsoft.lab.mobsoft.ui.Presenter;

public class QuestionsPresenter extends Presenter<QuestionsScreen> {

    @Override
    public void attachScreen(QuestionsScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void searchQuestions(String query) {
        // Search questions, call showQuestions()
    }

    public void sortQuestions(/*Sort criteria comes here*/) {
        // Sort questions, call showQuestions()
    }

    public void questionSelected(int questionId) {
        screen.showAnswersFor(questionId);
    }
}
