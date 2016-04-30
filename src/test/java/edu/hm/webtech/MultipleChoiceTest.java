package edu.hm.webtech;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import edu.hm.webtech.entities.MultipleChoice;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.ExerciseRepository;
import edu.hm.webtech.repositories.MultipleChoiceRepository;
import edu.hm.webtech.repositories.TopicRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Arrays;
import java.util.HashSet;

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

    private static String[] topics = new String[]{"Java", "C++", "GUI", "Scrum", "GIT"};

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
    public void createMultipleChoice(){
        HashSet<String> correctChoices = new HashSet<String>(Arrays.asList("a", "b"));
        HashSet<String> wrongChoices = new HashSet<String>(Arrays.asList("c", "d", "e"));

        MultipleChoice choice = new MultipleChoice();
        choice.setCorrectChoices(correctChoices);
        choice.setWrongChoices(wrongChoices);
        choice.setDescription("Test");

        choice = multipleChoiceRepository.save(choice);

        choice = multipleChoiceRepository.findOne(choice.getId());

        assertNotNull(choice.getId());
        assertEquals(Iterators.size(choice.getCorrectChoices().iterator()), correctChoices.size());
        assertEquals(Iterators.size(choice.getWrongChoices().iterator()), wrongChoices.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void createEmptyMultipleChoice(){
        MultipleChoice choice = new MultipleChoice();
        choice.setCorrectChoices(new HashSet<>());
        choice.setWrongChoices(new HashSet<>());
        choice.setDescription("Test");
        multipleChoiceRepository.save(choice);
    }
}
