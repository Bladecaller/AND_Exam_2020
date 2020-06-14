package model.retrofitData;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsResponse {
    participantIdentities[] participantIdentities;

    public List<Participants> getParticipants() {
        List<Participants> list = new ArrayList<>();
        for(int i = 0; i <participantIdentities.length;i++){
            list.add(new Participants(participantIdentities[i].player.summonerName));
        }

        return list;
    }

    public class participantIdentities{
        private player player;

        public class player{
            String summonerName;
        }
    }
}




