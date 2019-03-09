package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.TestOnlineApp;

import io.github.jhipster.application.domain.Fase;
import io.github.jhipster.application.repository.FaseRepository;
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
 * Test class for the FaseResource REST controller.
 *
 * @see FaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOnlineApp.class)
public class FaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FaseRepository faseRepository;

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

    private MockMvc restFaseMockMvc;

    private Fase fase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaseResource faseResource = new FaseResource(faseRepository);
        this.restFaseMockMvc = MockMvcBuilders.standaloneSetup(faseResource)
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
    public static Fase createEntity(EntityManager em) {
        Fase fase = new Fase()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return fase;
    }

    @Before
    public void initTest() {
        fase = createEntity(em);
    }

    @Test
    @Transactional
    public void createFase() throws Exception {
        int databaseSizeBeforeCreate = faseRepository.findAll().size();

        // Create the Fase
        restFaseMockMvc.perform(post("/api/fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fase)))
            .andExpect(status().isCreated());

        // Validate the Fase in the database
        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeCreate + 1);
        Fase testFase = faseList.get(faseList.size() - 1);
        assertThat(testFase.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFase.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faseRepository.findAll().size();

        // Create the Fase with an existing ID
        fase.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaseMockMvc.perform(post("/api/fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fase)))
            .andExpect(status().isBadRequest());

        // Validate the Fase in the database
        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = faseRepository.findAll().size();
        // set the field null
        fase.setName(null);

        // Create the Fase, which fails.

        restFaseMockMvc.perform(post("/api/fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fase)))
            .andExpect(status().isBadRequest());

        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFases() throws Exception {
        // Initialize the database
        faseRepository.saveAndFlush(fase);

        // Get all the faseList
        restFaseMockMvc.perform(get("/api/fases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFase() throws Exception {
        // Initialize the database
        faseRepository.saveAndFlush(fase);

        // Get the fase
        restFaseMockMvc.perform(get("/api/fases/{id}", fase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFase() throws Exception {
        // Get the fase
        restFaseMockMvc.perform(get("/api/fases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFase() throws Exception {
        // Initialize the database
        faseRepository.saveAndFlush(fase);

        int databaseSizeBeforeUpdate = faseRepository.findAll().size();

        // Update the fase
        Fase updatedFase = faseRepository.findById(fase.getId()).get();
        // Disconnect from session so that the updates on updatedFase are not directly saved in db
        em.detach(updatedFase);
        updatedFase
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restFaseMockMvc.perform(put("/api/fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFase)))
            .andExpect(status().isOk());

        // Validate the Fase in the database
        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeUpdate);
        Fase testFase = faseList.get(faseList.size() - 1);
        assertThat(testFase.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFase.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFase() throws Exception {
        int databaseSizeBeforeUpdate = faseRepository.findAll().size();

        // Create the Fase

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaseMockMvc.perform(put("/api/fases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fase)))
            .andExpect(status().isBadRequest());

        // Validate the Fase in the database
        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFase() throws Exception {
        // Initialize the database
        faseRepository.saveAndFlush(fase);

        int databaseSizeBeforeDelete = faseRepository.findAll().size();

        // Delete the fase
        restFaseMockMvc.perform(delete("/api/fases/{id}", fase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fase> faseList = faseRepository.findAll();
        assertThat(faseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fase.class);
        Fase fase1 = new Fase();
        fase1.setId(1L);
        Fase fase2 = new Fase();
        fase2.setId(fase1.getId());
        assertThat(fase1).isEqualTo(fase2);
        fase2.setId(2L);
        assertThat(fase1).isNotEqualTo(fase2);
        fase1.setId(null);
        assertThat(fase1).isNotEqualTo(fase2);
    }
}
