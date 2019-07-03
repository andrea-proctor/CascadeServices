package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.PoolInventory;
import io.github.jhipster.cascade.repository.PoolInventoryRepository;
import io.github.jhipster.cascade.repository.search.PoolInventorySearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.PoolInventory}.
 */
@RestController
@RequestMapping("/api")
public class PoolInventoryResource {

    private final Logger log = LoggerFactory.getLogger(PoolInventoryResource.class);

    private static final String ENTITY_NAME = "poolInventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoolInventoryRepository poolInventoryRepository;

    private final PoolInventorySearchRepository poolInventorySearchRepository;

    public PoolInventoryResource(PoolInventoryRepository poolInventoryRepository, PoolInventorySearchRepository poolInventorySearchRepository) {
        this.poolInventoryRepository = poolInventoryRepository;
        this.poolInventorySearchRepository = poolInventorySearchRepository;
    }

    /**
     * {@code POST  /pool-inventories} : Create a new poolInventory.
     *
     * @param poolInventory the poolInventory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poolInventory, or with status {@code 400 (Bad Request)} if the poolInventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pool-inventories")
    public ResponseEntity<PoolInventory> createPoolInventory(@RequestBody PoolInventory poolInventory) throws URISyntaxException {
        log.debug("REST request to save PoolInventory : {}", poolInventory);
        if (poolInventory.getId() != null) {
            throw new BadRequestAlertException("A new poolInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoolInventory result = poolInventoryRepository.save(poolInventory);
        poolInventorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pool-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pool-inventories} : Updates an existing poolInventory.
     *
     * @param poolInventory the poolInventory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poolInventory,
     * or with status {@code 400 (Bad Request)} if the poolInventory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poolInventory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pool-inventories")
    public ResponseEntity<PoolInventory> updatePoolInventory(@RequestBody PoolInventory poolInventory) throws URISyntaxException {
        log.debug("REST request to update PoolInventory : {}", poolInventory);
        if (poolInventory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoolInventory result = poolInventoryRepository.save(poolInventory);
        poolInventorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poolInventory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pool-inventories} : get all the poolInventories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poolInventories in body.
     */
    @GetMapping("/pool-inventories")
    public List<PoolInventory> getAllPoolInventories() {
        log.debug("REST request to get all PoolInventories");
        return poolInventoryRepository.findAll();
    }

    /**
     * {@code GET  /pool-inventories/:id} : get the "id" poolInventory.
     *
     * @param id the id of the poolInventory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poolInventory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pool-inventories/{id}")
    public ResponseEntity<PoolInventory> getPoolInventory(@PathVariable Long id) {
        log.debug("REST request to get PoolInventory : {}", id);
        Optional<PoolInventory> poolInventory = poolInventoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poolInventory);
    }

    /**
     * {@code DELETE  /pool-inventories/:id} : delete the "id" poolInventory.
     *
     * @param id the id of the poolInventory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pool-inventories/{id}")
    public ResponseEntity<Void> deletePoolInventory(@PathVariable Long id) {
        log.debug("REST request to delete PoolInventory : {}", id);
        poolInventoryRepository.deleteById(id);
        poolInventorySearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pool-inventories?query=:query} : search for the poolInventory corresponding
     * to the query.
     *
     * @param query the query of the poolInventory search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pool-inventories")
    public List<PoolInventory> searchPoolInventories(@RequestParam String query) {
        log.debug("REST request to search PoolInventories for query {}", query);
        return StreamSupport
            .stream(poolInventorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
