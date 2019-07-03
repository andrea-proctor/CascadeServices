package io.github.jhipster.cascade.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TruckInventorySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TruckInventorySearchRepositoryMockConfiguration {

    @MockBean
    private TruckInventorySearchRepository mockTruckInventorySearchRepository;

}
