package hu.bme.aut.mobsoft.lab.mobsoft.model.question;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Question> {
    private SortBy sortBy;

    public QuestionComparator(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int compare(Question o1, Question o2) {
        switch (sortBy.getWhat()) {
            case TITLE:
                if(sortBy.isAscending()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return o2.getTitle().compareTo(o1.getTitle());
                }
            case NUMBER_OF_ANSWERS:
                if(sortBy.isAscending()) {
                    return o1.getNumberOfAnswers() - o2.getNumberOfAnswers();
                } else {
                    return o2.getNumberOfAnswers() - o1.getNumberOfAnswers();
                }
        }
        return 0;
    }
}
