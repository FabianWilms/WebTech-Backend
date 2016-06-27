package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.exercise.Exercise;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.singleChoice.SingleChoice;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.exercise.ExerciseRepository;
import edu.hm.webtech.singleChoice.SingleChoiceRepository;
import edu.hm.webtech.topic.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
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
 * Created by Bianca on 02.05.2016.
 */
public class SingleChoiceTest extends ItsApplicationTests {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    SingleChoiceRepository singleChoiceRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private String[] topics = new String[]{"Java", "C++", "GUI", "Scrum", "GIT"};

    private TopicBloomLevel levelA;
    private TopicBloomLevel levelAA;
    private TopicBloomLevel levelB;
    private TopicBloomLevel levelC;

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
        
        levelA = new TopicBloomLevel(BloomLevel.ANALYSIEREN, 1L);
        levelAA = new TopicBloomLevel(BloomLevel.KREIEREN, 1L);
        levelB = new TopicBloomLevel(BloomLevel.BEWERTEN, 2L);
        levelC = new TopicBloomLevel(BloomLevel.ERINNERN, 3L);
    }

    /**
     * Clear Test-Data.
     */
    @After
    public void clearDB(){
        topicRepository.deleteAll();
        singleChoiceRepository.deleteAll();
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
     * Create valid SingleChoice-Object.
     */
    @Test
    public void testCreateCorrectSingleChoice(){
        String correctChoice = "a";
        HashSet<String> wrongChoices = new HashSet<String>(Arrays.asList("c", "d", "e"));

        SingleChoice choiceA = new SingleChoice(correctChoice, wrongChoices, "Test",
                new ArrayList<>(Arrays.asList(levelA, levelAA)));
        
        choiceA = singleChoiceRepository.save(choiceA);
        choiceA = singleChoiceRepository.findOne(choiceA.getId());
        assertNotNull(choiceA.getId());
        assertEquals(choiceA.getCorrectChoice(), correctChoice);
        assertEquals(Iterators.size(choiceA.getWrongChoices().iterator()), wrongChoices.size());
    }

    /**
     * Create a new SingleChoice-Object with empty wrongChoices set.
     */
    @Test(expected = ConstraintViolationException.class)
    public void testCreateEmptySingleChoice(){
        SingleChoice choice = new SingleChoice("a", new HashSet<>(), "Test", null);
        singleChoiceRepository.save(choice);
    }

    /**
     * Check if the {@link ExerciseRepository#findByTopicBloomLevelTopicId(Long)} Method is correctly working.
     */
    @Test
    public void testFindBy(){
        String desc1 = "Test1";
        String desc2 = "Test2";
        String desc3 = "Test3";
        Set<String> choices = new HashSet<>(Arrays.asList("a", "b", "c"));
        SingleChoice c1 = new SingleChoice("a", choices, desc1, new ArrayList<>(Arrays.asList(levelA, levelC)));
        SingleChoice c2 = new SingleChoice("b", choices, desc2, new ArrayList<>(Collections.singletonList(levelB)));
        SingleChoice c3 = new SingleChoice("c", choices, desc3, new ArrayList<>(Collections.singletonList(levelC)));

        singleChoiceRepository.save(Arrays.asList(c1, c2, c3));

        List<Exercise> exercises = exerciseRepository.findByTopicBloomLevelTopicId(1L);

        assertEquals(exercises.size(), 1);
        assertTrue(exercises.stream().anyMatch(exercise -> exercise.getDescription().equals(desc1)));
    }

    /**
     * Test GET-Request to endpoint.
     * @throws Exception Exception thrown by {@link MockMvc#perform(RequestBuilder)}
     */
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/singleChoices"))
                .andExpect(status().isOk());
    }

    /**
     * Test POST to endpoint.
     * @throws Exception Exception thrown by {@link MockMvc#perform(RequestBuilder)}
     */
    @Test
    public void testRequestPost() throws Exception {
        Set<String> choices = new HashSet<>(Arrays.asList("a", "b", "c"));
        SingleChoice choice = new SingleChoice("a", choices, "Test", null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/singleChoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writer.writeValueAsString(choice))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description", is("Test")));
    }
}
