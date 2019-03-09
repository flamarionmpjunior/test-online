package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.MovementCategory;
import io.github.jhipster.application.repository.MovementCategoryRepository;
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
 * REST controller for managing MovementCategory.
 */
@RestController
@RequestMapping("/api")
public class MovementCategoryResource {

    private final Logger log = LoggerFactory.getLogger(MovementCategoryResource.class);

    private static final String ENTITY_NAME = "movementCategory";

    private final MovementCategoryRepository movementCategoryRepository;

    public MovementCategoryResource(MovementCategoryRepository movementCategoryRepository) {
        this.movementCategoryRepository = movementCategoryRepository;
    }

    /**
     * POST  /movement-categories : Create a new movementCategory.
     *
     * @param movementCategory the movementCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new movementCategory, or with status 400 (Bad Request) if the movementCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/movement-categories")
    public ResponseEntity<MovementCategory> createMovementCategory(@Valid @RequestBody MovementCategory movementCategory) throws URISyntaxException {
        log.debug("REST request to save MovementCategory : {}", movementCategory);
        if (movementCategory.getId() != null) {
            throw new BadRequestAlertException("A new movementCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MovementCategory result = movementCategoryRepository.save(movementCategory);
        return ResponseEntity.created(new URI("/api/movement-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /movement-categories : Updates an existing movementCategory.
     *
     * @param movementCategory the movementCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movementCategory,
     * or with status 400 (Bad Request) if the movementCategory is not valid,
     * or with status 500 (Internal Server Error) if the movementCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/movement-categories")
    public ResponseEntity<MovementCategory> updateMovementCategory(@Valid @RequestBody MovementCategory movementCategory) throws URISyntaxException {
        log.debug("REST request to update MovementCategory : {}", movementCategory);
        if (movementCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MovementCategory result = movementCategoryRepository.save(movementCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, movementCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /movement-categories : get all the movementCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of movementCategories in body
     */
    @GetMapping("/movement-categories")
    public List<MovementCategory> getAllMovementCategories() {
        log.debug("REST request to get all MovementCategories");
        return movementCategoryRepository.findAll();
    }

    /**
     * GET  /movement-categories/:id : get the "id" movementCategory.
     *
     * @param id the id of the movementCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the movementCategory, or with status 404 (Not Found)
     */
    @GetMapping("/movement-categories/{id}")
    public ResponseEntity<MovementCategory> getMovementCategory(@PathVariable Long id) {
        log.debug("REST request to get MovementCategory : {}", id);
        Optional<MovementCategory> movementCategory = movementCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(movementCategory);
    }

    /**
     * DELETE  /movement-categories/:id : delete the "id" movementCategory.
     *
     * @param id the id of the movementCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/movement-categories/{id}")
    public ResponseEntity<Void> deleteMovementCategory(@PathVariable Long id) {
        log.debug("REST request to delete MovementCategory : {}", id);
        movementCategoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
