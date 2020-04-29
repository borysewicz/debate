package com.example.Debate.model;

import com.example.Debate.model.enums.Vote;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public abstract class Post extends Activity {

    protected Map<String, Vote> voters; // String is the user login
    protected int rating;

    public Post() {
        super();
        this.voters = new HashMap<>();
        this.rating = 0;
    }

    protected Post(long creationDate) {
        super(creationDate);
    }

    public void ratePost(String userName, Vote vote) {
        if (voters.containsKey(userName)) {
            var oldVote = voters.get(userName);
            if (!oldVote.equals(vote)) {
                voters.remove(userName);
                this.rating += vote.equals(Vote.POSITIVE) ? -1 : 1; // if the vote was POSITIVE, rating goes down, otherwise up.
            }
            return;
        }
        this.voters.put(userName, vote);
        this.rating += vote.equals(Vote.POSITIVE) ? 1 : -1;
    }

    public int getUpvotes() {
        return (int) this.voters.values().stream().filter(vote -> vote.equals(Vote.POSITIVE)).count();
    }

    public int getDownvotes() {
        return (int) this.voters.values().stream().filter(vote -> vote.equals(Vote.NEGATIVE)).count();
    }

    public Vote getUserVote(Optional<String> userLogin) {
        return userLogin.map(id -> voters.get(id)).orElse(Vote.NONE);
    }

}
