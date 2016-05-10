/* (C) 2016, M. Streich, mstreich@hm.edu
 * Oracle Corporation Java 1.8.0_74, Ubuntu 15.10 64 Bit
 * Intel Celeron 2957U 1.4 GHz x 2, 3.8 GiB RAM
 **/
package edu.hm.webtech.cloze;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author M. Streich, mstreich@hm.edu
 * @version 30.04.16.
 */
@Repository
public interface ClozeRepository extends CrudRepository<Cloze, Long> {
}
