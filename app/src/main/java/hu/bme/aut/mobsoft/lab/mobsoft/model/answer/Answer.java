package hu.bme.aut.mobsoft.lab.mobsoft.model.answer;

import com.orm.dsl.Table;

@Table
public class Answer {
    private Long id = null;

    private long questionId;
    private String title;
    private String description;
    private int rating;

    public Answer() {
    }

    public Answer(long questionId, String title, String description) {
        this.questionId = questionId;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return id.equals(answer.id);
    }

    public boolean sameContent(Answer answer) {
        return this == answer
                || answer != null
                && id.equals(answer.id)
                && questionId == answer.questionId
                && title.equals(answer.title)
                && description.equals(answer.description)
                && rating == answer.rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void addRating(Rating.VoteType vote){
        switch (vote) {
            case UPVOTE:
                rating++;
                break;
            case DOWNVOTE:
                rating--;
                break;
        }
    }
}
