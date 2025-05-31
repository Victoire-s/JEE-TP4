package fr.esgi.rent.api;

import fr.esgi.rent.dto.RentalPropertyDto;
import fr.esgi.rent.service.RentalPropertyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RentalPropertyResource {


    private final RentalPropertyService service;

    public RentalPropertyResource(RentalPropertyService service) {

        this.service = service;
    }

    @GetMapping("/rental-properties")
    public List<RentalPropertyDto> getRentalProperties() {
        return service.findAll();
    }
    @GetMapping("/rental-properties/{id}")
    public RentalPropertyDto getOne(@PathVariable UUID id) {
        return service.findById(id);
    }
}
