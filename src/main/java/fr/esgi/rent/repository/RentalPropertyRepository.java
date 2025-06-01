package fr.esgi.rent.repository;
import fr.esgi.rent.domain.RentalPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Repository
public interface RentalPropertyRepository extends JpaRepository<RentalPropertyEntity, UUID> {
    List<RentalPropertyEntity> findByTownIn(Collection<String> towns);
    @Query("select distinct r.town from RentalPropertyEntity r")
    List<String> findDistinctTowns();
}