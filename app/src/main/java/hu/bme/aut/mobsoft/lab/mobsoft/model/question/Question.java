package hu.bme.aut.mobsoft.lab.mobsoft.model.question;

import com.orm.dsl.Table;

@Table
public class Question {
    private Long id = null;

    private String title;
    private String description;
    private int numberOfAnswers = 0;

    public Question() {
    }

    public Question(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id != null ? id.equals(question.id) : question.id == null;
    }

    public boolean sameContent(Question question) {
        return this == question ||
                question != null
                        && id.equals(question.id)
                        && description.equals(question.description)
                        && numberOfAnswers == question.numberOfAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public void setNumberOfAnswers(int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
    }

    public void increaseNumberOfAnswers() { ++numberOfAnswers; }
}
