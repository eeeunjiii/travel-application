package travel.travelapplication.place.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travel.travelapplication.place.domain.Tag;
import travel.travelapplication.place.repository.TagRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Map<String, String> findAllTag() {
        return tagRepository.findAll().stream()
                .collect(Collectors.toMap(tag -> tag.getId().toHexString(), Tag::getName));
    }
}
