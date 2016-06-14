package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.hm.webtech.cloze.Cloze;
import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.cloze.ClozeRepository;
import edu.hm.webtech.exercise.ExerciseRepository;
import edu.hm.webtech.topic.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author M. Streich, mstreich@hm.edu
 * @version 02.05.16.
 */
public class ClozeTest extends ItsApplicationTests {
    private final static String[] topics = new String[]{"Java", "C++", "GUI", "Scrum", "GIT"};

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ClozeRepository clozeRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setMockUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void setTopics() {
        Arrays.stream(topics).forEach(topic -> topicRepository.save(new Topic(topic)));
    }

    @After
    public void clearDB() {
        topicRepository.deleteAll();
        clozeRepository.deleteAll();
        exerciseRepository.deleteAll();
    }


    /**
     * Test if {@link Cloze} is correctly saved.
     */
    @Test
    public void saveClozeCorrectlyTest() {

    }


    /**
     * Test GET-Request to endpoint.
     *
     * @throws Exception
     */
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/clozes"))
                .andExpect(status().isOk());
    }

    /**
     * Test POST to endpoint.
     *
     * @throws Exception
     */
    @Test
    public void testRequestPost() throws Exception {
        Cloze choice = new Cloze("Test", "Text[[omission]]Text", null);

        mockMvc.perform(post("/clozes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(choice))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description", is("Test")));
    }
}