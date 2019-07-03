package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.TruckInventory;
import io.github.jhipster.cascade.repository.TruckInventoryRepository;
import io.github.jhipster.cascade.repository.search.TruckInventorySearchRepository;
import io.github.jhipster.cascade.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.jhipster.cascade.domain.TruckInventory}.
 */
@RestController
@RequestMapping("/api")
public class TruckInventoryResource {

    private final Logger log = LoggerFactory.getLogger(TruckInventoryResource.class);

    private static final String ENTITY_NAME = "truckInventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TruckInventoryRepository truckInventoryRepository;

    private final TruckInventorySearchRepository truckInventorySearchRepository;

    public TruckInventoryResource(TruckInventoryRepository truckInventoryRepository, TruckInventorySearchRepository truckInventorySearchRepository) {
        this.truckInventoryRepository = truckInventoryRepository;
        this.truckInventorySearchRepository = truckInventorySearchRepository;
    }

    /**
     * {@code POST  /truck-inventories} : Create a new truckInventory.
     *
     * @param truckInventory the truckInventory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new truckInventory, or with status {@code 400 (Bad Request)} if the truckInventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/truck-inventories")
    public ResponseEntity<TruckInventory> createTruckInventory(@Valid @RequestBody TruckInventory truckInventory) throws URISyntaxException {
        log.debug("REST request to save TruckInventory : {}", truckInventory);
        if (truckInventory.getId() != null) {
            throw new BadRequestAlertException("A new truckInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TruckInventory result = truckInventoryRepository.save(truckInventory);
        truckInventorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/truck-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /truck-inventories} : Updates an existing truckInventory.
     *
     * @param truckInventory the truckInventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated truckInventory,
     * or with status {@code 400 (Bad Request)} if the truckInventory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the truckInventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/truck-inventories")
    public ResponseEntity<TruckInventory> updateTruckInventory(@Valid @RequestBody TruckInventory truckInventory) throws URISyntaxException {
        log.debug("REST request to update TruckInventory : {}", truckInventory);
        if (truckInventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TruckInventory result = truckInventoryRepository.save(truckInventory);
        truckInventorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, truckInventory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /truck-inventories} : get all the truckInventories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of truckInventories in body.
     */
    @GetMapping("/truck-inventories")
    public List<TruckInventory> getAllTruckInventories() {
        log.debug("REST request to get all TruckInventories");
        return truckInventoryRepository.findAll();
    }

    /**
     * {@code GET  /truck-inventories/:id} : get the "id" truckInventory.
     *
     * @param id the id of the truckInventory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the truckInventory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/truck-inventories/{id}")
    public ResponseEntity<TruckInventory> getTruckInventory(@PathVariable Long id) {
        log.debug("REST request to get TruckInventory : {}", id);
        Optional<TruckInventory> truckInventory = truckInventoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(truckInventory);
    }

    /**
     * {@code DELETE  /truck-inventories/:id} : delete the "id" truckInventory.
     *
     * @param id the id of the truckInventory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/truck-inventories/{id}")
    public ResponseEntity<Void> deleteTruckInventory(@PathVariable Long id) {
        log.debug("REST request to delete TruckInventory : {}", id);
        truckInventoryRepository.deleteById(id);
        truckInventorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/truck-inventories?query=:query} : search for the truckInventory corresponding
     * to the query.
     *
     * @param query the query of the truckInventory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/truck-inventories")
    public List<TruckInventory> searchTruckInventories(@RequestParam String query) {
        log.debug("REST request to search TruckInventories for query {}", query);
        return StreamSupport
            .stream(truckInventorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
