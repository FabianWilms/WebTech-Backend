package edu.hm.webtech.singleChoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of the entity {@link SingleChoice}.
 * 
 * @author Bianca
 */
@Repository
public interface SingleChoiceRepository extends CrudRepository<SingleChoice, Long> {}
