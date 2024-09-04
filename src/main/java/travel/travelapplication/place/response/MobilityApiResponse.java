package travel.travelapplication.place.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MobilityApiResponse { // Kakako Mobility Response
    private String name;
    private Double x;
    private Double y;
    private int distance;
    private int duration;

    public MobilityApiResponse(String name, Double x, Double y, int distance, int duration) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.duration = duration;
    }
}
