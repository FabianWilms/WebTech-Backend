package edu.hm.webtech;

import edu.hm.webtech.repositories.ExerciseRepository;
import edu.hm.webtech.repositories.MultipleChoiceRepository;
import edu.hm.webtech.repositories.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author M. Streich, mstreich@hm.edu
 * @version 02.05.16.
 */
public class ClozeTest extends ItsApplicationTests {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    MultipleChoiceRepository multipleChoiceRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setMockUpMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void clearDB() {
        topicRepository.deleteAll();
        multipleChoiceRepository.deleteAll();
        exerciseRepository.deleteAll();
    }

}