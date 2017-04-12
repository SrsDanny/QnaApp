package hu.bme.aut.mobsoft.lab.mobsoft.model.question;

public class SortBy {
    public enum What {
        TITLE,
        NUMBER_OF_ANSWERS
    }

    private What what;
    private boolean ascending;

    public SortBy(What what, boolean ascending) {
        this.what = what;
        this.ascending = ascending;
    }

    public What getWhat() {
        return what;
    }

    public boolean isAscending() {
        return ascending;
    }
}
