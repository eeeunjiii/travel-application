package travel.travelapplication.place.application;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.Place;
import travel.travelapplication.place.repository.PlaceRepository;
import travel.travelapplication.plan.repository.PlanRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place findByName(String name) {
        return placeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("place not found : " + name));
    }

    public Place findById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("place not found : " + id));
    }
}

