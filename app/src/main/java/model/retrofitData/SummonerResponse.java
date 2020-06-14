package model.retrofitData;

public class SummonerResponse {
    private String accountId;
    private String name;
    private String  summonerLevel;

    public Summoner getSummoner(){
        return new Summoner(accountId,name,summonerLevel);
    }
}
