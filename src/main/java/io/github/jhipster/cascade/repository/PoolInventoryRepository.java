package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.PoolInventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PoolInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoolInventoryRepository extends JpaRepository<PoolInventory, Long> {

}
