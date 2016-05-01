package edu.hm.webtech.repositories;

import edu.hm.webtech.entities.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author peter-mueller
 */
@Repository
public interface AssociationRepository extends CrudRepository<Association, Long> {
}
