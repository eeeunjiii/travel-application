package travel.travelapplication.dto.userplan;

import lombok.Getter;
import travel.travelapplication.place.domain.Place;

import java.util.List;

@Getter
public class SelectedPlaceDto {

    private List<Place> selectedPlaces;

    public SelectedPlaceDto(List<Place> selectedPlaces) {
        this.selectedPlaces=selectedPlaces;
    }
}
