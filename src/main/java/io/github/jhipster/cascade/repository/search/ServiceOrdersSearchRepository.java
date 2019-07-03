package io.github.jhipster.cascade.repository.search;

import io.github.jhipster.cascade.domain.ServiceOrders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceOrders} entity.
 */
public interface ServiceOrdersSearchRepository extends ElasticsearchRepository<ServiceOrders, Long> {
}
