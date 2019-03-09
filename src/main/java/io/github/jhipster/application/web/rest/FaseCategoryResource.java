package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.FaseCategory;
import io.github.jhipster.application.repository.FaseCategoryRepository;
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
 * REST controller for managing FaseCategory.
 */
@RestController
@RequestMapping("/api")
public class FaseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(FaseCategoryResource.class);

    private static final String ENTITY_NAME = "faseCategory";

    private final FaseCategoryRepository faseCategoryRepository;

    public FaseCategoryResource(FaseCategoryRepository faseCategoryRepository) {
        this.faseCategoryRepository = faseCategoryRepository;
    }

    /**
     * POST  /fase-categories : Create a new faseCategory.
     *
     * @param faseCategory the faseCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faseCategory, or with status 400 (Bad Request) if the faseCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fase-categories")
    public ResponseEntity<FaseCategory> createFaseCategory(@Valid @RequestBody FaseCategory faseCategory) throws URISyntaxException {
        log.debug("REST request to save FaseCategory : {}", faseCategory);
        if (faseCategory.getId() != null) {
            throw new BadRequestAlertException("A new faseCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaseCategory result = faseCategoryRepository.save(faseCategory);
        return ResponseEntity.created(new URI("/api/fase-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fase-categories : Updates an existing faseCategory.
     *
     * @param faseCategory the faseCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faseCategory,
     * or with status 400 (Bad Request) if the faseCategory is not valid,
     * or with status 500 (Internal Server Error) if the faseCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fase-categories")
    public ResponseEntity<FaseCategory> updateFaseCategory(@Valid @RequestBody FaseCategory faseCategory) throws URISyntaxException {
        log.debug("REST request to update FaseCategory : {}", faseCategory);
        if (faseCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaseCategory result = faseCategoryRepository.save(faseCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faseCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fase-categories : get all the faseCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faseCategories in body
     */
    @GetMapping("/fase-categories")
    public List<FaseCategory> getAllFaseCategories() {
        log.debug("REST request to get all FaseCategories");
        return faseCategoryRepository.findAll();
    }

    /**
     * GET  /fase-categories/:id : get the "id" faseCategory.
     *
     * @param id the id of the faseCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faseCategory, or with status 404 (Not Found)
     */
    @GetMapping("/fase-categories/{id}")
    public ResponseEntity<FaseCategory> getFaseCategory(@PathVariable Long id) {
        log.debug("REST request to get FaseCategory : {}", id);
        Optional<FaseCategory> faseCategory = faseCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faseCategory);
    }

    /**
     * DELETE  /fase-categories/:id : delete the "id" faseCategory.
     *
     * @param id the id of the faseCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fase-categories/{id}")
    public ResponseEntity<Void> deleteFaseCategory(@PathVariable Long id) {
        log.debug("REST request to delete FaseCategory : {}", id);
        faseCategoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
