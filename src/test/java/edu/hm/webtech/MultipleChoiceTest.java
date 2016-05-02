package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.validation.ConstraintViolationException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    TopicBloomLevel levelA;
    TopicBloomLevel levelB;
    TopicBloomLevel levelC;

    private MockMvc mockMvc;

    @Before
    public void setMockUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * Initialize {@link TopicRepository} with Test-Data
     */
    @Before
    public void setUp(){
        Arrays.stream(topics).forEach(s -> {
            Topic t = new Topic(s);
            topicRepository.save(t);
        });
        levelA = new TopicBloomLevel(BloomLevel.ANALYSIEREN, topicRepository.findTopicByName("Java"));
        levelB = new TopicBloomLevel(BloomLevel.BEWERTEN, topicRepository.findTopicByName("Java"));
        levelC = new TopicBloomLevel(BloomLevel.ERINNERN, topicRepository.findTopicByName("Scrum"));
    }

    /**
     * Clear Test-Data.
     */
    @After
    public void clearDB(){
        topicRepository.deleteAll();
        multipleChoiceRepository.deleteAll();
        exerciseRepository.deleteAll();
    }

    /**
     * Check if the Data from {@link #setUp()} was properly stored.
     */
    @Test
    public void testDataInitialized(){
        assertEquals(topics.length, Iterables.size(topicRepository.findAll()));
    }

    /**
     * Create several different valid MultipleChoice-Objects.
     */
    @Test
    public void testCreateCorrectMultipleChoice(){
        HashSet<String> correctChoices = new HashSet<String>(Arrays.asList("a", "b"));
        HashSet<String> wrongChoices = new HashSet<String>(Arrays.asList("c", "d", "e"));

        MultipleChoice choiceA = new MultipleChoice(correctChoices, wrongChoices, "Test", null);
        MultipleChoice choiceB = new MultipleChoice(correctChoices, new HashSet<>(), "Test", null);
        MultipleChoice choiceC = new MultipleChoice(new HashSet<>(), wrongChoices, "Test", null);

        choiceA = multipleChoiceRepository.save(choiceA);
        choiceA = multipleChoiceRepository.findOne(choiceA.getId());
        assertNotNull(choiceA.getId());
        assertEquals(Iterators.size(choiceA.getCorrectChoices().iterator()), correctChoices.size());
        assertEquals(Iterators.size(choiceA.getWrongChoices().iterator()), wrongChoices.size());

        choiceB = multipleChoiceRepository.save(choiceB);
        choiceB = multipleChoiceRepository.findOne(choiceB.getId());
        assertNotNull(choiceB.getId());
        assertEquals(Iterators.size(choiceB.getCorrectChoices().iterator()), correctChoices.size());
        assertEquals(Iterators.size(choiceB.getWrongChoices().iterator()), 0);

        choiceC = multipleChoiceRepository.save(choiceC);
        choiceC = multipleChoiceRepository.findOne(choiceC.getId());
        assertNotNull(choiceC.getId());
        assertEquals(Iterators.size(choiceC.getCorrectChoices().iterator()), 0);
        assertEquals(Iterators.size(choiceC.getWrongChoices().iterator()), wrongChoices.size());
    }

    /**
     * Create a new MultipleChoice-Object with two empty sets.
     */
    @Test(expected = ConstraintViolationException.class)
    public void testCreateEmptyMultipleChoice(){
        MultipleChoice choice = new MultipleChoice(new HashSet<>(), new HashSet<>(), "Test", null);
        multipleChoiceRepository.save(choice);
    }

    /**
     * Check if the {@link ExerciseRepository#findByTopicBloomLevelTopic(Topic)} Method is correctly working.
     */
    @Test
    public void testFindBy(){
        String desc1 = "Test1";
        String desc2 = "Test2";
        String desc3 = "Test3";
        Set<String> choices = new HashSet<>(Arrays.asList("a", "b", "c"));
        MultipleChoice c1 = new MultipleChoice(choices, new HashSet<>(), desc1, new HashSet<>(Arrays.asList(levelA, levelC)));
        MultipleChoice c2 = new MultipleChoice(choices, new HashSet<>(), desc2, new HashSet<>(Collections.singletonList(levelB)));
        MultipleChoice c3 = new MultipleChoice(choices, new HashSet<>(), desc3, new HashSet<>(Collections.singletonList(levelC)));

        multipleChoiceRepository.save(Arrays.asList(c1, c2, c3));

        List<Exercise> exercises = exerciseRepository.findByTopicBloomLevelTopic(topicRepository.findTopicByName("Java"));

        assertEquals(exercises.size(), 2);
        assertTrue(exercises.stream().anyMatch(exercise -> exercise.getDescription().equals(desc1)));
        assertTrue(exercises.stream().anyMatch(exercise -> exercise.getDescription().equals(desc2)));
    }

    /**
     * Test GET-Request to endpoint.
     * @throws Exception
     */
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/multipleChoices"))
                .andExpect(status().isOk());
    }

    /**
     * Test POST to endpoint.
     * @throws Exception
     */
    @Test
    public void testRequestPost() throws Exception {
        Set<String> choices = new HashSet<>(Arrays.asList("a", "b", "c"));
        MultipleChoice choice = new MultipleChoice(choices, new HashSet<>(), "Test", null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/multipleChoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writer.writeValueAsString(choice))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description", is("Test")));
    }
}
