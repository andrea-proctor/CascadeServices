package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.InventoryValues;
import io.github.jhipster.cascade.repository.InventoryValuesRepository;
import io.github.jhipster.cascade.repository.search.InventoryValuesSearchRepository;
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
 * Integration tests for the {@Link InventoryValuesResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class InventoryValuesResourceIT {

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PART_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_EACH = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_EACH = "BBBBBBBBBB";

    @Autowired
    private InventoryValuesRepository inventoryValuesRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.InventoryValuesSearchRepositoryMockConfiguration
     */
    @Autowired
    private InventoryValuesSearchRepository mockInventoryValuesSearchRepository;

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

    private MockMvc restInventoryValuesMockMvc;

    private InventoryValues inventoryValues;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventoryValuesResource inventoryValuesResource = new InventoryValuesResource(inventoryValuesRepository, mockInventoryValuesSearchRepository);
        this.restInventoryValuesMockMvc = MockMvcBuilders.standaloneSetup(inventoryValuesResource)
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
    public static InventoryValues createEntity(EntityManager em) {
        InventoryValues inventoryValues = new InventoryValues()
            .partNumber(DEFAULT_PART_NUMBER)
            .partDescription(DEFAULT_PART_DESCRIPTION)
            .valueEach(DEFAULT_VALUE_EACH);
        return inventoryValues;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryValues createUpdatedEntity(EntityManager em) {
        InventoryValues inventoryValues = new InventoryValues()
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .valueEach(UPDATED_VALUE_EACH);
        return inventoryValues;
    }

    @BeforeEach
    public void initTest() {
        inventoryValues = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryValues() throws Exception {
        int databaseSizeBeforeCreate = inventoryValuesRepository.findAll().size();

        // Create the InventoryValues
        restInventoryValuesMockMvc.perform(post("/api/inventory-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryValues)))
            .andExpect(status().isCreated());

        // Validate the InventoryValues in the database
        List<InventoryValues> inventoryValuesList = inventoryValuesRepository.findAll();
        assertThat(inventoryValuesList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryValues testInventoryValues = inventoryValuesList.get(inventoryValuesList.size() - 1);
        assertThat(testInventoryValues.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);
        assertThat(testInventoryValues.getPartDescription()).isEqualTo(DEFAULT_PART_DESCRIPTION);
        assertThat(testInventoryValues.getValueEach()).isEqualTo(DEFAULT_VALUE_EACH);

        // Validate the InventoryValues in Elasticsearch
        verify(mockInventoryValuesSearchRepository, times(1)).save(testInventoryValues);
    }

    @Test
    @Transactional
    public void createInventoryValuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryValuesRepository.findAll().size();

        // Create the InventoryValues with an existing ID
        inventoryValues.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryValuesMockMvc.perform(post("/api/inventory-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryValues)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryValues in the database
        List<InventoryValues> inventoryValuesList = inventoryValuesRepository.findAll();
        assertThat(inventoryValuesList).hasSize(databaseSizeBeforeCreate);

        // Validate the InventoryValues in Elasticsearch
        verify(mockInventoryValuesSearchRepository, times(0)).save(inventoryValues);
    }


    @Test
    @Transactional
    public void getAllInventoryValues() throws Exception {
        // Initialize the database
        inventoryValuesRepository.saveAndFlush(inventoryValues);

        // Get all the inventoryValuesList
        restInventoryValuesMockMvc.perform(get("/api/inventory-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].valueEach").value(hasItem(DEFAULT_VALUE_EACH.toString())));
    }
    
    @Test
    @Transactional
    public void getInventoryValues() throws Exception {
        // Initialize the database
        inventoryValuesRepository.saveAndFlush(inventoryValues);

        // Get the inventoryValues
        restInventoryValuesMockMvc.perform(get("/api/inventory-values/{id}", inventoryValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryValues.getId().intValue()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.toString()))
            .andExpect(jsonPath("$.partDescription").value(DEFAULT_PART_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.valueEach").value(DEFAULT_VALUE_EACH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryValues() throws Exception {
        // Get the inventoryValues
        restInventoryValuesMockMvc.perform(get("/api/inventory-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryValues() throws Exception {
        // Initialize the database
        inventoryValuesRepository.saveAndFlush(inventoryValues);

        int databaseSizeBeforeUpdate = inventoryValuesRepository.findAll().size();

        // Update the inventoryValues
        InventoryValues updatedInventoryValues = inventoryValuesRepository.findById(inventoryValues.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryValues are not directly saved in db
        em.detach(updatedInventoryValues);
        updatedInventoryValues
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .valueEach(UPDATED_VALUE_EACH);

        restInventoryValuesMockMvc.perform(put("/api/inventory-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryValues)))
            .andExpect(status().isOk());

        // Validate the InventoryValues in the database
        List<InventoryValues> inventoryValuesList = inventoryValuesRepository.findAll();
        assertThat(inventoryValuesList).hasSize(databaseSizeBeforeUpdate);
        InventoryValues testInventoryValues = inventoryValuesList.get(inventoryValuesList.size() - 1);
        assertThat(testInventoryValues.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);
        assertThat(testInventoryValues.getPartDescription()).isEqualTo(UPDATED_PART_DESCRIPTION);
        assertThat(testInventoryValues.getValueEach()).isEqualTo(UPDATED_VALUE_EACH);

        // Validate the InventoryValues in Elasticsearch
        verify(mockInventoryValuesSearchRepository, times(1)).save(testInventoryValues);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryValues() throws Exception {
        int databaseSizeBeforeUpdate = inventoryValuesRepository.findAll().size();

        // Create the InventoryValues

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryValuesMockMvc.perform(put("/api/inventory-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventoryValues)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryValues in the database
        List<InventoryValues> inventoryValuesList = inventoryValuesRepository.findAll();
        assertThat(inventoryValuesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InventoryValues in Elasticsearch
        verify(mockInventoryValuesSearchRepository, times(0)).save(inventoryValues);
    }

    @Test
    @Transactional
    public void deleteInventoryValues() throws Exception {
        // Initialize the database
        inventoryValuesRepository.saveAndFlush(inventoryValues);

        int databaseSizeBeforeDelete = inventoryValuesRepository.findAll().size();

        // Delete the inventoryValues
        restInventoryValuesMockMvc.perform(delete("/api/inventory-values/{id}", inventoryValues.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryValues> inventoryValuesList = inventoryValuesRepository.findAll();
        assertThat(inventoryValuesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InventoryValues in Elasticsearch
        verify(mockInventoryValuesSearchRepository, times(1)).deleteById(inventoryValues.getId());
    }

    @Test
    @Transactional
    public void searchInventoryValues() throws Exception {
        // Initialize the database
        inventoryValuesRepository.saveAndFlush(inventoryValues);
        when(mockInventoryValuesSearchRepository.search(queryStringQuery("id:" + inventoryValues.getId())))
            .thenReturn(Collections.singletonList(inventoryValues));
        // Search the inventoryValues
        restInventoryValuesMockMvc.perform(get("/api/_search/inventory-values?query=id:" + inventoryValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].valueEach").value(hasItem(DEFAULT_VALUE_EACH)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryValues.class);
        InventoryValues inventoryValues1 = new InventoryValues();
        inventoryValues1.setId(1L);
        InventoryValues inventoryValues2 = new InventoryValues();
        inventoryValues2.setId(inventoryValues1.getId());
        assertThat(inventoryValues1).isEqualTo(inventoryValues2);
        inventoryValues2.setId(2L);
        assertThat(inventoryValues1).isNotEqualTo(inventoryValues2);
        inventoryValues1.setId(null);
        assertThat(inventoryValues1).isNotEqualTo(inventoryValues2);
    }
}
