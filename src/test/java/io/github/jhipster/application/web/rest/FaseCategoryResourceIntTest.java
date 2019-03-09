package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.FaseCategory;
import io.github.jhipster.application.repository.FaseCategoryRepository;
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
 * Test class for the FaseCategoryResource REST controller.
 *
 * @see FaseCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class FaseCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GAME_SCORE = false;
    private static final Boolean UPDATED_GAME_SCORE = true;

    @Autowired
    private FaseCategoryRepository faseCategoryRepository;

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

    private MockMvc restFaseCategoryMockMvc;

    private FaseCategory faseCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaseCategoryResource faseCategoryResource = new FaseCategoryResource(faseCategoryRepository);
        this.restFaseCategoryMockMvc = MockMvcBuilders.standaloneSetup(faseCategoryResource)
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
    public static FaseCategory createEntity(EntityManager em) {
        FaseCategory faseCategory = new FaseCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .gameScore(DEFAULT_GAME_SCORE);
        return faseCategory;
    }

    @Before
    public void initTest() {
        faseCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaseCategory() throws Exception {
        int databaseSizeBeforeCreate = faseCategoryRepository.findAll().size();

        // Create the FaseCategory
        restFaseCategoryMockMvc.perform(post("/api/fase-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseCategory)))
            .andExpect(status().isCreated());

        // Validate the FaseCategory in the database
        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        FaseCategory testFaseCategory = faseCategoryList.get(faseCategoryList.size() - 1);
        assertThat(testFaseCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFaseCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFaseCategory.isGameScore()).isEqualTo(DEFAULT_GAME_SCORE);
    }

    @Test
    @Transactional
    public void createFaseCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faseCategoryRepository.findAll().size();

        // Create the FaseCategory with an existing ID
        faseCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaseCategoryMockMvc.perform(post("/api/fase-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseCategory)))
            .andExpect(status().isBadRequest());

        // Validate the FaseCategory in the database
        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = faseCategoryRepository.findAll().size();
        // set the field null
        faseCategory.setName(null);

        // Create the FaseCategory, which fails.

        restFaseCategoryMockMvc.perform(post("/api/fase-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseCategory)))
            .andExpect(status().isBadRequest());

        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaseCategories() throws Exception {
        // Initialize the database
        faseCategoryRepository.saveAndFlush(faseCategory);

        // Get all the faseCategoryList
        restFaseCategoryMockMvc.perform(get("/api/fase-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faseCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].gameScore").value(hasItem(DEFAULT_GAME_SCORE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFaseCategory() throws Exception {
        // Initialize the database
        faseCategoryRepository.saveAndFlush(faseCategory);

        // Get the faseCategory
        restFaseCategoryMockMvc.perform(get("/api/fase-categories/{id}", faseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faseCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.gameScore").value(DEFAULT_GAME_SCORE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFaseCategory() throws Exception {
        // Get the faseCategory
        restFaseCategoryMockMvc.perform(get("/api/fase-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaseCategory() throws Exception {
        // Initialize the database
        faseCategoryRepository.saveAndFlush(faseCategory);

        int databaseSizeBeforeUpdate = faseCategoryRepository.findAll().size();

        // Update the faseCategory
        FaseCategory updatedFaseCategory = faseCategoryRepository.findById(faseCategory.getId()).get();
        // Disconnect from session so that the updates on updatedFaseCategory are not directly saved in db
        em.detach(updatedFaseCategory);
        updatedFaseCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .gameScore(UPDATED_GAME_SCORE);

        restFaseCategoryMockMvc.perform(put("/api/fase-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaseCategory)))
            .andExpect(status().isOk());

        // Validate the FaseCategory in the database
        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeUpdate);
        FaseCategory testFaseCategory = faseCategoryList.get(faseCategoryList.size() - 1);
        assertThat(testFaseCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFaseCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFaseCategory.isGameScore()).isEqualTo(UPDATED_GAME_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingFaseCategory() throws Exception {
        int databaseSizeBeforeUpdate = faseCategoryRepository.findAll().size();

        // Create the FaseCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaseCategoryMockMvc.perform(put("/api/fase-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseCategory)))
            .andExpect(status().isBadRequest());

        // Validate the FaseCategory in the database
        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaseCategory() throws Exception {
        // Initialize the database
        faseCategoryRepository.saveAndFlush(faseCategory);

        int databaseSizeBeforeDelete = faseCategoryRepository.findAll().size();

        // Delete the faseCategory
        restFaseCategoryMockMvc.perform(delete("/api/fase-categories/{id}", faseCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FaseCategory> faseCategoryList = faseCategoryRepository.findAll();
        assertThat(faseCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaseCategory.class);
        FaseCategory faseCategory1 = new FaseCategory();
        faseCategory1.setId(1L);
        FaseCategory faseCategory2 = new FaseCategory();
        faseCategory2.setId(faseCategory1.getId());
        assertThat(faseCategory1).isEqualTo(faseCategory2);
        faseCategory2.setId(2L);
        assertThat(faseCategory1).isNotEqualTo(faseCategory2);
        faseCategory1.setId(null);
        assertThat(faseCategory1).isNotEqualTo(faseCategory2);
    }
}
