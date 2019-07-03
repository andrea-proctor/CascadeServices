package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.PurchaseOrders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PurchaseOrders} entity.
 */
public interface PurchaseOrdersSearchRepository extends ElasticsearchRepository<PurchaseOrders, Long> {
}
