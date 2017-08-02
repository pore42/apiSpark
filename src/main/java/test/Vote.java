package test;

import java.util.ArrayList;

public class Vote {
    private String userId;
    private ArrayList<Integer> votes;

    public Vote(String userId, ArrayList<Integer> votes) {
        this.userId = userId;
        this.votes = votes;
    }

    public Vote(String userId) {
        this.userId = userId;
    }


}
