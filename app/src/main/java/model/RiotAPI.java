package model;

import model.retrofitData.MatchResponse;
import model.retrofitData.ParticipantsResponse;
import model.retrofitData.SummonerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiotAPI {
    String key = "RGAPI-cf497d47-6f02-44b7-ae48-126bebf1551c";
    //Lasts one day!
    @GET("summoner/v4/summoners/by-name/{name}?api_key="+key)
    Call<SummonerResponse> getSummoner(@Path("name") String name);

    @GET("match/v4/matchlists/by-account/{name}?api_key="+key)
    Call<MatchResponse> getMatches(@Path("name") String name);

    @GET("match/v4/matches/{gameID}?api_key="+key)
    Call<ParticipantsResponse> getParticipant(@Path("gameID")String gameID);
}