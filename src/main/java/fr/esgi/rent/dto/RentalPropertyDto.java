package fr.esgi.rent.dto;

import java.util.UUID;

public record RentalPropertyDto(
        UUID id,
        String description,
        String town,
        String address,
        double rentAmount,
        double area,
        byte numberOfBedrooms
) {}
