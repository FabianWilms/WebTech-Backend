package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.hm.webtec.ItsApplication;
import edu.hm.webtec.entities.Topic;
import edu.hm.webtec.repositories.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Fabian on 21.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ItsApplication.class)
@WebAppConfiguration
public class TopicTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    TopicRepository topicRepository;

    private MockMvc mockMvc;

    @Before
    public void setupMockMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


    /**
     * Test the creation of a topic via the repository.
     */
    @Test
    public void testCreate() {
        String topic = "Topic";
        Topic t = new Topic();
        t.setTopic("Topic");
        t = topicRepository.save(t);
        assertNotNull(t.getId());
        assertEquals(topic, t.getTopic());
    }

    /**
     * Test the unique constraint on the column for the topics name.
     */
    @Test(expected = DataIntegrityViolationException.class)
    public void testDuplicate() {
        String topic = "sametopic";
        Topic t1 = new Topic();
        Topic t2 = new Topic();
        t1.setTopic(topic);
        t2.setTopic(topic);
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
        t.setTopic(topic);

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
                .andExpect(jsonPath("topic", is("asdf")));
    }


}
