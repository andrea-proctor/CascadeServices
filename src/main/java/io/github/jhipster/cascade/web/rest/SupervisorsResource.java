package io.github.jhipster.cascade.web.rest;

import io.github.jhipster.cascade.domain.Supervisors;
import io.github.jhipster.cascade.repository.SupervisorsRepository;
import io.github.jhipster.cascade.repository.search.SupervisorsSearchRepository;
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
 * REST controller for managing {@link io.github.jhipster.cascade.domain.Supervisors}.
 */
@RestController
@RequestMapping("/api")
public class SupervisorsResource {

    private final Logger log = LoggerFactory.getLogger(SupervisorsResource.class);

    private static final String ENTITY_NAME = "supervisors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupervisorsRepository supervisorsRepository;

    private final SupervisorsSearchRepository supervisorsSearchRepository;

    public SupervisorsResource(SupervisorsRepository supervisorsRepository, SupervisorsSearchRepository supervisorsSearchRepository) {
        this.supervisorsRepository = supervisorsRepository;
        this.supervisorsSearchRepository = supervisorsSearchRepository;
    }

    /**
     * {@code POST  /supervisors} : Create a new supervisors.
     *
     * @param supervisors the supervisors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supervisors, or with status {@code 400 (Bad Request)} if the supervisors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supervisors")
    public ResponseEntity<Supervisors> createSupervisors(@RequestBody Supervisors supervisors) throws URISyntaxException {
        log.debug("REST request to save Supervisors : {}", supervisors);
        if (supervisors.getId() != null) {
            throw new BadRequestAlertException("A new supervisors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Supervisors result = supervisorsRepository.save(supervisors);
        supervisorsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/supervisors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supervisors} : Updates an existing supervisors.
     *
     * @param supervisors the supervisors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supervisors,
     * or with status {@code 400 (Bad Request)} if the supervisors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supervisors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supervisors")
    public ResponseEntity<Supervisors> updateSupervisors(@RequestBody Supervisors supervisors) throws URISyntaxException {
        log.debug("REST request to update Supervisors : {}", supervisors);
        if (supervisors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Supervisors result = supervisorsRepository.save(supervisors);
        supervisorsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supervisors.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supervisors} : get all the supervisors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supervisors in body.
     */
    @GetMapping("/supervisors")
    public List<Supervisors> getAllSupervisors() {
        log.debug("REST request to get all Supervisors");
        return supervisorsRepository.findAll();
    }

    /**
     * {@code GET  /supervisors/:id} : get the "id" supervisors.
     *
     * @param id the id of the supervisors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supervisors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supervisors/{id}")
    public ResponseEntity<Supervisors> getSupervisors(@PathVariable Long id) {
        log.debug("REST request to get Supervisors : {}", id);
        Optional<Supervisors> supervisors = supervisorsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supervisors);
    }

    /**
     * {@code DELETE  /supervisors/:id} : delete the "id" supervisors.
     *
     * @param id the id of the supervisors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supervisors/{id}")
    public ResponseEntity<Void> deleteSupervisors(@PathVariable Long id) {
        log.debug("REST request to delete Supervisors : {}", id);
        supervisorsRepository.deleteById(id);
        supervisorsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/supervisors?query=:query} : search for the supervisors corresponding
     * to the query.
     *
     * @param query the query of the supervisors search.
     * @return the result of the search.
     */
    @GetMapping("/_search/supervisors")
    public List<Supervisors> searchSupervisors(@RequestParam String query) {
        log.debug("REST request to search Supervisors for query {}", query);
        return StreamSupport
            .stream(supervisorsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
