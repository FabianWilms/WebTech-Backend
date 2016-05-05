package edu.hm.webtech;

import edu.hm.webtech.entities.Cloze;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.ClozeRepository;
import edu.hm.webtech.repositories.ExerciseRepository;
import edu.hm.webtech.repositories.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author M. Streich, mstreich@hm.edu
 * @version 02.05.16.
 */
public class ClozeTest extends ItsApplicationTests {
    private final static String[] topics = new String[]{"Java", "C++", "GUI", "Scrum", "GIT"};

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ClozeRepository clozeRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

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
     * 3 random topicBloomLevels.
     *
     * @return 3 random topicBloomLevels.
     */
    private Set<TopicBloomLevel> randomTopicBloomLevel() {
        Set<TopicBloomLevel> result = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            BloomLevel bloomLevel = BloomLevel.values()[random.nextInt(5)];
            String topic = topics[random.nextInt(4)];
            TopicBloomLevel topicBloomLevel = new TopicBloomLevel(bloomLevel, topicRepository.findTopicByName(topic));
            result.add(topicBloomLevel);
        }
        return result;
    }

    @Test
    public void saveClozeTest() {
        Cloze cloze1 = new Cloze("Description", "AAAA<<<BBB>>>CCCC<<<DDD>>>", randomTopicBloomLevel());
        String[] solutions1 = {"BBB", "DDD"};
        clozeRepository.save(cloze1);
        System.out.println(clozeRepository.findOne(cloze1.getId()).getText());
        List<String> omissonsList = clozeRepository.findOne(cloze1.getId()).getOmissionsList();
        omissonsList.stream().forEach(System.out::println);
        for (int i = 0; i < solutions1.length; i++)
            assertEquals(clozeRepository.findOne(cloze1.getId()).getOmissionsList().get(i), solutions1[i]);
    }
}