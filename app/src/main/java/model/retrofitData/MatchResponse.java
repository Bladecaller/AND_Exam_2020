package model.retrofitData;

public class MatchResponse {
    private Matches[] matches;

    public Match getMatches(){
        return new Match(matches[0].gameId);
    }

    private class Matches{
        private String gameId;
    }
}
