package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.TopicRepository;
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
        Topic t = new Topic();
        t.setName("Topic");
        t = topicRepository.save(t);
        assertNotNull(t.getId());
        assertEquals(topic, t.getName());
    }

    /**
     * Test the unique constraint on the column for the topics name.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void testDuplicate() {
        String topic = "sametopic";
        Topic t1 = new Topic();
        Topic t2 = new Topic();
        t1.setName(topic.toLowerCase());
        t2.setName(topic.toUpperCase());
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

        Topic t = new Topic();
        t.setName(topic);

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
                .andExpect(jsonPath("name", is(topic)));
    }


}
