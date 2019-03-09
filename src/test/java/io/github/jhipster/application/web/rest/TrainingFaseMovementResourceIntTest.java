package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.TrainingFaseMovement;
import io.github.jhipster.application.repository.TrainingFaseMovementRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TrainingFaseMovementResource REST controller.
 *
 * @see TrainingFaseMovementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class TrainingFaseMovementResourceIntTest {

    private static final Instant DEFAULT_NAME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NAME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TrainingFaseMovementRepository trainingFaseMovementRepository;

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

    private MockMvc restTrainingFaseMovementMockMvc;

    private TrainingFaseMovement trainingFaseMovement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingFaseMovementResource trainingFaseMovementResource = new TrainingFaseMovementResource(trainingFaseMovementRepository);
        this.restTrainingFaseMovementMockMvc = MockMvcBuilders.standaloneSetup(trainingFaseMovementResource)
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
    public static TrainingFaseMovement createEntity(EntityManager em) {
        TrainingFaseMovement trainingFaseMovement = new TrainingFaseMovement()
            .name(DEFAULT_NAME);
        return trainingFaseMovement;
    }

    @Before
    public void initTest() {
        trainingFaseMovement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingFaseMovement() throws Exception {
        int databaseSizeBeforeCreate = trainingFaseMovementRepository.findAll().size();

        // Create the TrainingFaseMovement
        restTrainingFaseMovementMockMvc.perform(post("/api/training-fase-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFaseMovement)))
            .andExpect(status().isCreated());

        // Validate the TrainingFaseMovement in the database
        List<TrainingFaseMovement> trainingFaseMovementList = trainingFaseMovementRepository.findAll();
        assertThat(trainingFaseMovementList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingFaseMovement testTrainingFaseMovement = trainingFaseMovementList.get(trainingFaseMovementList.size() - 1);
        assertThat(testTrainingFaseMovement.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTrainingFaseMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingFaseMovementRepository.findAll().size();

        // Create the TrainingFaseMovement with an existing ID
        trainingFaseMovement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingFaseMovementMockMvc.perform(post("/api/training-fase-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFaseMovement)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingFaseMovement in the database
        List<TrainingFaseMovement> trainingFaseMovementList = trainingFaseMovementRepository.findAll();
        assertThat(trainingFaseMovementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainingFaseMovements() throws Exception {
        // Initialize the database
        trainingFaseMovementRepository.saveAndFlush(trainingFaseMovement);

        // Get all the trainingFaseMovementList
        restTrainingFaseMovementMockMvc.perform(get("/api/training-fase-movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingFaseMovement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTrainingFaseMovement() throws Exception {
        // Initialize the database
        trainingFaseMovementRepository.saveAndFlush(trainingFaseMovement);

        // Get the trainingFaseMovement
        restTrainingFaseMovementMockMvc.perform(get("/api/training-fase-movements/{id}", trainingFaseMovement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainingFaseMovement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingFaseMovement() throws Exception {
        // Get the trainingFaseMovement
        restTrainingFaseMovementMockMvc.perform(get("/api/training-fase-movements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingFaseMovement() throws Exception {
        // Initialize the database
        trainingFaseMovementRepository.saveAndFlush(trainingFaseMovement);

        int databaseSizeBeforeUpdate = trainingFaseMovementRepository.findAll().size();

        // Update the trainingFaseMovement
        TrainingFaseMovement updatedTrainingFaseMovement = trainingFaseMovementRepository.findById(trainingFaseMovement.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingFaseMovement are not directly saved in db
        em.detach(updatedTrainingFaseMovement);
        updatedTrainingFaseMovement
            .name(UPDATED_NAME);

        restTrainingFaseMovementMockMvc.perform(put("/api/training-fase-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingFaseMovement)))
            .andExpect(status().isOk());

        // Validate the TrainingFaseMovement in the database
        List<TrainingFaseMovement> trainingFaseMovementList = trainingFaseMovementRepository.findAll();
        assertThat(trainingFaseMovementList).hasSize(databaseSizeBeforeUpdate);
        TrainingFaseMovement testTrainingFaseMovement = trainingFaseMovementList.get(trainingFaseMovementList.size() - 1);
        assertThat(testTrainingFaseMovement.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingFaseMovement() throws Exception {
        int databaseSizeBeforeUpdate = trainingFaseMovementRepository.findAll().size();

        // Create the TrainingFaseMovement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingFaseMovementMockMvc.perform(put("/api/training-fase-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFaseMovement)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingFaseMovement in the database
        List<TrainingFaseMovement> trainingFaseMovementList = trainingFaseMovementRepository.findAll();
        assertThat(trainingFaseMovementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainingFaseMovement() throws Exception {
        // Initialize the database
        trainingFaseMovementRepository.saveAndFlush(trainingFaseMovement);

        int databaseSizeBeforeDelete = trainingFaseMovementRepository.findAll().size();

        // Delete the trainingFaseMovement
        restTrainingFaseMovementMockMvc.perform(delete("/api/training-fase-movements/{id}", trainingFaseMovement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingFaseMovement> trainingFaseMovementList = trainingFaseMovementRepository.findAll();
        assertThat(trainingFaseMovementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingFaseMovement.class);
        TrainingFaseMovement trainingFaseMovement1 = new TrainingFaseMovement();
        trainingFaseMovement1.setId(1L);
        TrainingFaseMovement trainingFaseMovement2 = new TrainingFaseMovement();
        trainingFaseMovement2.setId(trainingFaseMovement1.getId());
        assertThat(trainingFaseMovement1).isEqualTo(trainingFaseMovement2);
        trainingFaseMovement2.setId(2L);
        assertThat(trainingFaseMovement1).isNotEqualTo(trainingFaseMovement2);
        trainingFaseMovement1.setId(null);
        assertThat(trainingFaseMovement1).isNotEqualTo(trainingFaseMovement2);
    }
}
