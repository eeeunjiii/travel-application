package travel.travelapplication.place.application;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.repository.PlaceRepository;
import travel.travelapplication.plan.repository.PlanRepository;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public Place findByName(String name) {
        return placeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("user not found : " + name));
    }

    public Place findById(ObjectId id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found : " + id));
    }
}

