package hu.bme.aut.mobsoft.lab.mobsoft.model.answer;

import com.orm.dsl.Table;

@Table
public class Answer {
    private long id;
    private long questionId;
    private String title;
    private String description;
    private int rating;

    public Answer() {
    }

    public Answer(long id, long questionId, String title, String description, int rating) {
        this.id = id;
        this.questionId = questionId;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
