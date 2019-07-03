package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.TruckInventory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TruckInventory} entity.
 */
public interface TruckInventorySearchRepository extends ElasticsearchRepository<TruckInventory, Long> {
}
