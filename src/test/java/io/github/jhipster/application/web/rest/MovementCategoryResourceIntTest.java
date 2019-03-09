package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.MovementCategory;
import io.github.jhipster.application.repository.MovementCategoryRepository;
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
 * Test class for the MovementCategoryResource REST controller.
 *
 * @see MovementCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class MovementCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private MovementCategoryRepository movementCategoryRepository;

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

    private MockMvc restMovementCategoryMockMvc;

    private MovementCategory movementCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovementCategoryResource movementCategoryResource = new MovementCategoryResource(movementCategoryRepository);
        this.restMovementCategoryMockMvc = MockMvcBuilders.standaloneSetup(movementCategoryResource)
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
    public static MovementCategory createEntity(EntityManager em) {
        MovementCategory movementCategory = new MovementCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return movementCategory;
    }

    @Before
    public void initTest() {
        movementCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovementCategory() throws Exception {
        int databaseSizeBeforeCreate = movementCategoryRepository.findAll().size();

        // Create the MovementCategory
        restMovementCategoryMockMvc.perform(post("/api/movement-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementCategory)))
            .andExpect(status().isCreated());

        // Validate the MovementCategory in the database
        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        MovementCategory testMovementCategory = movementCategoryList.get(movementCategoryList.size() - 1);
        assertThat(testMovementCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMovementCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMovementCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movementCategoryRepository.findAll().size();

        // Create the MovementCategory with an existing ID
        movementCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovementCategoryMockMvc.perform(post("/api/movement-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MovementCategory in the database
        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementCategoryRepository.findAll().size();
        // set the field null
        movementCategory.setName(null);

        // Create the MovementCategory, which fails.

        restMovementCategoryMockMvc.perform(post("/api/movement-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementCategory)))
            .andExpect(status().isBadRequest());

        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovementCategories() throws Exception {
        // Initialize the database
        movementCategoryRepository.saveAndFlush(movementCategory);

        // Get all the movementCategoryList
        restMovementCategoryMockMvc.perform(get("/api/movement-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movementCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getMovementCategory() throws Exception {
        // Initialize the database
        movementCategoryRepository.saveAndFlush(movementCategory);

        // Get the movementCategory
        restMovementCategoryMockMvc.perform(get("/api/movement-categories/{id}", movementCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movementCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovementCategory() throws Exception {
        // Get the movementCategory
        restMovementCategoryMockMvc.perform(get("/api/movement-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovementCategory() throws Exception {
        // Initialize the database
        movementCategoryRepository.saveAndFlush(movementCategory);

        int databaseSizeBeforeUpdate = movementCategoryRepository.findAll().size();

        // Update the movementCategory
        MovementCategory updatedMovementCategory = movementCategoryRepository.findById(movementCategory.getId()).get();
        // Disconnect from session so that the updates on updatedMovementCategory are not directly saved in db
        em.detach(updatedMovementCategory);
        updatedMovementCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restMovementCategoryMockMvc.perform(put("/api/movement-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovementCategory)))
            .andExpect(status().isOk());

        // Validate the MovementCategory in the database
        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeUpdate);
        MovementCategory testMovementCategory = movementCategoryList.get(movementCategoryList.size() - 1);
        assertThat(testMovementCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMovementCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMovementCategory() throws Exception {
        int databaseSizeBeforeUpdate = movementCategoryRepository.findAll().size();

        // Create the MovementCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovementCategoryMockMvc.perform(put("/api/movement-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movementCategory)))
            .andExpect(status().isBadRequest());

        // Validate the MovementCategory in the database
        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovementCategory() throws Exception {
        // Initialize the database
        movementCategoryRepository.saveAndFlush(movementCategory);

        int databaseSizeBeforeDelete = movementCategoryRepository.findAll().size();

        // Delete the movementCategory
        restMovementCategoryMockMvc.perform(delete("/api/movement-categories/{id}", movementCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MovementCategory> movementCategoryList = movementCategoryRepository.findAll();
        assertThat(movementCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MovementCategory.class);
        MovementCategory movementCategory1 = new MovementCategory();
        movementCategory1.setId(1L);
        MovementCategory movementCategory2 = new MovementCategory();
        movementCategory2.setId(movementCategory1.getId());
        assertThat(movementCategory1).isEqualTo(movementCategory2);
        movementCategory2.setId(2L);
        assertThat(movementCategory1).isNotEqualTo(movementCategory2);
        movementCategory1.setId(null);
        assertThat(movementCategory1).isNotEqualTo(movementCategory2);
    }
}
