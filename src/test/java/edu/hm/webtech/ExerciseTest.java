package edu.hm.webtech;

import edu.hm.webtech.entities.Exercise;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.ExerciseRepository;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    private class ExerciseHelper extends Exercise {
        public ExerciseHelper() {
        }
    }
    
    private ExerciseHelper createExercise() {
        TopicBloomLevel tbl = new TopicBloomLevel();
        tbl.setBloomLevel(BloomLevel.VERSTEHEN);
        Topic topic = new Topic();
        topic.setName("Topic");
        topic.setId(1L);
        tbl.setTopicId(topic.getId());
        Set<TopicBloomLevel> tbls = new HashSet<>();
        tbls.add(tbl);
        ExerciseHelper helper = new ExerciseHelper();
        helper.setDescription("Exercise");
        helper.setId(1L);
        helper.setTopicBloomLevel(tbls);
        
        return helper;
    }
    
    @Test
    public void testCreate() {
        createExercise();
    }
    
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk());
    }
}
