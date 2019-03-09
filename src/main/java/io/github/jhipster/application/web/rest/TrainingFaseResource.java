package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.TrainingFase;
import io.github.jhipster.application.repository.TrainingFaseRepository;
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
 * REST controller for managing TrainingFase.
 */
@RestController
@RequestMapping("/api")
public class TrainingFaseResource {

    private final Logger log = LoggerFactory.getLogger(TrainingFaseResource.class);

    private static final String ENTITY_NAME = "trainingFase";

    private final TrainingFaseRepository trainingFaseRepository;

    public TrainingFaseResource(TrainingFaseRepository trainingFaseRepository) {
        this.trainingFaseRepository = trainingFaseRepository;
    }

    /**
     * POST  /training-fases : Create a new trainingFase.
     *
     * @param trainingFase the trainingFase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trainingFase, or with status 400 (Bad Request) if the trainingFase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/training-fases")
    public ResponseEntity<TrainingFase> createTrainingFase(@RequestBody TrainingFase trainingFase) throws URISyntaxException {
        log.debug("REST request to save TrainingFase : {}", trainingFase);
        if (trainingFase.getId() != null) {
            throw new BadRequestAlertException("A new trainingFase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainingFase result = trainingFaseRepository.save(trainingFase);
        return ResponseEntity.created(new URI("/api/training-fases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /training-fases : Updates an existing trainingFase.
     *
     * @param trainingFase the trainingFase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trainingFase,
     * or with status 400 (Bad Request) if the trainingFase is not valid,
     * or with status 500 (Internal Server Error) if the trainingFase couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/training-fases")
    public ResponseEntity<TrainingFase> updateTrainingFase(@RequestBody TrainingFase trainingFase) throws URISyntaxException {
        log.debug("REST request to update TrainingFase : {}", trainingFase);
        if (trainingFase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrainingFase result = trainingFaseRepository.save(trainingFase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trainingFase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /training-fases : get all the trainingFases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of trainingFases in body
     */
    @GetMapping("/training-fases")
    public List<TrainingFase> getAllTrainingFases() {
        log.debug("REST request to get all TrainingFases");
        return trainingFaseRepository.findAll();
    }

    /**
     * GET  /training-fases/:id : get the "id" trainingFase.
     *
     * @param id the id of the trainingFase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trainingFase, or with status 404 (Not Found)
     */
    @GetMapping("/training-fases/{id}")
    public ResponseEntity<TrainingFase> getTrainingFase(@PathVariable Long id) {
        log.debug("REST request to get TrainingFase : {}", id);
        Optional<TrainingFase> trainingFase = trainingFaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trainingFase);
    }

    /**
     * DELETE  /training-fases/:id : delete the "id" trainingFase.
     *
     * @param id the id of the trainingFase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/training-fases/{id}")
    public ResponseEntity<Void> deleteTrainingFase(@PathVariable Long id) {
        log.debug("REST request to delete TrainingFase : {}", id);
        trainingFaseRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
