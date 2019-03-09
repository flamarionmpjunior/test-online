package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Training;
import io.github.jhipster.application.repository.TrainingRepository;
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
 * REST controller for managing Training.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "training";

    private final TrainingRepository trainingRepository;

    public TrainingResource(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * POST  /trainings : Create a new training.
     *
     * @param training the training to create
     * @return the ResponseEntity with status 201 (Created) and with body the new training, or with status 400 (Bad Request) if the training has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trainings")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to save Training : {}", training);
        if (training.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Training result = trainingRepository.save(training);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainings : Updates an existing training.
     *
     * @param training the training to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated training,
     * or with status 400 (Bad Request) if the training is not valid,
     * or with status 500 (Internal Server Error) if the training couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trainings")
    public ResponseEntity<Training> updateTraining(@RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to update Training : {}", training);
        if (training.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Training result = trainingRepository.save(training);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, training.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainings : get all the trainings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trainings in body
     */
    @GetMapping("/trainings")
    public List<Training> getAllTrainings() {
        log.debug("REST request to get all Trainings");
        return trainingRepository.findAll();
    }

    /**
     * GET  /trainings/:id : get the "id" training.
     *
     * @param id the id of the training to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the training, or with status 404 (Not Found)
     */
    @GetMapping("/trainings/{id}")
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<Training> training = trainingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(training);
    }

    /**
     * DELETE  /trainings/:id : delete the "id" training.
     *
     * @param id the id of the training to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
