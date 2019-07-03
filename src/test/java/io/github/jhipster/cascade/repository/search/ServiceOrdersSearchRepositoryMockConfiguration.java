package io.github.jhipster.cascade.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ServiceOrdersSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ServiceOrdersSearchRepositoryMockConfiguration {

    @MockBean
    private ServiceOrdersSearchRepository mockServiceOrdersSearchRepository;

}
