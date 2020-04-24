package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public abstract class Post extends Activity{

    protected Map<String, Vote> voters; // String is the User login
    protected int rating;

    public Post(){
        super();
        this.voters = new HashMap<>();
        this.rating = 0;
    }

    protected Post(long creationDate){
        super(creationDate);
    }

    public void ratePost(String userId, Vote vote){
        if(voters.containsKey(userId)){
            var oldVote = voters.get(userId);
            if (!oldVote.equals(vote)){
                voters.remove(userId);
                this.rating += vote.equals(Vote.POSITIVE) ? -1 : 1; // if the vote was POSITIVE, rating goes down, otherwise up
            }
            return;
        }
        this.voters.put(userId, vote);
        this.rating += vote.equals(Vote.POSITIVE) ? 1 : -1;
    }

    public int getUpvotes(){
        return (int) this.voters.values().stream().filter(vote -> vote.equals(Vote.POSITIVE)).count();
    }

    public int getDownvotes(){
        return (int) this.voters.values().stream().filter(vote -> vote.equals(Vote.NEGATIVE)).count();
    }

    public Vote getUserVote(Optional<String> userId){
        return userId.map(id -> voters.get(id)).orElse(Vote.NONE);
    }

}
