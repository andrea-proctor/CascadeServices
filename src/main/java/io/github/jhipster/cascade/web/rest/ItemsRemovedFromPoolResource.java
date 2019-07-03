package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.ItemsRemovedFromPool;
import io.github.jhipster.cascade.repository.ItemsRemovedFromPoolRepository;
import io.github.jhipster.cascade.repository.search.ItemsRemovedFromPoolSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.ItemsRemovedFromPool}.
 */
@RestController
@RequestMapping("/api")
public class ItemsRemovedFromPoolResource {

    private final Logger log = LoggerFactory.getLogger(ItemsRemovedFromPoolResource.class);

    private static final String ENTITY_NAME = "itemsRemovedFromPool";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemsRemovedFromPoolRepository itemsRemovedFromPoolRepository;

    private final ItemsRemovedFromPoolSearchRepository itemsRemovedFromPoolSearchRepository;

    public ItemsRemovedFromPoolResource(ItemsRemovedFromPoolRepository itemsRemovedFromPoolRepository, ItemsRemovedFromPoolSearchRepository itemsRemovedFromPoolSearchRepository) {
        this.itemsRemovedFromPoolRepository = itemsRemovedFromPoolRepository;
        this.itemsRemovedFromPoolSearchRepository = itemsRemovedFromPoolSearchRepository;
    }

    /**
     * {@code POST  /items-removed-from-pools} : Create a new itemsRemovedFromPool.
     *
     * @param itemsRemovedFromPool the itemsRemovedFromPool to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemsRemovedFromPool, or with status {@code 400 (Bad Request)} if the itemsRemovedFromPool has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/items-removed-from-pools")
    public ResponseEntity<ItemsRemovedFromPool> createItemsRemovedFromPool(@RequestBody ItemsRemovedFromPool itemsRemovedFromPool) throws URISyntaxException {
        log.debug("REST request to save ItemsRemovedFromPool : {}", itemsRemovedFromPool);
        if (itemsRemovedFromPool.getId() != null) {
            throw new BadRequestAlertException("A new itemsRemovedFromPool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemsRemovedFromPool result = itemsRemovedFromPoolRepository.save(itemsRemovedFromPool);
        itemsRemovedFromPoolSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/items-removed-from-pools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /items-removed-from-pools} : Updates an existing itemsRemovedFromPool.
     *
     * @param itemsRemovedFromPool the itemsRemovedFromPool to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemsRemovedFromPool,
     * or with status {@code 400 (Bad Request)} if the itemsRemovedFromPool is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemsRemovedFromPool couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/items-removed-from-pools")
    public ResponseEntity<ItemsRemovedFromPool> updateItemsRemovedFromPool(@RequestBody ItemsRemovedFromPool itemsRemovedFromPool) throws URISyntaxException {
        log.debug("REST request to update ItemsRemovedFromPool : {}", itemsRemovedFromPool);
        if (itemsRemovedFromPool.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemsRemovedFromPool result = itemsRemovedFromPoolRepository.save(itemsRemovedFromPool);
        itemsRemovedFromPoolSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemsRemovedFromPool.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /items-removed-from-pools} : get all the itemsRemovedFromPools.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemsRemovedFromPools in body.
     */
    @GetMapping("/items-removed-from-pools")
    public List<ItemsRemovedFromPool> getAllItemsRemovedFromPools() {
        log.debug("REST request to get all ItemsRemovedFromPools");
        return itemsRemovedFromPoolRepository.findAll();
    }

    /**
     * {@code GET  /items-removed-from-pools/:id} : get the "id" itemsRemovedFromPool.
     *
     * @param id the id of the itemsRemovedFromPool to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemsRemovedFromPool, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/items-removed-from-pools/{id}")
    public ResponseEntity<ItemsRemovedFromPool> getItemsRemovedFromPool(@PathVariable Long id) {
        log.debug("REST request to get ItemsRemovedFromPool : {}", id);
        Optional<ItemsRemovedFromPool> itemsRemovedFromPool = itemsRemovedFromPoolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemsRemovedFromPool);
    }

    /**
     * {@code DELETE  /items-removed-from-pools/:id} : delete the "id" itemsRemovedFromPool.
     *
     * @param id the id of the itemsRemovedFromPool to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/items-removed-from-pools/{id}")
    public ResponseEntity<Void> deleteItemsRemovedFromPool(@PathVariable Long id) {
        log.debug("REST request to delete ItemsRemovedFromPool : {}", id);
        itemsRemovedFromPoolRepository.deleteById(id);
        itemsRemovedFromPoolSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/items-removed-from-pools?query=:query} : search for the itemsRemovedFromPool corresponding
     * to the query.
     *
     * @param query the query of the itemsRemovedFromPool search.
     * @return the result of the search.
     */
    @GetMapping("/_search/items-removed-from-pools")
    public List<ItemsRemovedFromPool> searchItemsRemovedFromPools(@RequestParam String query) {
        log.debug("REST request to search ItemsRemovedFromPools for query {}", query);
        return StreamSupport
            .stream(itemsRemovedFromPoolSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
