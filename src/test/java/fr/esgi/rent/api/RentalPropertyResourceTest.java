package fr.esgi.rent.api;

import fr.esgi.rent.dto.RentalPropertyDto;
import fr.esgi.rent.service.RentalPropertyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalPropertyResource.class)
@Import(RestExceptionHandler.class)
class RentalPropertyResourceTest {

    @Autowired MockMvc mvc;

    @MockitoBean
    RentalPropertyService service;   // on mocke la façade

    static UUID PROPERTY_ID = UUID.fromString("00000000-0000-0000-0000-000000000042");

    @Test
    void shouldReturnList() throws Exception {
        RentalPropertyDto dto = new RentalPropertyDto(
                PROPERTY_ID, "Studio", "Paris", "1 rue du Test", 800, 20, (byte)0);

        when(service.findAll()).thenReturn(List.of(dto));

        mvc.perform(get("/api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(PROPERTY_ID.toString()));
    }

    @Test
    void shouldReturnDetail() throws Exception {
        RentalPropertyDto dto = new RentalPropertyDto(
                PROPERTY_ID, "Studio", "Paris", "1 rue du Test", 800, 20, (byte)0);

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
}
