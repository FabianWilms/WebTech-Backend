package edu.hm.webtech.entities;

import com.google.common.collect.Iterables;
import edu.hm.webtech.BloomLevel;
import edu.hm.webtech.ItsApplicationTests;
import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.repositories.AssociationRepository;
import edu.hm.webtech.repositories.ExerciseRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * @author peter-mueller
 */
public class AssociationTest extends ItsApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    AssociationRepository associationRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    WebApplicationContext wac;

    @Before
    @Autowired
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

        final Set<TopicBloomLevel> levels = Collections.singleton(new TopicBloomLevel(BloomLevel.ANWENDEN, new Topic("A")));
        final Map<String, Set<String>> associations = Collections.singletonMap("Tier", new HashSet<>(Arrays.asList("Hund", "Keks")));
        final Association association = new Association("Question?", levels, associations);
        associationRepository.save(association);
    }

    @After
    public void clear() {
        exerciseRepository.deleteAll();
    }

    @Test
    public void testDataSaved() {
        assertEquals(1, Iterables.size(exerciseRepository.findAll()));
    }

    @Test
    public void testSaveAssociation() {
        final Iterable<Association> all = associationRepository.findAll();
        final Association association = all.iterator().next();
        final Association savedAssociation = associationRepository.save(association);

        final Exercise one = exerciseRepository.findOne(savedAssociation.getId());
        assertTrue(one instanceof Association);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateEmptyAssociations(){
        final Iterable<Association> all = associationRepository.findAll();
        final Association association = all.iterator().next();
        association.setAssociations(new HashMap<>());
        associationRepository.save(association);
    }


}