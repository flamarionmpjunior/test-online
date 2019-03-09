package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Sport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

}
