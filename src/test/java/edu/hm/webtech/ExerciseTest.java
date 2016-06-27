package edu.hm.webtech;

import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.exercise.Exercise;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.exercise.ExerciseRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Bianca
 */
public class ExerciseTest extends ItsApplicationTests {
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    ExerciseRepository exerciseRepository;
    
    private MockMvc mockMvc;
    
    @Before
    public void setMockUpMVC() {
         mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * Helper class to test creation of an {@link Exercise}.
     */
    private class ExerciseHelper extends Exercise {
        public ExerciseHelper() {
        }
    }
    
    /**
     * Create an {@link Exercise}.
     * 
     * @return an {@link ExerciseHelper}
     */
    private ExerciseHelper createExercise() {
        TopicBloomLevel tbl = new TopicBloomLevel();
        tbl.setBloomLevel(BloomLevel.VERSTEHEN);
        Topic topic = new Topic();
        topic.setName("Topic");
        topic.setId(1L);
        tbl.setTopicId(topic.getId());
        List<TopicBloomLevel> tbls = new ArrayList<>();
        tbls.add(tbl);
        ExerciseHelper helper = new ExerciseHelper();
        helper.setDescription("Exercise");
        helper.setId(1L);
        helper.setTopicBloomLevel(tbls);
        
        return helper;
    }
    
    /**
     * Test creation of an {@link Exercise}.
     */
    @Test
    public void testCreate() {
        createExercise();
    }
    
    /**
     * Test GET-Request to endpoint.
     * @throws Exception Exception thrown by {@link MockMvc#perform(RequestBuilder)}
     */
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk());
    }
}
