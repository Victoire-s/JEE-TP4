package fr.esgi.rent.external.velib;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "velib")
public record VelibProperties(String baseUrl) {}
