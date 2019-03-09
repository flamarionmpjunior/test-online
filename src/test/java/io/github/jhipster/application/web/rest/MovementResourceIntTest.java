package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.Movement;
import io.github.jhipster.application.repository.MovementRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MovementResource REST controller.
 *
 * @see MovementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class MovementResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_LINK = "AAAAAAAAAA";
    private static final String UPDATED_WEB_LINK = "BBBBBBBBBB";

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMovementMockMvc;

    private Movement movement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovementResource movementResource = new MovementResource(movementRepository);
        this.restMovementMockMvc = MockMvcBuilders.standaloneSetup(movementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movement createEntity(EntityManager em) {
        Movement movement = new Movement()
            .name(DEFAULT_NAME)
            .abreviation(DEFAULT_ABREVIATION)
            .description(DEFAULT_DESCRIPTION)
            .webLink(DEFAULT_WEB_LINK);
        return movement;
    }

    @Before
    public void initTest() {
        movement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovement() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();

        // Create the Movement
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isCreated());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate + 1);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovement.getAbreviation()).isEqualTo(DEFAULT_ABREVIATION);
        assertThat(testMovement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMovement.getWebLink()).isEqualTo(DEFAULT_WEB_LINK);
    }

    @Test
    @Transactional
    public void createMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();

        // Create the Movement with an existing ID
        movement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setName(null);

        // Create the Movement, which fails.

        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovements() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get all the movementList
        restMovementMockMvc.perform(get("/api/movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].webLink").value(hasItem(DEFAULT_WEB_LINK.toString())));
    }
    
    @Test
    @Transactional
    public void getMovement() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", movement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.abreviation").value(DEFAULT_ABREVIATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.webLink").value(DEFAULT_WEB_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovement() throws Exception {
        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovement() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // Update the movement
        Movement updatedMovement = movementRepository.findById(movement.getId()).get();
        // Disconnect from session so that the updates on updatedMovement are not directly saved in db
        em.detach(updatedMovement);
        updatedMovement
            .name(UPDATED_NAME)
            .abreviation(UPDATED_ABREVIATION)
            .description(UPDATED_DESCRIPTION)
            .webLink(UPDATED_WEB_LINK);

        restMovementMockMvc.perform(put("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovement)))
            .andExpect(status().isOk());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovement.getAbreviation()).isEqualTo(UPDATED_ABREVIATION);
        assertThat(testMovement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMovement.getWebLink()).isEqualTo(UPDATED_WEB_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingMovement() throws Exception {
        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // Create the Movement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovementMockMvc.perform(put("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovement() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        int databaseSizeBeforeDelete = movementRepository.findAll().size();

        // Delete the movement
        restMovementMockMvc.perform(delete("/api/movements/{id}", movement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movement.class);
        Movement movement1 = new Movement();
        movement1.setId(1L);
        Movement movement2 = new Movement();
        movement2.setId(movement1.getId());
        assertThat(movement1).isEqualTo(movement2);
        movement2.setId(2L);
        assertThat(movement1).isNotEqualTo(movement2);
        movement1.setId(null);
        assertThat(movement1).isNotEqualTo(movement2);
    }
}
