package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.PoolInventory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PoolInventory} entity.
 */
public interface PoolInventorySearchRepository extends ElasticsearchRepository<PoolInventory, Long> {
}
