package edu.hm.webtech.entities;

import com.google.common.collect.Iterables;
import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.ItsApplicationTests;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.association.Association;
import edu.hm.webtech.association.AssociationRepository;
import edu.hm.webtech.exercise.Exercise;
import edu.hm.webtech.exercise.ExerciseRepository;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.topic.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;
import java.util.*;

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
    TopicRepository topicRepository;
    @Autowired
    WebApplicationContext wac;

    @Before
    public void setup() {
        /**aufräumen :( */
        exerciseRepository.deleteAll();
        topicRepository.deleteAll();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

        Topic t = new Topic("A");
        t = topicRepository.save(t);

        final Set<TopicBloomLevel> levels = Collections.singleton(new TopicBloomLevel(BloomLevel.ANWENDEN, t.getId()));
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
        final Association association = new Association("Test", null, new HashMap<>());
        associationRepository.save(association);
    }


}