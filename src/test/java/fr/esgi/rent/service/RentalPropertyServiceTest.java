package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalPropertyEntity;
import fr.esgi.rent.dto.RentalPropertyDto;
import fr.esgi.rent.mapper.RentalPropertyDtoMapper;
import fr.esgi.rent.repository.RentalPropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.liquibase.enabled=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@EntityScan("fr.esgi.rent.domain")
@Import({RentalPropertyService.class, RentalPropertyDtoMapper.class})
class RentalPropertyServiceTest {

    @Autowired RentalPropertyService service;
    @Autowired RentalPropertyRepository repository;

    @Test
    void shouldFindById() {
        var e = new RentalPropertyEntity();
        e.setDescription("T2 balcon");
        e.setTown("Lyon");
        repository.saveAndFlush(e);              // INSERT OK, table existe

        RentalPropertyDto dto = service.findById(e.getId());

        assertThat(dto.description()).isEqualTo("T2 balcon");
        assertThat(dto.town()).isEqualTo("Lyon");
    }
    @Test
    void shouldReturnAll() {
        var e1 = new RentalPropertyEntity();
        e1.setDescription("Studio");
        e1.setTown("Paris");

        var e2 = new RentalPropertyEntity();
        e2.setDescription("T2 balcon");
        e2.setTown("Lyon");

        repository.saveAllAndFlush(List.of(e1, e2));

        var list = service.findAll();

        assertThat(list)
                .hasSize(2)
                .extracting(RentalPropertyDto::town)
                .containsExactlyInAnyOrder("Paris", "Lyon");
    }


    @Test
    void shouldThrowWhenMissing() {
        assertThrows(EntityNotFoundException.class,
                () -> service.findById(UUID.randomUUID()));
    }
}
