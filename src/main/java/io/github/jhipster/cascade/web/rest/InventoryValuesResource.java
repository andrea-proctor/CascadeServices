package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.InventoryValues;
import io.github.jhipster.cascade.repository.InventoryValuesRepository;
import io.github.jhipster.cascade.repository.search.InventoryValuesSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.InventoryValues}.
 */
@RestController
@RequestMapping("/api")
public class InventoryValuesResource {

    private final Logger log = LoggerFactory.getLogger(InventoryValuesResource.class);

    private static final String ENTITY_NAME = "inventoryValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryValuesRepository inventoryValuesRepository;

    private final InventoryValuesSearchRepository inventoryValuesSearchRepository;

    public InventoryValuesResource(InventoryValuesRepository inventoryValuesRepository, InventoryValuesSearchRepository inventoryValuesSearchRepository) {
        this.inventoryValuesRepository = inventoryValuesRepository;
        this.inventoryValuesSearchRepository = inventoryValuesSearchRepository;
    }

    /**
     * {@code POST  /inventory-values} : Create a new inventoryValues.
     *
     * @param inventoryValues the inventoryValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryValues, or with status {@code 400 (Bad Request)} if the inventoryValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-values")
    public ResponseEntity<InventoryValues> createInventoryValues(@RequestBody InventoryValues inventoryValues) throws URISyntaxException {
        log.debug("REST request to save InventoryValues : {}", inventoryValues);
        if (inventoryValues.getId() != null) {
            throw new BadRequestAlertException("A new inventoryValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryValues result = inventoryValuesRepository.save(inventoryValues);
        inventoryValuesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inventory-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-values} : Updates an existing inventoryValues.
     *
     * @param inventoryValues the inventoryValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryValues,
     * or with status {@code 400 (Bad Request)} if the inventoryValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-values")
    public ResponseEntity<InventoryValues> updateInventoryValues(@RequestBody InventoryValues inventoryValues) throws URISyntaxException {
        log.debug("REST request to update InventoryValues : {}", inventoryValues);
        if (inventoryValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InventoryValues result = inventoryValuesRepository.save(inventoryValues);
        inventoryValuesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inventoryValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inventory-values} : get all the inventoryValues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryValues in body.
     */
    @GetMapping("/inventory-values")
    public List<InventoryValues> getAllInventoryValues() {
        log.debug("REST request to get all InventoryValues");
        return inventoryValuesRepository.findAll();
    }

    /**
     * {@code GET  /inventory-values/:id} : get the "id" inventoryValues.
     *
     * @param id the id of the inventoryValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-values/{id}")
    public ResponseEntity<InventoryValues> getInventoryValues(@PathVariable Long id) {
        log.debug("REST request to get InventoryValues : {}", id);
        Optional<InventoryValues> inventoryValues = inventoryValuesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inventoryValues);
    }

    /**
     * {@code DELETE  /inventory-values/:id} : delete the "id" inventoryValues.
     *
     * @param id the id of the inventoryValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-values/{id}")
    public ResponseEntity<Void> deleteInventoryValues(@PathVariable Long id) {
        log.debug("REST request to delete InventoryValues : {}", id);
        inventoryValuesRepository.deleteById(id);
        inventoryValuesSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/inventory-values?query=:query} : search for the inventoryValues corresponding
     * to the query.
     *
     * @param query the query of the inventoryValues search.
     * @return the result of the search.
     */
    @GetMapping("/_search/inventory-values")
    public List<InventoryValues> searchInventoryValues(@RequestParam String query) {
        log.debug("REST request to search InventoryValues for query {}", query);
        return StreamSupport
            .stream(inventoryValuesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
