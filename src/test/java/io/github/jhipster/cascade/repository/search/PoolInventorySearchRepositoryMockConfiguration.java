package io.github.jhipster.cascade.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PoolInventorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PoolInventorySearchRepositoryMockConfiguration {

    @MockBean
    private PoolInventorySearchRepository mockPoolInventorySearchRepository;

}
