package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TrainingFase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TrainingFase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainingFaseRepository extends JpaRepository<TrainingFase, Long> {

}
