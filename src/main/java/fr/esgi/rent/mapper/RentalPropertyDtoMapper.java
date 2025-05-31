package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.dto.RentalPropertyDto;
import org.springframework.stereotype.Component;

@Component
public class RentalPropertyDtoMapper {
    public RentalPropertyDto toDto(RentalPropertyEntity e) {
        return new RentalPropertyDto(
                e.getId(),
                e.getDescription(),
                e.getTown(),
                e.getAddress(),
                e.getRentAmount(),
                e.getArea(),
                e.getNumberOfBedrooms()
        );
    }
}
