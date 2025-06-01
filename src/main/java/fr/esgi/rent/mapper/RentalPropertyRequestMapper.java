package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.*;
import fr.esgi.rent.dto.request.RentalPropertyRequest;
import fr.esgi.rent.repository.EnergyClassificationRepository;
import fr.esgi.rent.repository.PropertyTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RentalPropertyRequestMapper {

    private final PropertyTypeRepository typeRepo;
    private final EnergyClassificationRepository energyRepo;

    public RentalPropertyRequestMapper(PropertyTypeRepository typeRepo,
                                       EnergyClassificationRepository energyRepo) {
        this.typeRepo   = typeRepo;
        this.energyRepo = energyRepo;
    }

    public RentalPropertyEntity toEntity(RentalPropertyRequest r) {
        PropertyTypeEntity type = typeRepo.findById(r.propertyTypeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Property type %s not found".formatted(r.propertyTypeId())));

        EnergyClassificationEntity energy = energyRepo.findById(r.energyClassificationId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Energy class %s not found".formatted(r.energyClassificationId())));

        var e = new RentalPropertyEntity();
        e.setDescription(r.description());
        e.setTown(r.town());
        e.setAddress(r.address());
        e.setRentAmount(r.rentAmount());
        e.setSecurityDepositAmount(r.securityDepositAmount());
        e.setArea(r.area());
        e.setNumberOfBedrooms(r.numberOfBedrooms());
        e.setConstructionYear(r.constructionYear());
        e.setFloorNumber(r.floorNumber());
        e.setNumberOfFloors(r.numberOfFloors());
        e.setHasElevator(r.hasElevator());
        e.setHasIntercom(r.hasIntercom());
        e.setHasBalcony(r.hasBalcony());
        e.setHasParkingSpace(r.hasParkingSpace());

        e.setPropertyType(type);
        e.setEnergyClassification(energy);
        return e;
    }
}
