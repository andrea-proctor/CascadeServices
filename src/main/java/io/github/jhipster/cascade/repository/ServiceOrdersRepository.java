package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.ServiceOrders;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOrders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOrdersRepository extends JpaRepository<ServiceOrders, Long> {

}
