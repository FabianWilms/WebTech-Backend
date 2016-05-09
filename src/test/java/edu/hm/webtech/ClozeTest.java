package edu.hm.webtech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.hm.webtech.entities.Cloze;
import edu.hm.webtech.entities.Topic;
import edu.hm.webtech.repositories.ClozeRepository;
import edu.hm.webtech.repositories.ExerciseRepository;
import edu.hm.webtech.repositories.TopicRepository;
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

    private final static String[][][] textsWithSolutions = {
            {{"AAAA <<<BBB>>> CCCC <<<DDD>>>."}, {"AAAA <<<>>> CCCC <<<>>>."}, {"BBB", "DDD"}},
            {{"<<<AAA>>> BBBB <<<CCC>>><<<DDD>>>."}, {"<<<>>> BBBB <<<>>><<<>>>."}, {"AAA", "CCC", "DDD"}},
            {{"<<<AAA>>> BBBB /<<<CCC/>>>."}, {"<<<>>> BBBB <<<CCC>>>."}, {"AAA"}},
            {{"<<<AAA>>> BBBB <<<CCC/>>>>>>"}, {"<<<>>> BBBB <<<>>>>>"}, {"AAA", "CCC>"}},
            {{"<<<////>>>"}, {"<<<>>>"}, {"//"}},
            {{"<<</>/>/>>>>"}, {"<<<>>>"}, {">>>"}},
            {{"<<</<<<>>>"}, {"<<<>>>"}, {"<<<"}},
            {{"/<<<Text/>>>"}, {"<<<Text>>>"}, {}},
            {{"<<<////>>> <<</>/>/>/>>>>"}, {"<<<>>> <<<>>>"}, {"//", ">>>>"}}
    };

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ClozeRepository clozeRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            BloomLevel bloomLevel = BloomLevel.values()[random.nextInt(5)];
            String topic = topics[random.nextInt(4)];
            TopicBloomLevel topicBloomLevel = new TopicBloomLevel(bloomLevel, topicRepository.findTopicByName(topic));
            result.add(topicBloomLevel);
        }
        return result;
    }

    @Test
    public void saveClozeCorrectlyTest() {
        List<Cloze> clozes = Arrays.stream(textsWithSolutions)
                .map(text -> new Cloze("Description", text[0][0], randomTopicBloomLevel()))
                .collect(Collectors.toList());
        clozes.stream().forEach(clozeRepository::save);
        for (int i = 0; i < textsWithSolutions.length; i++) {

            String text = clozeRepository.findOne(clozes.get(i).getId()).getText();
            assertEquals(textsWithSolutions[i][0][0], text);

            String textWithOmissions = clozeRepository.findOne(clozes.get(i).getId()).getTextWithOmissions();
            assertEquals(textsWithSolutions[i][1][0], textWithOmissions);

            List<String> omissonsList = clozeRepository.findOne(clozes.get(i).getId()).getOmissionsList();
            for (int j = 0; j < textsWithSolutions[i][2].length; j++) {
                if (!omissonsList.isEmpty()) {
                    assertEquals(textsWithSolutions[i][2][j], omissonsList.get(j));
                }
                assertEquals(omissonsList.isEmpty(), textsWithSolutions[i][2].length == 0);
            }
        }
    }

    @Test
    public void speedTest() {
        final String testString = "<<<Hier>>> steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>. Hier steht ein <<<Text>>>, der <<<gefiltert werden soll>>>.";
        Cloze cloze = new Cloze("Description", "anyText", randomTopicBloomLevel());
        long time = System.currentTimeMillis();
        cloze.setText(testString);
        time = System.currentTimeMillis() - time;
        assertTrue(time < 10);
        assertEquals(testString, cloze.getText());
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
        Cloze choice = new Cloze("Test", "Text<<<omission>>>Text", null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        mockMvc.perform(post("/clozes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writer.writeValueAsString(choice))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("description", is("Test")));
    }
}