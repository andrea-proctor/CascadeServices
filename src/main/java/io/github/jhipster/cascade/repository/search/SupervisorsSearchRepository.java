package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.Supervisors;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Supervisors} entity.
 */
public interface SupervisorsSearchRepository extends ElasticsearchRepository<Supervisors, Long> {
}
