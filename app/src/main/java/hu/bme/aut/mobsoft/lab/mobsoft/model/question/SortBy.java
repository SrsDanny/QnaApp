package hu.bme.aut.mobsoft.lab.mobsoft.model.question;

public class SortBy {
    public enum What {
        TITLE("title"),
        NUMBER_OF_ANSWERS("answers");

        private String val;

        What(String val) {
            this.val = val;
        }
        
        public String toCustomString() {
            return val;
        }
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

    public void setWhat(What what) {
        this.what = what;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
