package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.Locations;
import io.github.jhipster.cascade.repository.LocationsRepository;
import io.github.jhipster.cascade.repository.search.LocationsSearchRepository;
import io.github.jhipster.cascade.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.cascade.domain.Locations}.
 */
@RestController
@RequestMapping("/api")
public class LocationsResource {

    private final Logger log = LoggerFactory.getLogger(LocationsResource.class);

    private static final String ENTITY_NAME = "locations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocationsRepository locationsRepository;

    private final LocationsSearchRepository locationsSearchRepository;

    public LocationsResource(LocationsRepository locationsRepository, LocationsSearchRepository locationsSearchRepository) {
        this.locationsRepository = locationsRepository;
        this.locationsSearchRepository = locationsSearchRepository;
    }

    /**
     * {@code POST  /locations} : Create a new locations.
     *
     * @param locations the locations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locations, or with status {@code 400 (Bad Request)} if the locations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locations")
    public ResponseEntity<Locations> createLocations(@RequestBody Locations locations) throws URISyntaxException {
        log.debug("REST request to save Locations : {}", locations);
        if (locations.getId() != null) {
            throw new BadRequestAlertException("A new locations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locations result = locationsRepository.save(locations);
        locationsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locations} : Updates an existing locations.
     *
     * @param locations the locations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locations,
     * or with status {@code 400 (Bad Request)} if the locations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locations")
    public ResponseEntity<Locations> updateLocations(@RequestBody Locations locations) throws URISyntaxException {
        log.debug("REST request to update Locations : {}", locations);
        if (locations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locations result = locationsRepository.save(locations);
        locationsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locations.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /locations} : get all the locations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locations in body.
     */
    @GetMapping("/locations")
    public List<Locations> getAllLocations() {
        log.debug("REST request to get all Locations");
        return locationsRepository.findAll();
    }

    /**
     * {@code GET  /locations/:id} : get the "id" locations.
     *
     * @param id the id of the locations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locations/{id}")
    public ResponseEntity<Locations> getLocations(@PathVariable Long id) {
        log.debug("REST request to get Locations : {}", id);
        Optional<Locations> locations = locationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locations);
    }

    /**
     * {@code DELETE  /locations/:id} : delete the "id" locations.
     *
     * @param id the id of the locations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Void> deleteLocations(@PathVariable Long id) {
        log.debug("REST request to delete Locations : {}", id);
        locationsRepository.deleteById(id);
        locationsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/locations?query=:query} : search for the locations corresponding
     * to the query.
     *
     * @param query the query of the locations search.
     * @return the result of the search.
     */
    @GetMapping("/_search/locations")
    public List<Locations> searchLocations(@RequestParam String query) {
        log.debug("REST request to search Locations for query {}", query);
        return StreamSupport
            .stream(locationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
