package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.InventoryValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InventoryValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryValuesRepository extends JpaRepository<InventoryValues, Long> {

}
