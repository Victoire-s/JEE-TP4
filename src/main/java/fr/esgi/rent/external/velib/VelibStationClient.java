package fr.esgi.rent.external.velib;

import fr.esgi.rent.external.velib.dto.StationDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class VelibStationClient {

    private final WebClient client;

    public VelibStationClient(WebClient.Builder builder, VelibProperties props) {
        this.client = builder.baseUrl(props.baseUrl()).build();
    }

    public List<StationDto> findByTowns(List<String> towns) {
        return client.get()
                .uri(uri -> uri.path("/api/stations/velibs")
                        .queryParam("town", towns.toArray())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(StationDto.class)
                .collectList()
                .block();
    }
}
