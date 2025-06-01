package fr.esgi.rent.service;

import fr.esgi.rent.domain.*;
import fr.esgi.rent.dto.request.RentalPropertyRequest;
import fr.esgi.rent.dto.response.RentalPropertyDto;
import fr.esgi.rent.external.velib.VelibStationClient;
import fr.esgi.rent.mapper.*;
import fr.esgi.rent.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EntityScan("fr.esgi.rent.domain")
@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.liquibase.enabled=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@Import({
        RentalPropertyService.class,
        RentalPropertyDtoMapper.class,
        RentalPropertyRequestMapper.class
})
class RentalPropertyServiceTest {

    @org.springframework.boot.test.mock.mockito.MockBean
    VelibStationClient velibClient;

    @Autowired RentalPropertyService          service;
    @Autowired RentalPropertyRepository       repository;
    @Autowired PropertyTypeRepository         typeRepo;
    @Autowired EnergyClassificationRepository energyRepo;

    @Test
    void shouldFindById() {
        var e = new RentalPropertyEntity();
        e.setDescription("T2 balcon");
        e.setTown("Lyon");
        repository.saveAndFlush(e);

        RentalPropertyDto dto = service.findById(e.getId());

        assertThat(dto.description()).isEqualTo("T2 balcon");
        assertThat(dto.town()).isEqualTo("Lyon");
    }

    @Test
    void shouldReturnAll() {
        var e1 = new RentalPropertyEntity(); e1.setDescription("Studio"); e1.setTown("Paris");
        var e2 = new RentalPropertyEntity(); e2.setDescription("T2");     e2.setTown("Lyon");
        repository.saveAllAndFlush(List.of(e1, e2));

        List<RentalPropertyDto> list = service.findAll(false, null);

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

    @Test
    void shouldPersistAndReturnDto() {
        var type = new PropertyTypeEntity();
        type.setDesignation("Studio");
        typeRepo.saveAndFlush(type);

        var energy = new EnergyClassificationEntity();
        energy.setDesignation("A");
        energyRepo.saveAndFlush(energy);

        var req = new RentalPropertyRequest(
                "Studio",                     // description
                "Paris",                      // town
                "1 rue du Test",              // address
                20,                           // area
                (byte) 0,                     // numberOfBedrooms
                (short) 1995,                 // constructionYear
                (short) 0,                    // floorNumber
                (short) 0,                    // numberOfFloors
                false, false, false, false,   // flags
                800,                          // rentAmount
                800,                          // securityDepositAmount
                type.getId(),                 // FK propertyType
                energy.getId()                // FK energyClass
        );

        RentalPropertyDto dto = service.create(req);

        assertThat(dto.id()).isNotNull();
        assertThat(repository.findById(dto.id())).isPresent();
    }
}
