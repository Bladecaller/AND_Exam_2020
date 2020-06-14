package model;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import model.retrofitData.Match;
import model.retrofitData.MatchResponse;
import model.retrofitData.Participants;
import model.retrofitData.ParticipantsResponse;
import model.retrofitData.Summoner;
import model.retrofitData.SummonerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepository {
    private static MyRepository instance;
    private MutableLiveData<Match> match;
    private MutableLiveData<Summoner> summoner;
    private MutableLiveData<List<Participants>> participants;

    private MyRepository() {
        summoner = new MutableLiveData<>();
        match = new MutableLiveData<>();
        participants = new MutableLiveData<>();
    }

    public static synchronized MyRepository getInstance() {
        if (instance == null) {
            instance = new MyRepository();
        }

        return instance;
    }

    public LiveData<Summoner> getSummoner() {
        return summoner;
    }

    public LiveData<Match> getMatch() {
        return match;
    }

    public LiveData<List<Participants>> getParticipants() {
        return participants;
    }

    public void requestSummoner(String pokemonName) {
        RiotAPI riotAPI = ServiceGenerator.getRiotAPI();
        Call<SummonerResponse> call = riotAPI.getSummoner(pokemonName);
        call.enqueue(new Callback<SummonerResponse>() {
            @Override
            public void onResponse(Call<SummonerResponse> call, Response<SummonerResponse> response) {
                if (response.code() == 200) {
                   summoner.setValue(response.body().getSummoner()); //Updating LiveData
                    Log.i("Retrofit", "Summoner request passed");
                }
                if (response.code() == 404) {
                    summoner.setValue(response.body().getSummoner()); //Updating LiveData
                    Log.i("Retrofit", "404");
                }
                if (response.code() == 401) {
                    Log.i("Retrofit", "Unauthorized :(");
                }
            }

            @Override
            public void onFailure(Call<SummonerResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void requestMatch(String accountId) {
        RiotAPI riotAPI = ServiceGenerator.getRiotAPI();
        Call<MatchResponse> call = riotAPI.getMatches(accountId);
        call.enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                if (response.code() == 200) {
                    match.setValue(response.body().getMatches()); //Updating LiveData
                    Log.i("Retrofit", "Match request passed");
                }

                if (response.code() == 401) {
                    Log.i("Retrofit", "Unauthorized :(");
                }
            }

            @Override
            public void onFailure(Call<MatchResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void requestParticipants(String gameID) {
        RiotAPI riotAPI = ServiceGenerator.getRiotAPI();
        Call<ParticipantsResponse> call = riotAPI.getParticipant(gameID);
        call.enqueue(new Callback<ParticipantsResponse>() {
            @Override
            public void onResponse(Call<ParticipantsResponse> call, Response<ParticipantsResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    participants.setValue(response.body().getParticipants()); //Updating LiveData
                    Log.i("Retrofit", "Participant request passed");
                }

                if (response.code() == 401) {
                    Log.i("Retrofit", "Unauthorized :(");
                }
            }

            @Override
            public void onFailure(Call<ParticipantsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }
}