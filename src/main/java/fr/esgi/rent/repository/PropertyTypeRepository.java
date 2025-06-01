package fr.esgi.rent.repository;

import fr.esgi.rent.domain.PropertyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PropertyTypeRepository extends JpaRepository<PropertyTypeEntity, UUID> {

}
