package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.PoolInventory;
import io.github.jhipster.cascade.repository.PoolInventoryRepository;
import io.github.jhipster.cascade.repository.search.PoolInventorySearchRepository;
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

import io.github.jhipster.cascade.domain.enumeration.Language;
/**
 * Integration tests for the {@Link PoolInventoryResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class PoolInventoryResourceIT {

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PARTDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PARTDESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTYIN = 1;
    private static final Integer UPDATED_QTYIN = 2;

    private static final Integer DEFAULT_QTYOUT = 1;
    private static final Integer UPDATED_QTYOUT = 2;

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private PoolInventoryRepository poolInventoryRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.PoolInventorySearchRepositoryMockConfiguration
     */
    @Autowired
    private PoolInventorySearchRepository mockPoolInventorySearchRepository;

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

    private MockMvc restPoolInventoryMockMvc;

    private PoolInventory poolInventory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoolInventoryResource poolInventoryResource = new PoolInventoryResource(poolInventoryRepository, mockPoolInventorySearchRepository);
        this.restPoolInventoryMockMvc = MockMvcBuilders.standaloneSetup(poolInventoryResource)
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
    public static PoolInventory createEntity(EntityManager em) {
        PoolInventory poolInventory = new PoolInventory()
            .date(DEFAULT_DATE)
            .partnumber(DEFAULT_PARTNUMBER)
            .partdescription(DEFAULT_PARTDESCRIPTION)
            .qtyin(DEFAULT_QTYIN)
            .qtyout(DEFAULT_QTYOUT)
            .language(DEFAULT_LANGUAGE);
        return poolInventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoolInventory createUpdatedEntity(EntityManager em) {
        PoolInventory poolInventory = new PoolInventory()
            .date(UPDATED_DATE)
            .partnumber(UPDATED_PARTNUMBER)
            .partdescription(UPDATED_PARTDESCRIPTION)
            .qtyin(UPDATED_QTYIN)
            .qtyout(UPDATED_QTYOUT)
            .language(UPDATED_LANGUAGE);
        return poolInventory;
    }

    @BeforeEach
    public void initTest() {
        poolInventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoolInventory() throws Exception {
        int databaseSizeBeforeCreate = poolInventoryRepository.findAll().size();

        // Create the PoolInventory
        restPoolInventoryMockMvc.perform(post("/api/pool-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poolInventory)))
            .andExpect(status().isCreated());

        // Validate the PoolInventory in the database
        List<PoolInventory> poolInventoryList = poolInventoryRepository.findAll();
        assertThat(poolInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        PoolInventory testPoolInventory = poolInventoryList.get(poolInventoryList.size() - 1);
        assertThat(testPoolInventory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPoolInventory.getPartnumber()).isEqualTo(DEFAULT_PARTNUMBER);
        assertThat(testPoolInventory.getPartdescription()).isEqualTo(DEFAULT_PARTDESCRIPTION);
        assertThat(testPoolInventory.getQtyin()).isEqualTo(DEFAULT_QTYIN);
        assertThat(testPoolInventory.getQtyout()).isEqualTo(DEFAULT_QTYOUT);
        assertThat(testPoolInventory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the PoolInventory in Elasticsearch
        verify(mockPoolInventorySearchRepository, times(1)).save(testPoolInventory);
    }

    @Test
    @Transactional
    public void createPoolInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poolInventoryRepository.findAll().size();

        // Create the PoolInventory with an existing ID
        poolInventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoolInventoryMockMvc.perform(post("/api/pool-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poolInventory)))
            .andExpect(status().isBadRequest());

        // Validate the PoolInventory in the database
        List<PoolInventory> poolInventoryList = poolInventoryRepository.findAll();
        assertThat(poolInventoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the PoolInventory in Elasticsearch
        verify(mockPoolInventorySearchRepository, times(0)).save(poolInventory);
    }


    @Test
    @Transactional
    public void getAllPoolInventories() throws Exception {
        // Initialize the database
        poolInventoryRepository.saveAndFlush(poolInventory);

        // Get all the poolInventoryList
        restPoolInventoryMockMvc.perform(get("/api/pool-inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poolInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].partnumber").value(hasItem(DEFAULT_PARTNUMBER.toString())))
            .andExpect(jsonPath("$.[*].partdescription").value(hasItem(DEFAULT_PARTDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].qtyin").value(hasItem(DEFAULT_QTYIN)))
            .andExpect(jsonPath("$.[*].qtyout").value(hasItem(DEFAULT_QTYOUT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getPoolInventory() throws Exception {
        // Initialize the database
        poolInventoryRepository.saveAndFlush(poolInventory);

        // Get the poolInventory
        restPoolInventoryMockMvc.perform(get("/api/pool-inventories/{id}", poolInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poolInventory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.partnumber").value(DEFAULT_PARTNUMBER.toString()))
            .andExpect(jsonPath("$.partdescription").value(DEFAULT_PARTDESCRIPTION.toString()))
            .andExpect(jsonPath("$.qtyin").value(DEFAULT_QTYIN))
            .andExpect(jsonPath("$.qtyout").value(DEFAULT_QTYOUT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPoolInventory() throws Exception {
        // Get the poolInventory
        restPoolInventoryMockMvc.perform(get("/api/pool-inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoolInventory() throws Exception {
        // Initialize the database
        poolInventoryRepository.saveAndFlush(poolInventory);

        int databaseSizeBeforeUpdate = poolInventoryRepository.findAll().size();

        // Update the poolInventory
        PoolInventory updatedPoolInventory = poolInventoryRepository.findById(poolInventory.getId()).get();
        // Disconnect from session so that the updates on updatedPoolInventory are not directly saved in db
        em.detach(updatedPoolInventory);
        updatedPoolInventory
            .date(UPDATED_DATE)
            .partnumber(UPDATED_PARTNUMBER)
            .partdescription(UPDATED_PARTDESCRIPTION)
            .qtyin(UPDATED_QTYIN)
            .qtyout(UPDATED_QTYOUT)
            .language(UPDATED_LANGUAGE);

        restPoolInventoryMockMvc.perform(put("/api/pool-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoolInventory)))
            .andExpect(status().isOk());

        // Validate the PoolInventory in the database
        List<PoolInventory> poolInventoryList = poolInventoryRepository.findAll();
        assertThat(poolInventoryList).hasSize(databaseSizeBeforeUpdate);
        PoolInventory testPoolInventory = poolInventoryList.get(poolInventoryList.size() - 1);
        assertThat(testPoolInventory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPoolInventory.getPartnumber()).isEqualTo(UPDATED_PARTNUMBER);
        assertThat(testPoolInventory.getPartdescription()).isEqualTo(UPDATED_PARTDESCRIPTION);
        assertThat(testPoolInventory.getQtyin()).isEqualTo(UPDATED_QTYIN);
        assertThat(testPoolInventory.getQtyout()).isEqualTo(UPDATED_QTYOUT);
        assertThat(testPoolInventory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the PoolInventory in Elasticsearch
        verify(mockPoolInventorySearchRepository, times(1)).save(testPoolInventory);
    }

    @Test
    @Transactional
    public void updateNonExistingPoolInventory() throws Exception {
        int databaseSizeBeforeUpdate = poolInventoryRepository.findAll().size();

        // Create the PoolInventory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoolInventoryMockMvc.perform(put("/api/pool-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poolInventory)))
            .andExpect(status().isBadRequest());

        // Validate the PoolInventory in the database
        List<PoolInventory> poolInventoryList = poolInventoryRepository.findAll();
        assertThat(poolInventoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PoolInventory in Elasticsearch
        verify(mockPoolInventorySearchRepository, times(0)).save(poolInventory);
    }

    @Test
    @Transactional
    public void deletePoolInventory() throws Exception {
        // Initialize the database
        poolInventoryRepository.saveAndFlush(poolInventory);

        int databaseSizeBeforeDelete = poolInventoryRepository.findAll().size();

        // Delete the poolInventory
        restPoolInventoryMockMvc.perform(delete("/api/pool-inventories/{id}", poolInventory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoolInventory> poolInventoryList = poolInventoryRepository.findAll();
        assertThat(poolInventoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PoolInventory in Elasticsearch
        verify(mockPoolInventorySearchRepository, times(1)).deleteById(poolInventory.getId());
    }

    @Test
    @Transactional
    public void searchPoolInventory() throws Exception {
        // Initialize the database
        poolInventoryRepository.saveAndFlush(poolInventory);
        when(mockPoolInventorySearchRepository.search(queryStringQuery("id:" + poolInventory.getId())))
            .thenReturn(Collections.singletonList(poolInventory));
        // Search the poolInventory
        restPoolInventoryMockMvc.perform(get("/api/_search/pool-inventories?query=id:" + poolInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poolInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].partnumber").value(hasItem(DEFAULT_PARTNUMBER)))
            .andExpect(jsonPath("$.[*].partdescription").value(hasItem(DEFAULT_PARTDESCRIPTION)))
            .andExpect(jsonPath("$.[*].qtyin").value(hasItem(DEFAULT_QTYIN)))
            .andExpect(jsonPath("$.[*].qtyout").value(hasItem(DEFAULT_QTYOUT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoolInventory.class);
        PoolInventory poolInventory1 = new PoolInventory();
        poolInventory1.setId(1L);
        PoolInventory poolInventory2 = new PoolInventory();
        poolInventory2.setId(poolInventory1.getId());
        assertThat(poolInventory1).isEqualTo(poolInventory2);
        poolInventory2.setId(2L);
        assertThat(poolInventory1).isNotEqualTo(poolInventory2);
        poolInventory1.setId(null);
        assertThat(poolInventory1).isNotEqualTo(poolInventory2);
    }
}
