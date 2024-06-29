package travel.travelapplication.place.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    private String title;
    private String latitude;
    private String longitude;

    public LocationDto(String title, String latitude, String longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
