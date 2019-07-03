package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.TruckInventory;
import io.github.jhipster.cascade.repository.TruckInventoryRepository;
import io.github.jhipster.cascade.repository.search.TruckInventorySearchRepository;
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
 * Integration tests for the {@Link TruckInventoryResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class TruckInventoryResourceIT {

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_TRUCK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRUCK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PART_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY_ON_HAND = 1;
    private static final Integer UPDATED_QTY_ON_HAND = 2;

    private static final Integer DEFAULT_QTY_OUT = 1;
    private static final Integer UPDATED_QTY_OUT = 2;

    @Autowired
    private TruckInventoryRepository truckInventoryRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.TruckInventorySearchRepositoryMockConfiguration
     */
    @Autowired
    private TruckInventorySearchRepository mockTruckInventorySearchRepository;

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

    private MockMvc restTruckInventoryMockMvc;

    private TruckInventory truckInventory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TruckInventoryResource truckInventoryResource = new TruckInventoryResource(truckInventoryRepository, mockTruckInventorySearchRepository);
        this.restTruckInventoryMockMvc = MockMvcBuilders.standaloneSetup(truckInventoryResource)
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
    public static TruckInventory createEntity(EntityManager em) {
        TruckInventory truckInventory = new TruckInventory()
            .date(DEFAULT_DATE)
            .truckNumber(DEFAULT_TRUCK_NUMBER)
            .site(DEFAULT_SITE)
            .partNumber(DEFAULT_PART_NUMBER)
            .partDescription(DEFAULT_PART_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .qtyOnHand(DEFAULT_QTY_ON_HAND)
            .qtyOut(DEFAULT_QTY_OUT);
        return truckInventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TruckInventory createUpdatedEntity(EntityManager em) {
        TruckInventory truckInventory = new TruckInventory()
            .date(UPDATED_DATE)
            .truckNumber(UPDATED_TRUCK_NUMBER)
            .site(UPDATED_SITE)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .qtyOnHand(UPDATED_QTY_ON_HAND)
            .qtyOut(UPDATED_QTY_OUT);
        return truckInventory;
    }

    @BeforeEach
    public void initTest() {
        truckInventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTruckInventory() throws Exception {
        int databaseSizeBeforeCreate = truckInventoryRepository.findAll().size();

        // Create the TruckInventory
        restTruckInventoryMockMvc.perform(post("/api/truck-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckInventory)))
            .andExpect(status().isCreated());

        // Validate the TruckInventory in the database
        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        TruckInventory testTruckInventory = truckInventoryList.get(truckInventoryList.size() - 1);
        assertThat(testTruckInventory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTruckInventory.getTruckNumber()).isEqualTo(DEFAULT_TRUCK_NUMBER);
        assertThat(testTruckInventory.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testTruckInventory.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);
        assertThat(testTruckInventory.getPartDescription()).isEqualTo(DEFAULT_PART_DESCRIPTION);
        assertThat(testTruckInventory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTruckInventory.getQtyOnHand()).isEqualTo(DEFAULT_QTY_ON_HAND);
        assertThat(testTruckInventory.getQtyOut()).isEqualTo(DEFAULT_QTY_OUT);

        // Validate the TruckInventory in Elasticsearch
        verify(mockTruckInventorySearchRepository, times(1)).save(testTruckInventory);
    }

    @Test
    @Transactional
    public void createTruckInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = truckInventoryRepository.findAll().size();

        // Create the TruckInventory with an existing ID
        truckInventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTruckInventoryMockMvc.perform(post("/api/truck-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckInventory)))
            .andExpect(status().isBadRequest());

        // Validate the TruckInventory in the database
        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the TruckInventory in Elasticsearch
        verify(mockTruckInventorySearchRepository, times(0)).save(truckInventory);
    }


    @Test
    @Transactional
    public void checkTruckNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = truckInventoryRepository.findAll().size();
        // set the field null
        truckInventory.setTruckNumber(null);

        // Create the TruckInventory, which fails.

        restTruckInventoryMockMvc.perform(post("/api/truck-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckInventory)))
            .andExpect(status().isBadRequest());

        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTruckInventories() throws Exception {
        // Initialize the database
        truckInventoryRepository.saveAndFlush(truckInventory);

        // Get all the truckInventoryList
        restTruckInventoryMockMvc.perform(get("/api/truck-inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truckInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].truckNumber").value(hasItem(DEFAULT_TRUCK_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].qtyOnHand").value(hasItem(DEFAULT_QTY_ON_HAND)))
            .andExpect(jsonPath("$.[*].qtyOut").value(hasItem(DEFAULT_QTY_OUT)));
    }
    
    @Test
    @Transactional
    public void getTruckInventory() throws Exception {
        // Initialize the database
        truckInventoryRepository.saveAndFlush(truckInventory);

        // Get the truckInventory
        restTruckInventoryMockMvc.perform(get("/api/truck-inventories/{id}", truckInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(truckInventory.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.truckNumber").value(DEFAULT_TRUCK_NUMBER.toString()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.toString()))
            .andExpect(jsonPath("$.partDescription").value(DEFAULT_PART_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.qtyOnHand").value(DEFAULT_QTY_ON_HAND))
            .andExpect(jsonPath("$.qtyOut").value(DEFAULT_QTY_OUT));
    }

    @Test
    @Transactional
    public void getNonExistingTruckInventory() throws Exception {
        // Get the truckInventory
        restTruckInventoryMockMvc.perform(get("/api/truck-inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTruckInventory() throws Exception {
        // Initialize the database
        truckInventoryRepository.saveAndFlush(truckInventory);

        int databaseSizeBeforeUpdate = truckInventoryRepository.findAll().size();

        // Update the truckInventory
        TruckInventory updatedTruckInventory = truckInventoryRepository.findById(truckInventory.getId()).get();
        // Disconnect from session so that the updates on updatedTruckInventory are not directly saved in db
        em.detach(updatedTruckInventory);
        updatedTruckInventory
            .date(UPDATED_DATE)
            .truckNumber(UPDATED_TRUCK_NUMBER)
            .site(UPDATED_SITE)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .qtyOnHand(UPDATED_QTY_ON_HAND)
            .qtyOut(UPDATED_QTY_OUT);

        restTruckInventoryMockMvc.perform(put("/api/truck-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTruckInventory)))
            .andExpect(status().isOk());

        // Validate the TruckInventory in the database
        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeUpdate);
        TruckInventory testTruckInventory = truckInventoryList.get(truckInventoryList.size() - 1);
        assertThat(testTruckInventory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTruckInventory.getTruckNumber()).isEqualTo(UPDATED_TRUCK_NUMBER);
        assertThat(testTruckInventory.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testTruckInventory.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);
        assertThat(testTruckInventory.getPartDescription()).isEqualTo(UPDATED_PART_DESCRIPTION);
        assertThat(testTruckInventory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTruckInventory.getQtyOnHand()).isEqualTo(UPDATED_QTY_ON_HAND);
        assertThat(testTruckInventory.getQtyOut()).isEqualTo(UPDATED_QTY_OUT);

        // Validate the TruckInventory in Elasticsearch
        verify(mockTruckInventorySearchRepository, times(1)).save(testTruckInventory);
    }

    @Test
    @Transactional
    public void updateNonExistingTruckInventory() throws Exception {
        int databaseSizeBeforeUpdate = truckInventoryRepository.findAll().size();

        // Create the TruckInventory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTruckInventoryMockMvc.perform(put("/api/truck-inventories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(truckInventory)))
            .andExpect(status().isBadRequest());

        // Validate the TruckInventory in the database
        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TruckInventory in Elasticsearch
        verify(mockTruckInventorySearchRepository, times(0)).save(truckInventory);
    }

    @Test
    @Transactional
    public void deleteTruckInventory() throws Exception {
        // Initialize the database
        truckInventoryRepository.saveAndFlush(truckInventory);

        int databaseSizeBeforeDelete = truckInventoryRepository.findAll().size();

        // Delete the truckInventory
        restTruckInventoryMockMvc.perform(delete("/api/truck-inventories/{id}", truckInventory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TruckInventory> truckInventoryList = truckInventoryRepository.findAll();
        assertThat(truckInventoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TruckInventory in Elasticsearch
        verify(mockTruckInventorySearchRepository, times(1)).deleteById(truckInventory.getId());
    }

    @Test
    @Transactional
    public void searchTruckInventory() throws Exception {
        // Initialize the database
        truckInventoryRepository.saveAndFlush(truckInventory);
        when(mockTruckInventorySearchRepository.search(queryStringQuery("id:" + truckInventory.getId())))
            .thenReturn(Collections.singletonList(truckInventory));
        // Search the truckInventory
        restTruckInventoryMockMvc.perform(get("/api/_search/truck-inventories?query=id:" + truckInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(truckInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].truckNumber").value(hasItem(DEFAULT_TRUCK_NUMBER)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].qtyOnHand").value(hasItem(DEFAULT_QTY_ON_HAND)))
            .andExpect(jsonPath("$.[*].qtyOut").value(hasItem(DEFAULT_QTY_OUT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruckInventory.class);
        TruckInventory truckInventory1 = new TruckInventory();
        truckInventory1.setId(1L);
        TruckInventory truckInventory2 = new TruckInventory();
        truckInventory2.setId(truckInventory1.getId());
        assertThat(truckInventory1).isEqualTo(truckInventory2);
        truckInventory2.setId(2L);
        assertThat(truckInventory1).isNotEqualTo(truckInventory2);
        truckInventory1.setId(null);
        assertThat(truckInventory1).isNotEqualTo(truckInventory2);
    }
}
