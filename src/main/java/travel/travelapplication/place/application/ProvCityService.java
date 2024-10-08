package travel.travelapplication.place.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.ProvCity;
import travel.travelapplication.place.repository.ProvCityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvCityService {

    private final ProvCityRepository provCityRepository;

    public Optional<ProvCity> getCity(String city) {
        return provCityRepository.findByName(city);
    }
}
