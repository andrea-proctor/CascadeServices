package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.ItemsRemovedFromPool;
import io.github.jhipster.cascade.repository.ItemsRemovedFromPoolRepository;
import io.github.jhipster.cascade.repository.search.ItemsRemovedFromPoolSearchRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@Link ItemsRemovedFromPoolResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class ItemsRemovedFromPoolResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALARY = 1L;
    private static final Long UPDATED_SALARY = 2L;

    private static final Long DEFAULT_COMMISSION_PCT = 1L;
    private static final Long UPDATED_COMMISSION_PCT = 2L;

    @Autowired
    private ItemsRemovedFromPoolRepository itemsRemovedFromPoolRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.ItemsRemovedFromPoolSearchRepositoryMockConfiguration
     */
    @Autowired
    private ItemsRemovedFromPoolSearchRepository mockItemsRemovedFromPoolSearchRepository;

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

    private MockMvc restItemsRemovedFromPoolMockMvc;

    private ItemsRemovedFromPool itemsRemovedFromPool;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemsRemovedFromPoolResource itemsRemovedFromPoolResource = new ItemsRemovedFromPoolResource(itemsRemovedFromPoolRepository, mockItemsRemovedFromPoolSearchRepository);
        this.restItemsRemovedFromPoolMockMvc = MockMvcBuilders.standaloneSetup(itemsRemovedFromPoolResource)
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
    public static ItemsRemovedFromPool createEntity(EntityManager em) {
        ItemsRemovedFromPool itemsRemovedFromPool = new ItemsRemovedFromPool()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .salary(DEFAULT_SALARY)
            .commissionPct(DEFAULT_COMMISSION_PCT);
        return itemsRemovedFromPool;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemsRemovedFromPool createUpdatedEntity(EntityManager em) {
        ItemsRemovedFromPool itemsRemovedFromPool = new ItemsRemovedFromPool()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT);
        return itemsRemovedFromPool;
    }

    @BeforeEach
    public void initTest() {
        itemsRemovedFromPool = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemsRemovedFromPool() throws Exception {
        int databaseSizeBeforeCreate = itemsRemovedFromPoolRepository.findAll().size();

        // Create the ItemsRemovedFromPool
        restItemsRemovedFromPoolMockMvc.perform(post("/api/items-removed-from-pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsRemovedFromPool)))
            .andExpect(status().isCreated());

        // Validate the ItemsRemovedFromPool in the database
        List<ItemsRemovedFromPool> itemsRemovedFromPoolList = itemsRemovedFromPoolRepository.findAll();
        assertThat(itemsRemovedFromPoolList).hasSize(databaseSizeBeforeCreate + 1);
        ItemsRemovedFromPool testItemsRemovedFromPool = itemsRemovedFromPoolList.get(itemsRemovedFromPoolList.size() - 1);
        assertThat(testItemsRemovedFromPool.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testItemsRemovedFromPool.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testItemsRemovedFromPool.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testItemsRemovedFromPool.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testItemsRemovedFromPool.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testItemsRemovedFromPool.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testItemsRemovedFromPool.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);

        // Validate the ItemsRemovedFromPool in Elasticsearch
        verify(mockItemsRemovedFromPoolSearchRepository, times(1)).save(testItemsRemovedFromPool);
    }

    @Test
    @Transactional
    public void createItemsRemovedFromPoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemsRemovedFromPoolRepository.findAll().size();

        // Create the ItemsRemovedFromPool with an existing ID
        itemsRemovedFromPool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemsRemovedFromPoolMockMvc.perform(post("/api/items-removed-from-pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsRemovedFromPool)))
            .andExpect(status().isBadRequest());

        // Validate the ItemsRemovedFromPool in the database
        List<ItemsRemovedFromPool> itemsRemovedFromPoolList = itemsRemovedFromPoolRepository.findAll();
        assertThat(itemsRemovedFromPoolList).hasSize(databaseSizeBeforeCreate);

        // Validate the ItemsRemovedFromPool in Elasticsearch
        verify(mockItemsRemovedFromPoolSearchRepository, times(0)).save(itemsRemovedFromPool);
    }


    @Test
    @Transactional
    public void getAllItemsRemovedFromPools() throws Exception {
        // Initialize the database
        itemsRemovedFromPoolRepository.saveAndFlush(itemsRemovedFromPool);

        // Get all the itemsRemovedFromPoolList
        restItemsRemovedFromPoolMockMvc.perform(get("/api/items-removed-from-pools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemsRemovedFromPool.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.intValue())));
    }
    
    @Test
    @Transactional
    public void getItemsRemovedFromPool() throws Exception {
        // Initialize the database
        itemsRemovedFromPoolRepository.saveAndFlush(itemsRemovedFromPool);

        // Get the itemsRemovedFromPool
        restItemsRemovedFromPoolMockMvc.perform(get("/api/items-removed-from-pools/{id}", itemsRemovedFromPool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemsRemovedFromPool.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.commissionPct").value(DEFAULT_COMMISSION_PCT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemsRemovedFromPool() throws Exception {
        // Get the itemsRemovedFromPool
        restItemsRemovedFromPoolMockMvc.perform(get("/api/items-removed-from-pools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemsRemovedFromPool() throws Exception {
        // Initialize the database
        itemsRemovedFromPoolRepository.saveAndFlush(itemsRemovedFromPool);

        int databaseSizeBeforeUpdate = itemsRemovedFromPoolRepository.findAll().size();

        // Update the itemsRemovedFromPool
        ItemsRemovedFromPool updatedItemsRemovedFromPool = itemsRemovedFromPoolRepository.findById(itemsRemovedFromPool.getId()).get();
        // Disconnect from session so that the updates on updatedItemsRemovedFromPool are not directly saved in db
        em.detach(updatedItemsRemovedFromPool);
        updatedItemsRemovedFromPool
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT);

        restItemsRemovedFromPoolMockMvc.perform(put("/api/items-removed-from-pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemsRemovedFromPool)))
            .andExpect(status().isOk());

        // Validate the ItemsRemovedFromPool in the database
        List<ItemsRemovedFromPool> itemsRemovedFromPoolList = itemsRemovedFromPoolRepository.findAll();
        assertThat(itemsRemovedFromPoolList).hasSize(databaseSizeBeforeUpdate);
        ItemsRemovedFromPool testItemsRemovedFromPool = itemsRemovedFromPoolList.get(itemsRemovedFromPoolList.size() - 1);
        assertThat(testItemsRemovedFromPool.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testItemsRemovedFromPool.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testItemsRemovedFromPool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testItemsRemovedFromPool.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testItemsRemovedFromPool.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testItemsRemovedFromPool.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testItemsRemovedFromPool.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);

        // Validate the ItemsRemovedFromPool in Elasticsearch
        verify(mockItemsRemovedFromPoolSearchRepository, times(1)).save(testItemsRemovedFromPool);
    }

    @Test
    @Transactional
    public void updateNonExistingItemsRemovedFromPool() throws Exception {
        int databaseSizeBeforeUpdate = itemsRemovedFromPoolRepository.findAll().size();

        // Create the ItemsRemovedFromPool

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemsRemovedFromPoolMockMvc.perform(put("/api/items-removed-from-pools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsRemovedFromPool)))
            .andExpect(status().isBadRequest());

        // Validate the ItemsRemovedFromPool in the database
        List<ItemsRemovedFromPool> itemsRemovedFromPoolList = itemsRemovedFromPoolRepository.findAll();
        assertThat(itemsRemovedFromPoolList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ItemsRemovedFromPool in Elasticsearch
        verify(mockItemsRemovedFromPoolSearchRepository, times(0)).save(itemsRemovedFromPool);
    }

    @Test
    @Transactional
    public void deleteItemsRemovedFromPool() throws Exception {
        // Initialize the database
        itemsRemovedFromPoolRepository.saveAndFlush(itemsRemovedFromPool);

        int databaseSizeBeforeDelete = itemsRemovedFromPoolRepository.findAll().size();

        // Delete the itemsRemovedFromPool
        restItemsRemovedFromPoolMockMvc.perform(delete("/api/items-removed-from-pools/{id}", itemsRemovedFromPool.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemsRemovedFromPool> itemsRemovedFromPoolList = itemsRemovedFromPoolRepository.findAll();
        assertThat(itemsRemovedFromPoolList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ItemsRemovedFromPool in Elasticsearch
        verify(mockItemsRemovedFromPoolSearchRepository, times(1)).deleteById(itemsRemovedFromPool.getId());
    }

    @Test
    @Transactional
    public void searchItemsRemovedFromPool() throws Exception {
        // Initialize the database
        itemsRemovedFromPoolRepository.saveAndFlush(itemsRemovedFromPool);
        when(mockItemsRemovedFromPoolSearchRepository.search(queryStringQuery("id:" + itemsRemovedFromPool.getId())))
            .thenReturn(Collections.singletonList(itemsRemovedFromPool));
        // Search the itemsRemovedFromPool
        restItemsRemovedFromPoolMockMvc.perform(get("/api/_search/items-removed-from-pools?query=id:" + itemsRemovedFromPool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemsRemovedFromPool.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemsRemovedFromPool.class);
        ItemsRemovedFromPool itemsRemovedFromPool1 = new ItemsRemovedFromPool();
        itemsRemovedFromPool1.setId(1L);
        ItemsRemovedFromPool itemsRemovedFromPool2 = new ItemsRemovedFromPool();
        itemsRemovedFromPool2.setId(itemsRemovedFromPool1.getId());
        assertThat(itemsRemovedFromPool1).isEqualTo(itemsRemovedFromPool2);
        itemsRemovedFromPool2.setId(2L);
        assertThat(itemsRemovedFromPool1).isNotEqualTo(itemsRemovedFromPool2);
        itemsRemovedFromPool1.setId(null);
        assertThat(itemsRemovedFromPool1).isNotEqualTo(itemsRemovedFromPool2);
    }
}
