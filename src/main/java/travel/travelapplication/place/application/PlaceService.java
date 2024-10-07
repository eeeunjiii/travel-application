package travel.travelapplication.place.application;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.repository.PlaceRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place findByName(String name) {
        return placeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("place not found : " + name));
    }

    public Place findById(ObjectId id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("place not found : " + id));
    }

    public Place findByPlaceId(String placeId) {
        return placeRepository.findByPlaceId(placeId)
                .orElseThrow(() -> new IllegalArgumentException("place not found: " + placeId));
    }

    public Place findByStringifiedId(String objectId) {
        return placeRepository.findByStringifiedId(objectId)
                .orElseThrow(() -> new IllegalArgumentException("place not found: " + objectId));
    }
}

