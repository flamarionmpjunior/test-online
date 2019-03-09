package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.Training;
import io.github.jhipster.application.repository.TrainingRepository;
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
 * Test class for the TrainingResource REST controller.
 *
 * @see TrainingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class TrainingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TrainingRepository trainingRepository;

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

    private MockMvc restTrainingMockMvc;

    private Training training;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingResource trainingResource = new TrainingResource(trainingRepository);
        this.restTrainingMockMvc = MockMvcBuilders.standaloneSetup(trainingResource)
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
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return training;
    }

    @Before
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraining.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training with an existing ID
        training.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraining)))
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraining.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Create the Training

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(training)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Training.class);
        Training training1 = new Training();
        training1.setId(1L);
        Training training2 = new Training();
        training2.setId(training1.getId());
        assertThat(training1).isEqualTo(training2);
        training2.setId(2L);
        assertThat(training1).isNotEqualTo(training2);
        training1.setId(null);
        assertThat(training1).isNotEqualTo(training2);
    }
}
