package fr.esgi.rent.external.velib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StationDto(
        @JsonProperty("nom_commune") String town
) {}
