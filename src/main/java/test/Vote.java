package test;

import java.util.ArrayList;

public class Vote {
    private String userId;
    private ArrayList<Integer> votes;

    public Vote(String userId, ArrayList<Integer> votes) {
        this.userId = userId;
        this.votes = votes;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Integer> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Integer> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "utente: '" + userId + '\'' +
                ", votes=" + votes +
                '}';
    }
}
