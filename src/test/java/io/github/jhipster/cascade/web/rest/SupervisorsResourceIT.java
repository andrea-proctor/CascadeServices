package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.Supervisors;
import io.github.jhipster.cascade.repository.SupervisorsRepository;
import io.github.jhipster.cascade.repository.search.SupervisorsSearchRepository;
import io.github.jhipster.cascade.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.github.jhipster.cascade.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SupervisorsResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class SupervisorsResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    @Autowired
    private SupervisorsRepository supervisorsRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.SupervisorsSearchRepositoryMockConfiguration
     */
    @Autowired
    private SupervisorsSearchRepository mockSupervisorsSearchRepository;

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

    private MockMvc restSupervisorsMockMvc;

    private Supervisors supervisors;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupervisorsResource supervisorsResource = new SupervisorsResource(supervisorsRepository, mockSupervisorsSearchRepository);
        this.restSupervisorsMockMvc = MockMvcBuilders.standaloneSetup(supervisorsResource)
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
    public static Supervisors createEntity(EntityManager em) {
        Supervisors supervisors = new Supervisors()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .site(DEFAULT_SITE);
        return supervisors;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supervisors createUpdatedEntity(EntityManager em) {
        Supervisors supervisors = new Supervisors()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .site(UPDATED_SITE);
        return supervisors;
    }

    @BeforeEach
    public void initTest() {
        supervisors = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupervisors() throws Exception {
        int databaseSizeBeforeCreate = supervisorsRepository.findAll().size();

        // Create the Supervisors
        restSupervisorsMockMvc.perform(post("/api/supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisors)))
            .andExpect(status().isCreated());

        // Validate the Supervisors in the database
        List<Supervisors> supervisorsList = supervisorsRepository.findAll();
        assertThat(supervisorsList).hasSize(databaseSizeBeforeCreate + 1);
        Supervisors testSupervisors = supervisorsList.get(supervisorsList.size() - 1);
        assertThat(testSupervisors.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSupervisors.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSupervisors.getSite()).isEqualTo(DEFAULT_SITE);

        // Validate the Supervisors in Elasticsearch
        verify(mockSupervisorsSearchRepository, times(1)).save(testSupervisors);
    }

    @Test
    @Transactional
    public void createSupervisorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supervisorsRepository.findAll().size();

        // Create the Supervisors with an existing ID
        supervisors.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupervisorsMockMvc.perform(post("/api/supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisors)))
            .andExpect(status().isBadRequest());

        // Validate the Supervisors in the database
        List<Supervisors> supervisorsList = supervisorsRepository.findAll();
        assertThat(supervisorsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Supervisors in Elasticsearch
        verify(mockSupervisorsSearchRepository, times(0)).save(supervisors);
    }


    @Test
    @Transactional
    public void getAllSupervisors() throws Exception {
        // Initialize the database
        supervisorsRepository.saveAndFlush(supervisors);

        // Get all the supervisorsList
        restSupervisorsMockMvc.perform(get("/api/supervisors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supervisors.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())));
    }
    
    @Test
    @Transactional
    public void getSupervisors() throws Exception {
        // Initialize the database
        supervisorsRepository.saveAndFlush(supervisors);

        // Get the supervisors
        restSupervisorsMockMvc.perform(get("/api/supervisors/{id}", supervisors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supervisors.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupervisors() throws Exception {
        // Get the supervisors
        restSupervisorsMockMvc.perform(get("/api/supervisors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupervisors() throws Exception {
        // Initialize the database
        supervisorsRepository.saveAndFlush(supervisors);

        int databaseSizeBeforeUpdate = supervisorsRepository.findAll().size();

        // Update the supervisors
        Supervisors updatedSupervisors = supervisorsRepository.findById(supervisors.getId()).get();
        // Disconnect from session so that the updates on updatedSupervisors are not directly saved in db
        em.detach(updatedSupervisors);
        updatedSupervisors
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .site(UPDATED_SITE);

        restSupervisorsMockMvc.perform(put("/api/supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupervisors)))
            .andExpect(status().isOk());

        // Validate the Supervisors in the database
        List<Supervisors> supervisorsList = supervisorsRepository.findAll();
        assertThat(supervisorsList).hasSize(databaseSizeBeforeUpdate);
        Supervisors testSupervisors = supervisorsList.get(supervisorsList.size() - 1);
        assertThat(testSupervisors.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupervisors.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSupervisors.getSite()).isEqualTo(UPDATED_SITE);

        // Validate the Supervisors in Elasticsearch
        verify(mockSupervisorsSearchRepository, times(1)).save(testSupervisors);
    }

    @Test
    @Transactional
    public void updateNonExistingSupervisors() throws Exception {
        int databaseSizeBeforeUpdate = supervisorsRepository.findAll().size();

        // Create the Supervisors

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupervisorsMockMvc.perform(put("/api/supervisors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supervisors)))
            .andExpect(status().isBadRequest());

        // Validate the Supervisors in the database
        List<Supervisors> supervisorsList = supervisorsRepository.findAll();
        assertThat(supervisorsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Supervisors in Elasticsearch
        verify(mockSupervisorsSearchRepository, times(0)).save(supervisors);
    }

    @Test
    @Transactional
    public void deleteSupervisors() throws Exception {
        // Initialize the database
        supervisorsRepository.saveAndFlush(supervisors);

        int databaseSizeBeforeDelete = supervisorsRepository.findAll().size();

        // Delete the supervisors
        restSupervisorsMockMvc.perform(delete("/api/supervisors/{id}", supervisors.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supervisors> supervisorsList = supervisorsRepository.findAll();
        assertThat(supervisorsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Supervisors in Elasticsearch
        verify(mockSupervisorsSearchRepository, times(1)).deleteById(supervisors.getId());
    }

    @Test
    @Transactional
    public void searchSupervisors() throws Exception {
        // Initialize the database
        supervisorsRepository.saveAndFlush(supervisors);
        when(mockSupervisorsSearchRepository.search(queryStringQuery("id:" + supervisors.getId())))
            .thenReturn(Collections.singletonList(supervisors));
        // Search the supervisors
        restSupervisorsMockMvc.perform(get("/api/_search/supervisors?query=id:" + supervisors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supervisors.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supervisors.class);
        Supervisors supervisors1 = new Supervisors();
        supervisors1.setId(1L);
        Supervisors supervisors2 = new Supervisors();
        supervisors2.setId(supervisors1.getId());
        assertThat(supervisors1).isEqualTo(supervisors2);
        supervisors2.setId(2L);
        assertThat(supervisors1).isNotEqualTo(supervisors2);
        supervisors1.setId(null);
        assertThat(supervisors1).isNotEqualTo(supervisors2);
    }
}
