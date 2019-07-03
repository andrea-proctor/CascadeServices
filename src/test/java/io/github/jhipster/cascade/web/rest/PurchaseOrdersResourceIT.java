package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.PurchaseOrders;
import io.github.jhipster.cascade.repository.PurchaseOrdersRepository;
import io.github.jhipster.cascade.repository.search.PurchaseOrdersSearchRepository;
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
 * Integration tests for the {@Link PurchaseOrdersResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class PurchaseOrdersResourceIT {

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_SUPERVISOR = "AAAAAAAAAA";
    private static final String UPDATED_SUPERVISOR = "BBBBBBBBBB";

    private static final String DEFAULT_PO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PART_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PART_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PART_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY_REPLACED = 1;
    private static final Integer UPDATED_QTY_REPLACED = 2;

    private static final String DEFAULT_REPLACEMENT_COST_EACH = "AAAAAAAAAA";
    private static final String UPDATED_REPLACEMENT_COST_EACH = "BBBBBBBBBB";

    private static final String DEFAULT_FREIGHT_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_FREIGHT_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private PurchaseOrdersRepository purchaseOrdersRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.PurchaseOrdersSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseOrdersSearchRepository mockPurchaseOrdersSearchRepository;

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

    private MockMvc restPurchaseOrdersMockMvc;

    private PurchaseOrders purchaseOrders;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrdersResource purchaseOrdersResource = new PurchaseOrdersResource(purchaseOrdersRepository, mockPurchaseOrdersSearchRepository);
        this.restPurchaseOrdersMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrdersResource)
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
    public static PurchaseOrders createEntity(EntityManager em) {
        PurchaseOrders purchaseOrders = new PurchaseOrders()
            .site(DEFAULT_SITE)
            .supervisor(DEFAULT_SUPERVISOR)
            .poNumber(DEFAULT_PO_NUMBER)
            .partNumber(DEFAULT_PART_NUMBER)
            .partDescription(DEFAULT_PART_DESCRIPTION)
            .qtyReplaced(DEFAULT_QTY_REPLACED)
            .replacementCostEach(DEFAULT_REPLACEMENT_COST_EACH)
            .freightCharge(DEFAULT_FREIGHT_CHARGE)
            .totalCharge(DEFAULT_TOTAL_CHARGE)
            .notes(DEFAULT_NOTES);
        return purchaseOrders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrders createUpdatedEntity(EntityManager em) {
        PurchaseOrders purchaseOrders = new PurchaseOrders()
            .site(UPDATED_SITE)
            .supervisor(UPDATED_SUPERVISOR)
            .poNumber(UPDATED_PO_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .qtyReplaced(UPDATED_QTY_REPLACED)
            .replacementCostEach(UPDATED_REPLACEMENT_COST_EACH)
            .freightCharge(UPDATED_FREIGHT_CHARGE)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .notes(UPDATED_NOTES);
        return purchaseOrders;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrders() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrders)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testPurchaseOrders.getSupervisor()).isEqualTo(DEFAULT_SUPERVISOR);
        assertThat(testPurchaseOrders.getPoNumber()).isEqualTo(DEFAULT_PO_NUMBER);
        assertThat(testPurchaseOrders.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);
        assertThat(testPurchaseOrders.getPartDescription()).isEqualTo(DEFAULT_PART_DESCRIPTION);
        assertThat(testPurchaseOrders.getQtyReplaced()).isEqualTo(DEFAULT_QTY_REPLACED);
        assertThat(testPurchaseOrders.getReplacementCostEach()).isEqualTo(DEFAULT_REPLACEMENT_COST_EACH);
        assertThat(testPurchaseOrders.getFreightCharge()).isEqualTo(DEFAULT_FREIGHT_CHARGE);
        assertThat(testPurchaseOrders.getTotalCharge()).isEqualTo(DEFAULT_TOTAL_CHARGE);
        assertThat(testPurchaseOrders.getNotes()).isEqualTo(DEFAULT_NOTES);

        // Validate the PurchaseOrders in Elasticsearch
        verify(mockPurchaseOrdersSearchRepository, times(1)).save(testPurchaseOrders);
    }

    @Test
    @Transactional
    public void createPurchaseOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders with an existing ID
        purchaseOrders.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrders)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseOrders in Elasticsearch
        verify(mockPurchaseOrdersSearchRepository, times(0)).save(purchaseOrders);
    }


    @Test
    @Transactional
    public void checkPoNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrdersRepository.findAll().size();
        // set the field null
        purchaseOrders.setPoNumber(null);

        // Create the PurchaseOrders, which fails.

        restPurchaseOrdersMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrders)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get all the purchaseOrdersList
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR.toString())))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].qtyReplaced").value(hasItem(DEFAULT_QTY_REPLACED)))
            .andExpect(jsonPath("$.[*].replacementCostEach").value(hasItem(DEFAULT_REPLACEMENT_COST_EACH.toString())))
            .andExpect(jsonPath("$.[*].freightCharge").value(hasItem(DEFAULT_FREIGHT_CHARGE.toString())))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrders.getId().intValue()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.supervisor").value(DEFAULT_SUPERVISOR.toString()))
            .andExpect(jsonPath("$.poNumber").value(DEFAULT_PO_NUMBER.toString()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.toString()))
            .andExpect(jsonPath("$.partDescription").value(DEFAULT_PART_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.qtyReplaced").value(DEFAULT_QTY_REPLACED))
            .andExpect(jsonPath("$.replacementCostEach").value(DEFAULT_REPLACEMENT_COST_EACH.toString()))
            .andExpect(jsonPath("$.freightCharge").value(DEFAULT_FREIGHT_CHARGE.toString()))
            .andExpect(jsonPath("$.totalCharge").value(DEFAULT_TOTAL_CHARGE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrders() throws Exception {
        // Get the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Update the purchaseOrders
        PurchaseOrders updatedPurchaseOrders = purchaseOrdersRepository.findById(purchaseOrders.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrders are not directly saved in db
        em.detach(updatedPurchaseOrders);
        updatedPurchaseOrders
            .site(UPDATED_SITE)
            .supervisor(UPDATED_SUPERVISOR)
            .poNumber(UPDATED_PO_NUMBER)
            .partNumber(UPDATED_PART_NUMBER)
            .partDescription(UPDATED_PART_DESCRIPTION)
            .qtyReplaced(UPDATED_QTY_REPLACED)
            .replacementCostEach(UPDATED_REPLACEMENT_COST_EACH)
            .freightCharge(UPDATED_FREIGHT_CHARGE)
            .totalCharge(UPDATED_TOTAL_CHARGE)
            .notes(UPDATED_NOTES);

        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseOrders)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrders testPurchaseOrders = purchaseOrdersList.get(purchaseOrdersList.size() - 1);
        assertThat(testPurchaseOrders.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testPurchaseOrders.getSupervisor()).isEqualTo(UPDATED_SUPERVISOR);
        assertThat(testPurchaseOrders.getPoNumber()).isEqualTo(UPDATED_PO_NUMBER);
        assertThat(testPurchaseOrders.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);
        assertThat(testPurchaseOrders.getPartDescription()).isEqualTo(UPDATED_PART_DESCRIPTION);
        assertThat(testPurchaseOrders.getQtyReplaced()).isEqualTo(UPDATED_QTY_REPLACED);
        assertThat(testPurchaseOrders.getReplacementCostEach()).isEqualTo(UPDATED_REPLACEMENT_COST_EACH);
        assertThat(testPurchaseOrders.getFreightCharge()).isEqualTo(UPDATED_FREIGHT_CHARGE);
        assertThat(testPurchaseOrders.getTotalCharge()).isEqualTo(UPDATED_TOTAL_CHARGE);
        assertThat(testPurchaseOrders.getNotes()).isEqualTo(UPDATED_NOTES);

        // Validate the PurchaseOrders in Elasticsearch
        verify(mockPurchaseOrdersSearchRepository, times(1)).save(testPurchaseOrders);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrders() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrdersRepository.findAll().size();

        // Create the PurchaseOrders

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrdersMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrders)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrders in the database
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseOrders in Elasticsearch
        verify(mockPurchaseOrdersSearchRepository, times(0)).save(purchaseOrders);
    }

    @Test
    @Transactional
    public void deletePurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);

        int databaseSizeBeforeDelete = purchaseOrdersRepository.findAll().size();

        // Delete the purchaseOrders
        restPurchaseOrdersMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrders> purchaseOrdersList = purchaseOrdersRepository.findAll();
        assertThat(purchaseOrdersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseOrders in Elasticsearch
        verify(mockPurchaseOrdersSearchRepository, times(1)).deleteById(purchaseOrders.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrdersRepository.saveAndFlush(purchaseOrders);
        when(mockPurchaseOrdersSearchRepository.search(queryStringQuery("id:" + purchaseOrders.getId())))
            .thenReturn(Collections.singletonList(purchaseOrders));
        // Search the purchaseOrders
        restPurchaseOrdersMockMvc.perform(get("/api/_search/purchase-orders?query=id:" + purchaseOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrders.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].supervisor").value(hasItem(DEFAULT_SUPERVISOR)))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER)))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER)))
            .andExpect(jsonPath("$.[*].partDescription").value(hasItem(DEFAULT_PART_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].qtyReplaced").value(hasItem(DEFAULT_QTY_REPLACED)))
            .andExpect(jsonPath("$.[*].replacementCostEach").value(hasItem(DEFAULT_REPLACEMENT_COST_EACH)))
            .andExpect(jsonPath("$.[*].freightCharge").value(hasItem(DEFAULT_FREIGHT_CHARGE)))
            .andExpect(jsonPath("$.[*].totalCharge").value(hasItem(DEFAULT_TOTAL_CHARGE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrders.class);
        PurchaseOrders purchaseOrders1 = new PurchaseOrders();
        purchaseOrders1.setId(1L);
        PurchaseOrders purchaseOrders2 = new PurchaseOrders();
        purchaseOrders2.setId(purchaseOrders1.getId());
        assertThat(purchaseOrders1).isEqualTo(purchaseOrders2);
        purchaseOrders2.setId(2L);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
        purchaseOrders1.setId(null);
        assertThat(purchaseOrders1).isNotEqualTo(purchaseOrders2);
    }
}
