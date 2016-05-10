package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.topic.TopicRepository;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Fabian on 21.04.2016.
 */
public class TopicTest extends ItsApplicationTests{

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    TopicRepository topicRepository;

    private MockMvc mockMvc;

    @Before
    public void setupMockMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void clearDB(){
        topicRepository.deleteAll();
    }


    /**
     * Test the creation of a topic via the repository.
     */
    @Test
    public void testCreate() {
        String topic = "Topic";
        Topic t = new Topic(topic);
        t = topicRepository.save(t);
        assertNotNull(t.getId());
        assertEquals(topic, t.getName());
    }

    @Test
    public void testSearchByName(){
        Topic t1 = new Topic("A");
        Topic t2 = new Topic("B");
        Topic t3 = new Topic("C");
        t1 = topicRepository.save(t1);
        t2 = topicRepository.save(t2);
        t3 = topicRepository.save(t3);

        assertEquals(topicRepository.findTopicByName("A").getId(), t1.getId());
        assertEquals(topicRepository.findTopicByName("B").getId(), t2.getId());
        assertEquals(topicRepository.findTopicByName("C").getId(), t3.getId());
    }

    /**
     * Test the unique constraint on the column for the topics name.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void testDuplicate() {
        String topic = "sametopic";
        Topic t1 = new Topic(topic.toLowerCase());
        Topic t2 = new Topic(topic.toUpperCase());
        topicRepository.save(t1);
        topicRepository.save(t2);
    }

    /**
     * Test get-request on topics-endpoint.
     * @throws Exception
     */
    @Test
    public void testRequestGet() throws Exception {
        mockMvc.perform(get("/topics"))
                .andExpect(status().isOk());
    }

    /**
     * Test saving a topic via REST-API
     * @throws Exception
     */
    @Test
    public void testRequestPost() throws Exception {
        String topic = "asdf";

        Topic t = new Topic(topic);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/topics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writer.writeValueAsString(t))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name", is(topic)))
                .andExpect(jsonPath("id").value(IsNull.notNullValue()));
    }
}
