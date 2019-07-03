package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.ServiceOrders;
import io.github.jhipster.cascade.repository.ServiceOrdersRepository;
import io.github.jhipster.cascade.repository.search.ServiceOrdersSearchRepository;
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
 * Integration tests for the {@Link ServiceOrdersResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class ServiceOrdersResourceIT {

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERVISOR = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR = "BBBBBBBBBB";

    private static final String DEFAULT_SO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PART_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REPAIR_COST = "AAAAAAAAAA";
    private static final String UPDATED_REPAIR_COST = "BBBBBBBBBB";

    private static final String DEFAULT_FREIGHT_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_FREIGHT_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private ServiceOrdersRepository serviceOrdersRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.ServiceOrdersSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceOrdersSearchRepository mockServiceOrdersSearchRepository;

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

    private MockMvc restServiceOrdersMockMvc;

    private ServiceOrders serviceOrders;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOrdersResource serviceOrdersResource = new ServiceOrdersResource(serviceOrdersRepository, mockServiceOrdersSearchRepository);
        this.restServiceOrdersMockMvc = MockMvcBuilders.standaloneSetup(serviceOrdersResource)
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
    public static ServiceOrders createEntity(EntityManager em) {
        ServiceOrders serviceOrders = new ServiceOrders()
            .site(DEFAULT_SITE)
            .supervisor(DEFAULT_SUPERVISOR)
            .soNumber(DEFAULT_SO_NUMBER)
            .partNumber(DEFAULT_PART_NUMBER)
            .partDescription(DEFAULT_PART_DESCRIPTION)
            .repairCost(DEFAULT_REPAIR_COST)
            .freightCharge(DEFAULT_FREIGHT_CHARGE)
            .totalCharge(DEFAULT_TOTAL_CHARGE)
            .notes(DEFAULT_NOTES);
        return serviceOrders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOrders createUpdatedEntity(EntityManager em) {
        ServiceOrders serviceOrders = new ServiceOrders()
            .site(UPDATED_SITE)
            .supervisor(UPDATED_SUPERVISOR)
            .soNumber(UPDATED_SO_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .repairCost(UPDATED_REPAIR_COST)
            .freightCharge(UPDATED_FREIGHT_CHARGE)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .notes(UPDATED_NOTES);
        return serviceOrders;
    }

    @BeforeEach
    public void initTest() {
        serviceOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOrders() throws Exception {
        int databaseSizeBeforeCreate = serviceOrdersRepository.findAll().size();

        // Create the ServiceOrders
        restServiceOrdersMockMvc.perform(post("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrders)))
            .andExpect(status().isCreated());

        // Validate the ServiceOrders in the database
        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOrders testServiceOrders = serviceOrdersList.get(serviceOrdersList.size() - 1);
        assertThat(testServiceOrders.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testServiceOrders.getSupervisor()).isEqualTo(DEFAULT_SUPERVISOR);
        assertThat(testServiceOrders.getSoNumber()).isEqualTo(DEFAULT_SO_NUMBER);
        assertThat(testServiceOrders.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);
        assertThat(testServiceOrders.getPartDescription()).isEqualTo(DEFAULT_PART_DESCRIPTION);
        assertThat(testServiceOrders.getRepairCost()).isEqualTo(DEFAULT_REPAIR_COST);
        assertThat(testServiceOrders.getFreightCharge()).isEqualTo(DEFAULT_FREIGHT_CHARGE);
        assertThat(testServiceOrders.getTotalCharge()).isEqualTo(DEFAULT_TOTAL_CHARGE);
        assertThat(testServiceOrders.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the ServiceOrders in Elasticsearch
        verify(mockServiceOrdersSearchRepository, times(1)).save(testServiceOrders);
    }

    @Test
    @Transactional
    public void createServiceOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOrdersRepository.findAll().size();

        // Create the ServiceOrders with an existing ID
        serviceOrders.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOrdersMockMvc.perform(post("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrders)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrders in the database
        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceOrders in Elasticsearch
        verify(mockServiceOrdersSearchRepository, times(0)).save(serviceOrders);
    }


    @Test
    @Transactional
    public void checkSoNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOrdersRepository.findAll().size();
        // set the field null
        serviceOrders.setSoNumber(null);

        // Create the ServiceOrders, which fails.

        restServiceOrdersMockMvc.perform(post("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrders)))
            .andExpect(status().isBadRequest());

        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOrders() throws Exception {
        // Initialize the database
        serviceOrdersRepository.saveAndFlush(serviceOrders);

        // Get all the serviceOrdersList
        restServiceOrdersMockMvc.perform(get("/api/service-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR.toString())))
            .andExpect(jsonPath("$.[*].soNumber").value(hasItem(DEFAULT_SO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].repairCost").value(hasItem(DEFAULT_REPAIR_COST.toString())))
            .andExpect(jsonPath("$.[*].freightCharge").value(hasItem(DEFAULT_FREIGHT_CHARGE.toString())))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceOrders() throws Exception {
        // Initialize the database
        serviceOrdersRepository.saveAndFlush(serviceOrders);

        // Get the serviceOrders
        restServiceOrdersMockMvc.perform(get("/api/service-orders/{id}", serviceOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOrders.getId().intValue()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.supervisor").value(DEFAULT_SUPERVISOR.toString()))
            .andExpect(jsonPath("$.soNumber").value(DEFAULT_SO_NUMBER.toString()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.toString()))
            .andExpect(jsonPath("$.partDescription").value(DEFAULT_PART_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.repairCost").value(DEFAULT_REPAIR_COST.toString()))
            .andExpect(jsonPath("$.freightCharge").value(DEFAULT_FREIGHT_CHARGE.toString()))
            .andExpect(jsonPath("$.totalCharge").value(DEFAULT_TOTAL_CHARGE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceOrders() throws Exception {
        // Get the serviceOrders
        restServiceOrdersMockMvc.perform(get("/api/service-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOrders() throws Exception {
        // Initialize the database
        serviceOrdersRepository.saveAndFlush(serviceOrders);

        int databaseSizeBeforeUpdate = serviceOrdersRepository.findAll().size();

        // Update the serviceOrders
        ServiceOrders updatedServiceOrders = serviceOrdersRepository.findById(serviceOrders.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOrders are not directly saved in db
        em.detach(updatedServiceOrders);
        updatedServiceOrders
            .site(UPDATED_SITE)
            .supervisor(UPDATED_SUPERVISOR)
            .soNumber(UPDATED_SO_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .repairCost(UPDATED_REPAIR_COST)
            .freightCharge(UPDATED_FREIGHT_CHARGE)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .notes(UPDATED_NOTES);

        restServiceOrdersMockMvc.perform(put("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceOrders)))
            .andExpect(status().isOk());

        // Validate the ServiceOrders in the database
        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeUpdate);
        ServiceOrders testServiceOrders = serviceOrdersList.get(serviceOrdersList.size() - 1);
        assertThat(testServiceOrders.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testServiceOrders.getSupervisor()).isEqualTo(UPDATED_SUPERVISOR);
        assertThat(testServiceOrders.getSoNumber()).isEqualTo(UPDATED_SO_NUMBER);
        assertThat(testServiceOrders.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);
        assertThat(testServiceOrders.getPartDescription()).isEqualTo(UPDATED_PART_DESCRIPTION);
        assertThat(testServiceOrders.getRepairCost()).isEqualTo(UPDATED_REPAIR_COST);
        assertThat(testServiceOrders.getFreightCharge()).isEqualTo(UPDATED_FREIGHT_CHARGE);
        assertThat(testServiceOrders.getTotalCharge()).isEqualTo(UPDATED_TOTAL_CHARGE);
        assertThat(testServiceOrders.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the ServiceOrders in Elasticsearch
        verify(mockServiceOrdersSearchRepository, times(1)).save(testServiceOrders);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOrders() throws Exception {
        int databaseSizeBeforeUpdate = serviceOrdersRepository.findAll().size();

        // Create the ServiceOrders

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOrdersMockMvc.perform(put("/api/service-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOrders)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOrders in the database
        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceOrders in Elasticsearch
        verify(mockServiceOrdersSearchRepository, times(0)).save(serviceOrders);
    }

    @Test
    @Transactional
    public void deleteServiceOrders() throws Exception {
        // Initialize the database
        serviceOrdersRepository.saveAndFlush(serviceOrders);

        int databaseSizeBeforeDelete = serviceOrdersRepository.findAll().size();

        // Delete the serviceOrders
        restServiceOrdersMockMvc.perform(delete("/api/service-orders/{id}", serviceOrders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceOrders> serviceOrdersList = serviceOrdersRepository.findAll();
        assertThat(serviceOrdersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceOrders in Elasticsearch
        verify(mockServiceOrdersSearchRepository, times(1)).deleteById(serviceOrders.getId());
    }

    @Test
    @Transactional
    public void searchServiceOrders() throws Exception {
        // Initialize the database
        serviceOrdersRepository.saveAndFlush(serviceOrders);
        when(mockServiceOrdersSearchRepository.search(queryStringQuery("id:" + serviceOrders.getId())))
            .thenReturn(Collections.singletonList(serviceOrders));
        // Search the serviceOrders
        restServiceOrdersMockMvc.perform(get("/api/_search/service-orders?query=id:" + serviceOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR)))
            .andExpect(jsonPath("$.[*].soNumber").value(hasItem(DEFAULT_SO_NUMBER)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].repairCost").value(hasItem(DEFAULT_REPAIR_COST)))
            .andExpect(jsonPath("$.[*].freightCharge").value(hasItem(DEFAULT_FREIGHT_CHARGE)))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOrders.class);
        ServiceOrders serviceOrders1 = new ServiceOrders();
        serviceOrders1.setId(1L);
        ServiceOrders serviceOrders2 = new ServiceOrders();
        serviceOrders2.setId(serviceOrders1.getId());
        assertThat(serviceOrders1).isEqualTo(serviceOrders2);
        serviceOrders2.setId(2L);
        assertThat(serviceOrders1).isNotEqualTo(serviceOrders2);
        serviceOrders1.setId(null);
        assertThat(serviceOrders1).isNotEqualTo(serviceOrders2);
    }
}
