package edu.hm.webtech.repositories;

import edu.hm.webtech.entities.Association;
import jdk.Exported;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * @author peter-mueller
 */
@Repository
public interface AssociationRepository extends CrudRepository<Association, Long> {
    @Override
    @RestResource(exported = false)
    Iterable<Association> findAll();

    @Override
    @RestResource(exported = false)
    Iterable<Association> findAll(Iterable<Long> iterable);
}
