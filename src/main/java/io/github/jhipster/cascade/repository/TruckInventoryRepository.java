package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.TruckInventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TruckInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TruckInventoryRepository extends JpaRepository<TruckInventory, Long> {

}
