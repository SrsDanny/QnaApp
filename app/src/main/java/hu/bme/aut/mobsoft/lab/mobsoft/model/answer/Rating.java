package hu.bme.aut.mobsoft.lab.mobsoft.model.answer;


public class Rating {
    public enum VoteType {
        UPVOTE,
        DOWNVOTE
    }

    private VoteType vote;
    private long answerId;

    public Rating() {
    }

    public Rating(VoteType vote, long answerId) {
        this.vote = vote;
        this.answerId = answerId;
    }

    public VoteType getVote() {
        return vote;
    }

    public void setVote(VoteType vote) {
        this.vote = vote;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        return answerId == rating.answerId && vote == rating.vote;
    }
}
