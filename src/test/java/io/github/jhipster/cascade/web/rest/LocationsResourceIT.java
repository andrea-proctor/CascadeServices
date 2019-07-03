package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.CascadeServicesApp;
import io.github.jhipster.cascade.domain.Locations;
import io.github.jhipster.cascade.repository.LocationsRepository;
import io.github.jhipster.cascade.repository.search.LocationsSearchRepository;
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
 * Integration tests for the {@Link LocationsResource} REST controller.
 */
@SpringBootTest(classes = CascadeServicesApp.class)
public class LocationsResourceIT {

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private LocationsRepository locationsRepository;

    /**
     * This repository is mocked in the io.github.jhipster.cascade.repository.search test package.
     *
     * @see io.github.jhipster.cascade.repository.search.LocationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocationsSearchRepository mockLocationsSearchRepository;

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

    private MockMvc restLocationsMockMvc;

    private Locations locations;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationsResource locationsResource = new LocationsResource(locationsRepository, mockLocationsSearchRepository);
        this.restLocationsMockMvc = MockMvcBuilders.standaloneSetup(locationsResource)
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
    public static Locations createEntity(EntityManager em) {
        Locations locations = new Locations()
            .site(DEFAULT_SITE)
            .street(DEFAULT_STREET)
            .zipCode(DEFAULT_ZIP_CODE)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE);
        return locations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locations createUpdatedEntity(EntityManager em) {
        Locations locations = new Locations()
            .site(UPDATED_SITE)
            .street(UPDATED_STREET)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE);
        return locations;
    }

    @BeforeEach
    public void initTest() {
        locations = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocations() throws Exception {
        int databaseSizeBeforeCreate = locationsRepository.findAll().size();

        // Create the Locations
        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locations)))
            .andExpect(status().isCreated());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate + 1);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testLocations.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testLocations.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testLocations.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLocations.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the Locations in Elasticsearch
        verify(mockLocationsSearchRepository, times(1)).save(testLocations);
    }

    @Test
    @Transactional
    public void createLocationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationsRepository.findAll().size();

        // Create the Locations with an existing ID
        locations.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationsMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locations)))
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Locations in Elasticsearch
        verify(mockLocationsSearchRepository, times(0)).save(locations);
    }


    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get all the locationsList
        restLocationsMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locations.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        // Get the locations
        restLocationsMockMvc.perform(get("/api/locations/{id}", locations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locations.getId().intValue()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocations() throws Exception {
        // Get the locations
        restLocationsMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Update the locations
        Locations updatedLocations = locationsRepository.findById(locations.getId()).get();
        // Disconnect from session so that the updates on updatedLocations are not directly saved in db
        em.detach(updatedLocations);
        updatedLocations
            .site(UPDATED_SITE)
            .street(UPDATED_STREET)
            .zipCode(UPDATED_ZIP_CODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE);

        restLocationsMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocations)))
            .andExpect(status().isOk());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);
        Locations testLocations = locationsList.get(locationsList.size() - 1);
        assertThat(testLocations.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testLocations.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testLocations.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testLocations.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLocations.getState()).isEqualTo(UPDATED_STATE);

        // Validate the Locations in Elasticsearch
        verify(mockLocationsSearchRepository, times(1)).save(testLocations);
    }

    @Test
    @Transactional
    public void updateNonExistingLocations() throws Exception {
        int databaseSizeBeforeUpdate = locationsRepository.findAll().size();

        // Create the Locations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationsMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locations)))
            .andExpect(status().isBadRequest());

        // Validate the Locations in the database
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Locations in Elasticsearch
        verify(mockLocationsSearchRepository, times(0)).save(locations);
    }

    @Test
    @Transactional
    public void deleteLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);

        int databaseSizeBeforeDelete = locationsRepository.findAll().size();

        // Delete the locations
        restLocationsMockMvc.perform(delete("/api/locations/{id}", locations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locations> locationsList = locationsRepository.findAll();
        assertThat(locationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Locations in Elasticsearch
        verify(mockLocationsSearchRepository, times(1)).deleteById(locations.getId());
    }

    @Test
    @Transactional
    public void searchLocations() throws Exception {
        // Initialize the database
        locationsRepository.saveAndFlush(locations);
        when(mockLocationsSearchRepository.search(queryStringQuery("id:" + locations.getId())))
            .thenReturn(Collections.singletonList(locations));
        // Search the locations
        restLocationsMockMvc.perform(get("/api/_search/locations?query=id:" + locations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locations.getId().intValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locations.class);
        Locations locations1 = new Locations();
        locations1.setId(1L);
        Locations locations2 = new Locations();
        locations2.setId(locations1.getId());
        assertThat(locations1).isEqualTo(locations2);
        locations2.setId(2L);
        assertThat(locations1).isNotEqualTo(locations2);
        locations1.setId(null);
        assertThat(locations1).isNotEqualTo(locations2);
    }
}
