package fr.esgi.rent.service;

import fr.esgi.rent.dto.request.RentalPropertyRequest;
import fr.esgi.rent.dto.response.RentalPropertyDto;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.mapper.RentalPropertyRequestMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalPropertyService {

    private final RentalPropertyRepository repository;
    private final RentalPropertyDtoMapper mapper;
    private final RentalPropertyRequestMapper reqMapper;

    public RentalPropertyService(RentalPropertyRepository repository,
                                 RentalPropertyDtoMapper mapper,
                                 RentalPropertyRequestMapper reqMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.reqMapper = reqMapper;
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
    @Transactional
    public RentalPropertyDto create(RentalPropertyRequest req) {
        var entity = reqMapper.toEntity(req);
        repository.save(entity);
        return mapper.toDto(entity);
    }
}
