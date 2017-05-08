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
}
