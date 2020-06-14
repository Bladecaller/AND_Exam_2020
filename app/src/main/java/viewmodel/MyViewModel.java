package viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import model.retrofitData.Match;
import model.retrofitData.Participants;
import model.retrofitData.Summoner;
import model.MyRepository;

public class MyViewModel extends ViewModel {

    MyRepository repository;

    public MyViewModel(){
        repository = MyRepository.getInstance();
    }

    public LiveData<Summoner> getSummoner() {
        return repository.getSummoner();
    }

    public LiveData<Match> getMatch() {
        return repository.getMatch();
    }

    public LiveData<List<Participants>> getParticipants() {
        return repository.getParticipants();
    }

    public void requestSummoner(String name) {
        repository.requestSummoner(name);
    }

    public void requestMatch(String accountID) {
        repository.requestMatch(accountID);
    }

    public void requestParticipants(String gameID) {
        repository.requestParticipants(gameID);
    }
}
