package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.repository.RentalPropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentalPropertyResource {
    private final RentalPropertyRepository repository;

    public RentalPropertyResource(RentalPropertyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rental-properties")
    public List<RentalPropertyEntity> getRentalProperties() {
        return repository.findAll();
    }
}