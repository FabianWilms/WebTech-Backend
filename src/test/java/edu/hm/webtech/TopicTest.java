package edu.hm.webtech;

import edu.hm.webtec.ItsApplication;
import edu.hm.webtec.entities.Topic;
import edu.hm.webtec.repositories.TopicRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Fabian on 21.04.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ItsApplication.class)
@WebAppConfiguration
public class TopicTest {

    @Autowired
    TopicRepository topicRepository;

    @Test
    public void testCreate(){
        String topic = "Topic";
        Topic t = new Topic();
        t.setTopic("Topic");
        t = topicRepository.save(t);
        assertNotNull(t.getId());
        assertEquals(topic, t.getTopic());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testDuplicate(){
        String topic = "sametopic";
        Topic t1 = new Topic();
        Topic t2 = new Topic();
        t1.setTopic(topic);
        t2.setTopic(topic);
        topicRepository.save(t1);
        topicRepository.save(t2);
    }
}
