package edu.hm.webtech;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import edu.hm.webtech.entities.Exercise;
import edu.hm.webtech.entities.MultipleChoice;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.ExerciseRepository;
import edu.hm.webtech.repositories.MultipleChoiceRepository;
import edu.hm.webtech.repositories.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;

/**
 * Created by Fabian on 29.04.2016.
 */
public class MultipleChoiceTest extends ItsApplicationTests {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MultipleChoiceRepository multipleChoiceRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private String[] topics = new String[]{"Java", "C++", "GUI", "Scrum", "GIT"};

    TopicBloomLevel levelA = new TopicBloomLevel();
    TopicBloomLevel levelB = new TopicBloomLevel();
    TopicBloomLevel levelC = new TopicBloomLevel();

    private MockMvc mockMvc;

    @Before
    public void setMockUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setUp(){
        Arrays.stream(topics).forEach(s -> {
            Topic t = new Topic();
            t.setName(s);
            topicRepository.save(t);
        });
        levelA.setTopic(topicRepository.findTopicByName("Java"));
        levelA.setBloomLevel(BloomLevel.ANALYSIEREN);
        levelB.setTopic(topicRepository.findTopicByName("Java"));
        levelB.setBloomLevel(BloomLevel.BEWERTEN);
        levelC.setTopic(topicRepository.findTopicByName("Scrum"));
        levelC.setBloomLevel(BloomLevel.ERINNERN);
    }

    @After
    public void clearDB(){
        topicRepository.deleteAll();
        multipleChoiceRepository.deleteAll();
        exerciseRepository.deleteAll();
    }

    @Test
    public void testDataInitialized(){
        assertEquals(topics.length, Iterables.size(topicRepository.findAll()));
    }

    @Test
    public void testCreateCorrectMultipleChoice(){
        HashSet<String> correctChoices = new HashSet<String>(Arrays.asList("a", "b"));
        HashSet<String> wrongChoices = new HashSet<String>(Arrays.asList("c", "d", "e"));

        MultipleChoice choiceA = new MultipleChoice();
        MultipleChoice choiceB = new MultipleChoice();
        MultipleChoice choiceC = new MultipleChoice();
        choiceA.setDescription("Test");
        choiceB.setDescription("Test");
        choiceC.setDescription("Test");


        choiceA.setCorrectChoices(correctChoices);
        choiceA.setWrongChoices(wrongChoices);
        choiceA = multipleChoiceRepository.save(choiceA);
        choiceA = multipleChoiceRepository.findOne(choiceA.getId());
        assertNotNull(choiceA.getId());
        assertEquals(Iterators.size(choiceA.getCorrectChoices().iterator()), correctChoices.size());
        assertEquals(Iterators.size(choiceA.getWrongChoices().iterator()), wrongChoices.size());

        choiceB.setCorrectChoices(correctChoices);
        choiceB.setWrongChoices(new HashSet<>());
        choiceB = multipleChoiceRepository.save(choiceB);
        choiceB = multipleChoiceRepository.findOne(choiceB.getId());
        assertNotNull(choiceB.getId());
        assertEquals(Iterators.size(choiceB.getCorrectChoices().iterator()), correctChoices.size());
        assertEquals(Iterators.size(choiceB.getWrongChoices().iterator()), 0);

        choiceC.setCorrectChoices(new HashSet<>());
        choiceC.setWrongChoices(wrongChoices);
        choiceC = multipleChoiceRepository.save(choiceC);
        choiceC = multipleChoiceRepository.findOne(choiceC.getId());
        assertNotNull(choiceC.getId());
        assertEquals(Iterators.size(choiceC.getCorrectChoices().iterator()), 0);
        assertEquals(Iterators.size(choiceC.getWrongChoices().iterator()), wrongChoices.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateEmptyMultipleChoice(){
        MultipleChoice choice = new MultipleChoice();
        choice.setCorrectChoices(new HashSet<>());
        choice.setWrongChoices(new HashSet<>());
        choice.setDescription("Test");
        multipleChoiceRepository.save(choice);
    }

    @Test
    public void testFindBy(){
        String desc1 = "Test1";
        String desc2 = "Test2";
        String desc3 = "Test3";
        Set<String> choices = new HashSet<>(Arrays.asList("a", "b", "c"));
        MultipleChoice c1 = new MultipleChoice();
        MultipleChoice c2 = new MultipleChoice();
        MultipleChoice c3 = new MultipleChoice();
        c1.setDescription(desc1);
        c2.setDescription(desc2);
        c3.setDescription(desc3);
        c1.setCorrectChoices(choices);
        c2.setCorrectChoices(choices);
        c3.setCorrectChoices(choices);
        c1.setWrongChoices(new HashSet<>());
        c2.setWrongChoices(new HashSet<>());
        c3.setWrongChoices(new HashSet<>());

        c1.setTopicBloomLevel(new HashSet<>(Arrays.asList(levelA, levelC)));
        c2.setTopicBloomLevel(new HashSet<>(Collections.singletonList(levelB)));
        c3.setTopicBloomLevel(new HashSet<>(Collections.singletonList(levelC)));
        multipleChoiceRepository.save(Arrays.asList(c1, c2, c3));

        List<Exercise> exercises = exerciseRepository.findByTopicBloomLevelTopic(topicRepository.findTopicByName("Java"));

        assertEquals(exercises.size(), 2);
        assertTrue(exercises.stream().anyMatch(exercise -> exercise.getDescription().equals(desc1)));
        assertTrue(exercises.stream().anyMatch(exercise -> exercise.getDescription().equals(desc2)));
    }
}
