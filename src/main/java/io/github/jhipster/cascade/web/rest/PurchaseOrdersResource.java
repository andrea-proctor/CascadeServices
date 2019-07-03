package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.PurchaseOrders;
import io.github.jhipster.cascade.repository.PurchaseOrdersRepository;
import io.github.jhipster.cascade.repository.search.PurchaseOrdersSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.PurchaseOrders}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseOrdersResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrdersResource.class);

    private static final String ENTITY_NAME = "purchaseOrders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseOrdersRepository purchaseOrdersRepository;

    private final PurchaseOrdersSearchRepository purchaseOrdersSearchRepository;

    public PurchaseOrdersResource(PurchaseOrdersRepository purchaseOrdersRepository, PurchaseOrdersSearchRepository purchaseOrdersSearchRepository) {
        this.purchaseOrdersRepository = purchaseOrdersRepository;
        this.purchaseOrdersSearchRepository = purchaseOrdersSearchRepository;
    }

    /**
     * {@code POST  /purchase-orders} : Create a new purchaseOrders.
     *
     * @param purchaseOrders the purchaseOrders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseOrders, or with status {@code 400 (Bad Request)} if the purchaseOrders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrders> createPurchaseOrders(@Valid @RequestBody PurchaseOrders purchaseOrders) throws URISyntaxException {
        log.debug("REST request to save PurchaseOrders : {}", purchaseOrders);
        if (purchaseOrders.getId() != null) {
            throw new BadRequestAlertException("A new purchaseOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseOrders result = purchaseOrdersRepository.save(purchaseOrders);
        purchaseOrdersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/purchase-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-orders} : Updates an existing purchaseOrders.
     *
     * @param purchaseOrders the purchaseOrders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseOrders,
     * or with status {@code 400 (Bad Request)} if the purchaseOrders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseOrders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrders> updatePurchaseOrders(@Valid @RequestBody PurchaseOrders purchaseOrders) throws URISyntaxException {
        log.debug("REST request to update PurchaseOrders : {}", purchaseOrders);
        if (purchaseOrders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseOrders result = purchaseOrdersRepository.save(purchaseOrders);
        purchaseOrdersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, purchaseOrders.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /purchase-orders} : get all the purchaseOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseOrders in body.
     */
    @GetMapping("/purchase-orders")
    public List<PurchaseOrders> getAllPurchaseOrders() {
        log.debug("REST request to get all PurchaseOrders");
        return purchaseOrdersRepository.findAll();
    }

    /**
     * {@code GET  /purchase-orders/:id} : get the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseOrders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<PurchaseOrders> getPurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to get PurchaseOrders : {}", id);
        Optional<PurchaseOrders> purchaseOrders = purchaseOrdersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(purchaseOrders);
    }

    /**
     * {@code DELETE  /purchase-orders/:id} : delete the "id" purchaseOrders.
     *
     * @param id the id of the purchaseOrders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-orders/{id}")
    public ResponseEntity<Void> deletePurchaseOrders(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseOrders : {}", id);
        purchaseOrdersRepository.deleteById(id);
        purchaseOrdersSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/purchase-orders?query=:query} : search for the purchaseOrders corresponding
     * to the query.
     *
     * @param query the query of the purchaseOrders search.
     * @return the result of the search.
     */
    @GetMapping("/_search/purchase-orders")
    public List<PurchaseOrders> searchPurchaseOrders(@RequestParam String query) {
        log.debug("REST request to search PurchaseOrders for query {}", query);
        return StreamSupport
            .stream(purchaseOrdersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
