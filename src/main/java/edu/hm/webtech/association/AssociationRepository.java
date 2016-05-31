package edu.hm.webtech.association;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Repository of the entity {@link Association}.
 * 
 * @author peter-mueller
 */
@Repository
public interface AssociationRepository extends CrudRepository<Association, Long> {
    
    /**
     * Find all {@link Associations}.
     * @return all associations
     */
    @Override
    @RestResource(exported = false)
    Iterable<Association> findAll();

    /**
     * Find all {@link Associations} with associated {@code iterable}.
     * @param iterable iterable
     * @return all matching associations
     */
    @Override
    @RestResource(exported = false)
    Iterable<Association> findAll(final Iterable<Long> iterable);
}
