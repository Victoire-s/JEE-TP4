package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.repository.RentalPropertyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalPropertyResource.class)
class RentalPropertyResourceTest {

    @Autowired MockMvc mvc;

    @MockitoBean
    RentalPropertyRepository repository;

    @Test
    void shouldReturnAllProperties() throws Exception {
        when(repository.findAll()).thenReturn(List.of(new RentalPropertyEntity()));

        mvc.perform(get("/api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
