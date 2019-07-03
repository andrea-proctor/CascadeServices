package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.ItemsRemovedFromPool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ItemsRemovedFromPool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemsRemovedFromPoolRepository extends JpaRepository<ItemsRemovedFromPool, Long> {

}
