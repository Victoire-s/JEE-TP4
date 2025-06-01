package fr.esgi.rent.api;

import fr.esgi.rent.dto.response.RentalPropertyDto;
import fr.esgi.rent.service.RentalPropertyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalPropertyResource.class)
@Import(RestExceptionHandler.class)
class RentalPropertyResourceTest {

    @Autowired MockMvc mvc;

    @MockitoBean RentalPropertyService service;

    private static final UUID PROPERTY_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000042");

    @Test
    void shouldReturnList() throws Exception {
        RentalPropertyDto dto = new RentalPropertyDto(
                PROPERTY_ID, "Studio", "Paris", "1 rue du Test",
                800, 20, (byte) 0);

        // near_velib_stations = false, aucun filtre town
        when(service.findAll(eq(false), isNull()))
                .thenReturn(List.of(dto));

        mvc.perform(get("/api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(PROPERTY_ID.toString()));
    }

    @Test
    void shouldReturnDetail() throws Exception {
        RentalPropertyDto dto = new RentalPropertyDto(
                PROPERTY_ID, "Studio", "Paris", "1 rue du Test",
                800, 20, (byte) 0);

        when(service.findById(PROPERTY_ID)).thenReturn(dto);

        mvc.perform(get("/api/rental-properties/{id}", PROPERTY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.town").value("Paris"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        when(service.findById(PROPERTY_ID))
                .thenThrow(new EntityNotFoundException());

        mvc.perform(get("/api/rental-properties/{id}", PROPERTY_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreate() throws Exception {
        UUID id = UUID.randomUUID();

        String json = """
            {
              "description":"Studio",
              "town":"Paris",
              "address":"1 rue du Test",
              "rentAmount":800,
              "securityDepositAmount":800,
              "area":20,
              "numberOfBedrooms":0,
              "constructionYear":1995,
              "floorNumber":0,
              "numberOfFloors":0,
              "hasElevator":false,
              "hasIntercom":false,
              "hasBalcony":false,
              "hasParkingSpace":false,
              "propertyTypeId":"00000000-0000-0000-0000-000000000001",
              "energyClassificationId":"00000000-0000-0000-0000-000000000002"
            }""";

        RentalPropertyDto dto = new RentalPropertyDto(
                id, "Studio", "Paris", "1 rue du Test",
                800, 20, (byte) 0);

        when(service.create(any())).thenReturn(dto);

        mvc.perform(post("/api/rental-properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        endsWith("/api/rental-properties/" + id)))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
