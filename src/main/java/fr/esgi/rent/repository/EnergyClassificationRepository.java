package fr.esgi.rent.repository;

import fr.esgi.rent.domain.EnergyClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnergyClassificationRepository extends JpaRepository<EnergyClassificationEntity, UUID> {
}
