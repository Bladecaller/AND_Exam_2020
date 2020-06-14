package model.retrofitData;

public class ReviewedParticipant {
    String id;
    String name;
    String rating;

    public ReviewedParticipant(){}
    public ReviewedParticipant(String id, String name, String rating){
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }
}
