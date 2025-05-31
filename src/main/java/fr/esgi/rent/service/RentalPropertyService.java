package fr.esgi.rent.service;

import fr.esgi.rent.dto.RentalPropertyDto;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalPropertyService {

    private final RentalPropertyRepository repository;
    private final RentalPropertyDtoMapper mapper;

    public RentalPropertyService(RentalPropertyRepository repository,
                                 RentalPropertyDtoMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    public List<RentalPropertyDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
    public RentalPropertyDto findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rental property %s not found".formatted(id)));
    }
}
