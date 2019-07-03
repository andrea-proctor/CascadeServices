package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.ServiceOrders;
import io.github.jhipster.cascade.repository.ServiceOrdersRepository;
import io.github.jhipster.cascade.repository.search.ServiceOrdersSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.ServiceOrders}.
 */
@RestController
@RequestMapping("/api")
public class ServiceOrdersResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOrdersResource.class);

    private static final String ENTITY_NAME = "serviceOrders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOrdersRepository serviceOrdersRepository;

    private final ServiceOrdersSearchRepository serviceOrdersSearchRepository;

    public ServiceOrdersResource(ServiceOrdersRepository serviceOrdersRepository, ServiceOrdersSearchRepository serviceOrdersSearchRepository) {
        this.serviceOrdersRepository = serviceOrdersRepository;
        this.serviceOrdersSearchRepository = serviceOrdersSearchRepository;
    }

    /**
     * {@code POST  /service-orders} : Create a new serviceOrders.
     *
     * @param serviceOrders the serviceOrders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOrders, or with status {@code 400 (Bad Request)} if the serviceOrders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-orders")
    public ResponseEntity<ServiceOrders> createServiceOrders(@Valid @RequestBody ServiceOrders serviceOrders) throws URISyntaxException {
        log.debug("REST request to save ServiceOrders : {}", serviceOrders);
        if (serviceOrders.getId() != null) {
            throw new BadRequestAlertException("A new serviceOrders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOrders result = serviceOrdersRepository.save(serviceOrders);
        serviceOrdersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/service-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-orders} : Updates an existing serviceOrders.
     *
     * @param serviceOrders the serviceOrders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOrders,
     * or with status {@code 400 (Bad Request)} if the serviceOrders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOrders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-orders")
    public ResponseEntity<ServiceOrders> updateServiceOrders(@Valid @RequestBody ServiceOrders serviceOrders) throws URISyntaxException {
        log.debug("REST request to update ServiceOrders : {}", serviceOrders);
        if (serviceOrders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOrders result = serviceOrdersRepository.save(serviceOrders);
        serviceOrdersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceOrders.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-orders} : get all the serviceOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOrders in body.
     */
    @GetMapping("/service-orders")
    public List<ServiceOrders> getAllServiceOrders() {
        log.debug("REST request to get all ServiceOrders");
        return serviceOrdersRepository.findAll();
    }

    /**
     * {@code GET  /service-orders/:id} : get the "id" serviceOrders.
     *
     * @param id the id of the serviceOrders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOrders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-orders/{id}")
    public ResponseEntity<ServiceOrders> getServiceOrders(@PathVariable Long id) {
        log.debug("REST request to get ServiceOrders : {}", id);
        Optional<ServiceOrders> serviceOrders = serviceOrdersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(serviceOrders);
    }

    /**
     * {@code DELETE  /service-orders/:id} : delete the "id" serviceOrders.
     *
     * @param id the id of the serviceOrders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-orders/{id}")
    public ResponseEntity<Void> deleteServiceOrders(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOrders : {}", id);
        serviceOrdersRepository.deleteById(id);
        serviceOrdersSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-orders?query=:query} : search for the serviceOrders corresponding
     * to the query.
     *
     * @param query the query of the serviceOrders search.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-orders")
    public List<ServiceOrders> searchServiceOrders(@RequestParam String query) {
        log.debug("REST request to search ServiceOrders for query {}", query);
        return StreamSupport
            .stream(serviceOrdersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
