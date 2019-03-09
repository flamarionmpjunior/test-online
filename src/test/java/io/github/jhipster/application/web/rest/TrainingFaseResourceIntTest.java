package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.TrainingFase;
import io.github.jhipster.application.repository.TrainingFaseRepository;
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
 * Test class for the TrainingFaseResource REST controller.
 *
 * @see TrainingFaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class TrainingFaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TrainingFaseRepository trainingFaseRepository;

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

    private MockMvc restTrainingFaseMockMvc;

    private TrainingFase trainingFase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrainingFaseResource trainingFaseResource = new TrainingFaseResource(trainingFaseRepository);
        this.restTrainingFaseMockMvc = MockMvcBuilders.standaloneSetup(trainingFaseResource)
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
    public static TrainingFase createEntity(EntityManager em) {
        TrainingFase trainingFase = new TrainingFase()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return trainingFase;
    }

    @Before
    public void initTest() {
        trainingFase = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrainingFase() throws Exception {
        int databaseSizeBeforeCreate = trainingFaseRepository.findAll().size();

        // Create the TrainingFase
        restTrainingFaseMockMvc.perform(post("/api/training-fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFase)))
            .andExpect(status().isCreated());

        // Validate the TrainingFase in the database
        List<TrainingFase> trainingFaseList = trainingFaseRepository.findAll();
        assertThat(trainingFaseList).hasSize(databaseSizeBeforeCreate + 1);
        TrainingFase testTrainingFase = trainingFaseList.get(trainingFaseList.size() - 1);
        assertThat(testTrainingFase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrainingFase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTrainingFaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingFaseRepository.findAll().size();

        // Create the TrainingFase with an existing ID
        trainingFase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingFaseMockMvc.perform(post("/api/training-fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFase)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingFase in the database
        List<TrainingFase> trainingFaseList = trainingFaseRepository.findAll();
        assertThat(trainingFaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrainingFases() throws Exception {
        // Initialize the database
        trainingFaseRepository.saveAndFlush(trainingFase);

        // Get all the trainingFaseList
        restTrainingFaseMockMvc.perform(get("/api/training-fases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainingFase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getTrainingFase() throws Exception {
        // Initialize the database
        trainingFaseRepository.saveAndFlush(trainingFase);

        // Get the trainingFase
        restTrainingFaseMockMvc.perform(get("/api/training-fases/{id}", trainingFase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trainingFase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrainingFase() throws Exception {
        // Get the trainingFase
        restTrainingFaseMockMvc.perform(get("/api/training-fases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrainingFase() throws Exception {
        // Initialize the database
        trainingFaseRepository.saveAndFlush(trainingFase);

        int databaseSizeBeforeUpdate = trainingFaseRepository.findAll().size();

        // Update the trainingFase
        TrainingFase updatedTrainingFase = trainingFaseRepository.findById(trainingFase.getId()).get();
        // Disconnect from session so that the updates on updatedTrainingFase are not directly saved in db
        em.detach(updatedTrainingFase);
        updatedTrainingFase
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTrainingFaseMockMvc.perform(put("/api/training-fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrainingFase)))
            .andExpect(status().isOk());

        // Validate the TrainingFase in the database
        List<TrainingFase> trainingFaseList = trainingFaseRepository.findAll();
        assertThat(trainingFaseList).hasSize(databaseSizeBeforeUpdate);
        TrainingFase testTrainingFase = trainingFaseList.get(trainingFaseList.size() - 1);
        assertThat(testTrainingFase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrainingFase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTrainingFase() throws Exception {
        int databaseSizeBeforeUpdate = trainingFaseRepository.findAll().size();

        // Create the TrainingFase

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingFaseMockMvc.perform(put("/api/training-fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trainingFase)))
            .andExpect(status().isBadRequest());

        // Validate the TrainingFase in the database
        List<TrainingFase> trainingFaseList = trainingFaseRepository.findAll();
        assertThat(trainingFaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrainingFase() throws Exception {
        // Initialize the database
        trainingFaseRepository.saveAndFlush(trainingFase);

        int databaseSizeBeforeDelete = trainingFaseRepository.findAll().size();

        // Delete the trainingFase
        restTrainingFaseMockMvc.perform(delete("/api/training-fases/{id}", trainingFase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrainingFase> trainingFaseList = trainingFaseRepository.findAll();
        assertThat(trainingFaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainingFase.class);
        TrainingFase trainingFase1 = new TrainingFase();
        trainingFase1.setId(1L);
        TrainingFase trainingFase2 = new TrainingFase();
        trainingFase2.setId(trainingFase1.getId());
        assertThat(trainingFase1).isEqualTo(trainingFase2);
        trainingFase2.setId(2L);
        assertThat(trainingFase1).isNotEqualTo(trainingFase2);
        trainingFase1.setId(null);
        assertThat(trainingFase1).isNotEqualTo(trainingFase2);
    }
}
