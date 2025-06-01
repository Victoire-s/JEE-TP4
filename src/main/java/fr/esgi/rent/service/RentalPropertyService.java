package fr.esgi.rent.service;

import fr.esgi.rent.dto.request.RentalPropertyRequest;
import fr.esgi.rent.dto.response.RentalPropertyDto;
import fr.esgi.rent.external.velib.VelibStationClient;
import fr.esgi.rent.external.velib.dto.StationDto;
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
    private final RentalPropertyDtoMapper   dtoMapper;
    private final RentalPropertyRequestMapper reqMapper;
    private final VelibStationClient        velibClient;

    public RentalPropertyService(RentalPropertyRepository repository,
                                 RentalPropertyDtoMapper dtoMapper,
                                 RentalPropertyRequestMapper reqMapper,
                                 VelibStationClient velibClient) {
        this.repository   = repository;
        this.dtoMapper    = dtoMapper;
        this.reqMapper    = reqMapper;
        this.velibClient  = velibClient;
    }

    public List<RentalPropertyDto> findAll(boolean nearVelib, List<String> towns) {

        if (!nearVelib) {
            return repository.findAll()
                    .stream().map(dtoMapper::toDto).toList();
        }

        List<String> communesCibles = (towns == null || towns.isEmpty())
                ? repository.findDistinctTowns()
                : towns;

        List<String> communesAvecVelib = velibClient.findByTowns(communesCibles)
                .stream()
                .map(StationDto::town)
                .distinct()
                .toList();

        return repository.findByTownIn(communesAvecVelib)
                .stream().map(dtoMapper::toDto).toList();
    }

    public RentalPropertyDto findById(UUID id) {
        return repository.findById(id)
                .map(dtoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rental property %s not found".formatted(id)));
    }

    @Transactional
    public RentalPropertyDto create(RentalPropertyRequest req) {
        var entity = reqMapper.toEntity(req);
        repository.save(entity);
        return dtoMapper.toDto(entity);
    }
}
