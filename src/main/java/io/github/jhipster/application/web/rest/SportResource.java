package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Sport;
import io.github.jhipster.application.repository.SportRepository;
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
 * REST controller for managing Sport.
 */
@RestController
@RequestMapping("/api")
public class SportResource {

    private final Logger log = LoggerFactory.getLogger(SportResource.class);

    private static final String ENTITY_NAME = "sport";

    private final SportRepository sportRepository;

    public SportResource(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    /**
     * POST  /sports : Create a new sport.
     *
     * @param sport the sport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sport, or with status 400 (Bad Request) if the sport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sports")
    public ResponseEntity<Sport> createSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to save Sport : {}", sport);
        if (sport.getId() != null) {
            throw new BadRequestAlertException("A new sport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sport result = sportRepository.save(sport);
        return ResponseEntity.created(new URI("/api/sports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sports : Updates an existing sport.
     *
     * @param sport the sport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sport,
     * or with status 400 (Bad Request) if the sport is not valid,
     * or with status 500 (Internal Server Error) if the sport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sports")
    public ResponseEntity<Sport> updateSport(@Valid @RequestBody Sport sport) throws URISyntaxException {
        log.debug("REST request to update Sport : {}", sport);
        if (sport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sport result = sportRepository.save(sport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sports : get all the sports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sports in body
     */
    @GetMapping("/sports")
    public List<Sport> getAllSports() {
        log.debug("REST request to get all Sports");
        return sportRepository.findAll();
    }

    /**
     * GET  /sports/:id : get the "id" sport.
     *
     * @param id the id of the sport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sport, or with status 404 (Not Found)
     */
    @GetMapping("/sports/{id}")
    public ResponseEntity<Sport> getSport(@PathVariable Long id) {
        log.debug("REST request to get Sport : {}", id);
        Optional<Sport> sport = sportRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sport);
    }

    /**
     * DELETE  /sports/:id : delete the "id" sport.
     *
     * @param id the id of the sport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sports/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        log.debug("REST request to delete Sport : {}", id);
        sportRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
