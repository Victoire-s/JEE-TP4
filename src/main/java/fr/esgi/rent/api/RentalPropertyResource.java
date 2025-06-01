package fr.esgi.rent.api;

import fr.esgi.rent.dto.request.RentalPropertyRequest;
import fr.esgi.rent.dto.response.RentalPropertyDto;
import fr.esgi.rent.service.RentalPropertyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
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
    @PostMapping("/rental-properties")
    public ResponseEntity<RentalPropertyDto> create(
            @Valid @RequestBody RentalPropertyRequest body,
            UriComponentsBuilder uriBuilder) {

        RentalPropertyDto dto = service.create(body);

        URI location = uriBuilder
                .path("/api/rental-properties/{id}")
                .buildAndExpand(dto.id())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }
}
