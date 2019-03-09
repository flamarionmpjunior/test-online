package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Movement;
import io.github.jhipster.application.repository.MovementRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Movement.
 */
@RestController
@RequestMapping("/api")
public class MovementResource {

    private final Logger log = LoggerFactory.getLogger(MovementResource.class);

    private static final String ENTITY_NAME = "movement";

    private final MovementRepository movementRepository;

    public MovementResource(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    /**
     * POST  /movements : Create a new movement.
     *
     * @param movement the movement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movement, or with status 400 (Bad Request) if the movement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movements")
    public ResponseEntity<Movement> createMovement(@Valid @RequestBody Movement movement) throws URISyntaxException {
        log.debug("REST request to save Movement : {}", movement);
        if (movement.getId() != null) {
            throw new BadRequestAlertException("A new movement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movement result = movementRepository.save(movement);
        return ResponseEntity.created(new URI("/api/movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movements : Updates an existing movement.
     *
     * @param movement the movement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movement,
     * or with status 400 (Bad Request) if the movement is not valid,
     * or with status 500 (Internal Server Error) if the movement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movements")
    public ResponseEntity<Movement> updateMovement(@Valid @RequestBody Movement movement) throws URISyntaxException {
        log.debug("REST request to update Movement : {}", movement);
        if (movement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Movement result = movementRepository.save(movement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movements : get all the movements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movements in body
     */
    @GetMapping("/movements")
    public List<Movement> getAllMovements() {
        log.debug("REST request to get all Movements");
        return movementRepository.findAll();
    }

    /**
     * GET  /movements/:id : get the "id" movement.
     *
     * @param id the id of the movement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movement, or with status 404 (Not Found)
     */
    @GetMapping("/movements/{id}")
    public ResponseEntity<Movement> getMovement(@PathVariable Long id) {
        log.debug("REST request to get Movement : {}", id);
        Optional<Movement> movement = movementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(movement);
    }

    /**
     * DELETE  /movements/:id : delete the "id" movement.
     *
     * @param id the id of the movement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movements/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        log.debug("REST request to delete Movement : {}", id);
        movementRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
