package model.retrofitData;

public class Summoner {
    private String accountId;
    private String name;
    private String summonerLevel;

    public Summoner(String id, String name, String summonerLevel) {
        this.accountId = id;
        this.name = name;
        this.summonerLevel = summonerLevel;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return accountId;
    }

    public String getSummonerLevel() {
        return summonerLevel;
    }
}
