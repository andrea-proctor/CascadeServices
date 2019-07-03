package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.Locations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Locations} entity.
 */
public interface LocationsSearchRepository extends ElasticsearchRepository<Locations, Long> {
}
