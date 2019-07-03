package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.ItemsRemovedFromPool;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ItemsRemovedFromPool} entity.
 */
public interface ItemsRemovedFromPoolSearchRepository extends ElasticsearchRepository<ItemsRemovedFromPool, Long> {
}
