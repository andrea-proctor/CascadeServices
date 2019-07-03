package io.github.jhipster.cascade.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ItemsRemovedFromPoolSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ItemsRemovedFromPoolSearchRepositoryMockConfiguration {

    @MockBean
    private ItemsRemovedFromPoolSearchRepository mockItemsRemovedFromPoolSearchRepository;

}
