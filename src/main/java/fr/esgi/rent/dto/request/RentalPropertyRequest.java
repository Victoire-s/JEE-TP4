package fr.esgi.rent.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record RentalPropertyRequest(

        @NotBlank
        String description,
        @NotBlank
        String town,
        @NotBlank
        String address,

        @Positive
        double area,
        @Min(0)
        byte numberOfBedrooms,
        @Min(0)
        short constructionYear,
        @Min(0)
        short floorNumber,
        @Min(0)
        short numberOfFloors,

        boolean hasElevator,
        boolean hasIntercom,
        boolean hasBalcony,
        boolean hasParkingSpace,

        @Positive
        double rentAmount,
        @Positive
        double securityDepositAmount,

        @NotNull
        UUID propertyTypeId,
        @NotNull
        UUID energyClassificationId
) {}
