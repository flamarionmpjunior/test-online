package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TrainingFaseMovement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrainingFaseMovement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingFaseMovementRepository extends JpaRepository<TrainingFaseMovement, Long> {

}
