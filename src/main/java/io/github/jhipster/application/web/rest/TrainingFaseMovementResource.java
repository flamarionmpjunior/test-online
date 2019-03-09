package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.TrainingFaseMovement;
import io.github.jhipster.application.repository.TrainingFaseMovementRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TrainingFaseMovement.
 */
@RestController
@RequestMapping("/api")
public class TrainingFaseMovementResource {

    private final Logger log = LoggerFactory.getLogger(TrainingFaseMovementResource.class);

    private static final String ENTITY_NAME = "trainingFaseMovement";

    private final TrainingFaseMovementRepository trainingFaseMovementRepository;

    public TrainingFaseMovementResource(TrainingFaseMovementRepository trainingFaseMovementRepository) {
        this.trainingFaseMovementRepository = trainingFaseMovementRepository;
    }

    /**
     * POST  /training-fase-movements : Create a new trainingFaseMovement.
     *
     * @param trainingFaseMovement the trainingFaseMovement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainingFaseMovement, or with status 400 (Bad Request) if the trainingFaseMovement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/training-fase-movements")
    public ResponseEntity<TrainingFaseMovement> createTrainingFaseMovement(@RequestBody TrainingFaseMovement trainingFaseMovement) throws URISyntaxException {
        log.debug("REST request to save TrainingFaseMovement : {}", trainingFaseMovement);
        if (trainingFaseMovement.getId() != null) {
            throw new BadRequestAlertException("A new trainingFaseMovement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingFaseMovement result = trainingFaseMovementRepository.save(trainingFaseMovement);
        return ResponseEntity.created(new URI("/api/training-fase-movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /training-fase-movements : Updates an existing trainingFaseMovement.
     *
     * @param trainingFaseMovement the trainingFaseMovement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainingFaseMovement,
     * or with status 400 (Bad Request) if the trainingFaseMovement is not valid,
     * or with status 500 (Internal Server Error) if the trainingFaseMovement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/training-fase-movements")
    public ResponseEntity<TrainingFaseMovement> updateTrainingFaseMovement(@RequestBody TrainingFaseMovement trainingFaseMovement) throws URISyntaxException {
        log.debug("REST request to update TrainingFaseMovement : {}", trainingFaseMovement);
        if (trainingFaseMovement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingFaseMovement result = trainingFaseMovementRepository.save(trainingFaseMovement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainingFaseMovement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /training-fase-movements : get all the trainingFaseMovements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trainingFaseMovements in body
     */
    @GetMapping("/training-fase-movements")
    public List<TrainingFaseMovement> getAllTrainingFaseMovements() {
        log.debug("REST request to get all TrainingFaseMovements");
        return trainingFaseMovementRepository.findAll();
    }

    /**
     * GET  /training-fase-movements/:id : get the "id" trainingFaseMovement.
     *
     * @param id the id of the trainingFaseMovement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainingFaseMovement, or with status 404 (Not Found)
     */
    @GetMapping("/training-fase-movements/{id}")
    public ResponseEntity<TrainingFaseMovement> getTrainingFaseMovement(@PathVariable Long id) {
        log.debug("REST request to get TrainingFaseMovement : {}", id);
        Optional<TrainingFaseMovement> trainingFaseMovement = trainingFaseMovementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingFaseMovement);
    }

    /**
     * DELETE  /training-fase-movements/:id : delete the "id" trainingFaseMovement.
     *
     * @param id the id of the trainingFaseMovement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/training-fase-movements/{id}")
    public ResponseEntity<Void> deleteTrainingFaseMovement(@PathVariable Long id) {
        log.debug("REST request to delete TrainingFaseMovement : {}", id);
        trainingFaseMovementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
