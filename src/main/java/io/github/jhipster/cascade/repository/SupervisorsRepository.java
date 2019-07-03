package io.github.jhipster.cascade.repository;

import io.github.jhipster.cascade.domain.Supervisors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Supervisors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupervisorsRepository extends JpaRepository<Supervisors, Long> {

}
