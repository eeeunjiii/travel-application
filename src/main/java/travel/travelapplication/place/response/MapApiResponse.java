package travel.travelapplication.place.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapApiResponse {

    private String id;

    @JsonProperty("place_name")
    private String placeName;

    private String phone;

    private String link;

    private String address;

    private String x;
    private String y;

    public MapApiResponse(String id, String placeName, String phone, String link,
                          String address, String x, String y) {
        this.id = id;
        this.placeName = placeName;
        this.phone = phone;
        this.link = link;
        this.address=address;
        this.x=x;
        this.y=y;
    }
}
