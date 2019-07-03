package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.InventoryValues;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InventoryValues} entity.
 */
public interface InventoryValuesSearchRepository extends ElasticsearchRepository<InventoryValues, Long> {
}
