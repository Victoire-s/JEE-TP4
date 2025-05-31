package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
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
@Import(RentalPropertyDtoMapper.class)
class RentalPropertyResourceTest {

    @Autowired MockMvc mvc;

    @MockitoBean
    RentalPropertyRepository repository;

    @Test
    void shouldReturnAllProperties() throws Exception {
        RentalPropertyEntity entity = new RentalPropertyEntity();
        entity.setId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        entity.setDescription("Studio cosy");
        entity.setTown("Vincennes");
        entity.setAddress("3 rue Victor-Hugo");
        entity.setRentAmount(790.0);
        entity.setArea(22.5);
        entity.setNumberOfBedrooms((byte) 0);

        when(repository.findAll()).thenReturn(List.of(entity));

        mvc.perform(get("/api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("00000000-0000-0000-0000-000000000001"))
                .andExpect(jsonPath("$[0].description").value("Studio cosy"))
                .andExpect(jsonPath("$[0].town").value("Vincennes"));
    }
}
