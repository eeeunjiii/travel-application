package travel.travelapplication.place.presentation;

import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import travel.travelapplication.place.application.PlaceService;
import travel.travelapplication.place.domain.Place;

@RestController
@Transactional
@RequestMapping("/places")
public class PlaceController {
    private PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<Place> findPlaceById(@RequestParam(name = "id") ObjectId id) {
        Place place = placeService.findById(id);
        return ResponseEntity.ok(place);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Place> findPlaceByName(@RequestParam(name = "name") String name) {
        Place place = placeService.findByName(name);
        return ResponseEntity.ok(place);
    }


}
