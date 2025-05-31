package fr.esgi.rent.api;

import fr.esgi.rent.dto.RentalPropertyDto;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentalPropertyResource {

    private final RentalPropertyRepository repository;
    private final RentalPropertyDtoMapper   mapper;

    public RentalPropertyResource(RentalPropertyRepository repository, RentalPropertyDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/rental-properties")
    public List<RentalPropertyDto> getRentalProperties() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
