package edu.hm.webtech.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.webtech.ItsApplicationTests;
import edu.hm.webtech.association.Association;
import edu.hm.webtech.association.AssociationRepository;
import edu.hm.webtech.cloze.ClozeRepository;
import edu.hm.webtech.exercise.BloomLevel;
import edu.hm.webtech.exercise.ExerciseRepository;
import edu.hm.webtech.exercise.TopicBloomLevel;
import edu.hm.webtech.multipleChoice.MultipleChoice;
import edu.hm.webtech.multipleChoice.MultipleChoiceRepository;
import edu.hm.webtech.singleChoice.SingleChoiceRepository;
import edu.hm.webtech.topic.Topic;
import edu.hm.webtech.topic.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author peter-mueller
 */
public class TeacherDocumentation extends ItsApplicationTests {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private SingleChoiceRepository singleChoiceRepository;
    @Autowired
    private ClozeRepository clozeRepository;
    @Autowired
    private MultipleChoiceRepository multipleChoiceRepository;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @After
    public void clean() {
        exerciseRepository.deleteAll();
        topicRepository.deleteAll();
        clozeRepository.deleteAll();
        associationRepository.deleteAll();
        singleChoiceRepository.deleteAll();
        multipleChoiceRepository.deleteAll();
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andDo(document("index"));
    }

    @Test
    public void testTeacherCanCreateExercises() throws Exception {
        Topic a = new Topic("B");
        a = topicRepository.save(a);

        final Map<String, Object> simpleTopicBloomLevel = new HashMap<>();
        simpleTopicBloomLevel.put("bloomLevel", "ANWENDEN");
        simpleTopicBloomLevel.put("topicId", a.getId());

        final Map<String, Object> association = new HashMap<>();
        association.put("description", "Please match the Animals to their amount of feet.");
        association.put("topicBloomLevel", Collections.singletonList(simpleTopicBloomLevel));
        final Map<String, Object> associations = new HashMap<>();
        associations.put("2", new String[]{"Bird"});
        associations.put("4", new String[]{"Dog", "Cat"});
        association.put("associations", associations);

        this.mockMvc.perform(post("/associations").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(association)))
                .andExpect(status().isCreated())
                .andDo(document("association-create"));

        final Map<String, Object> multipleChoice = new HashMap<>();
        multipleChoice.put("description", "Choose all Colors that contain blue!");
        multipleChoice.put("topicBloomLevel", Collections.singletonList(simpleTopicBloomLevel));
        multipleChoice.put("correctChoices", new String[]{"Blue", "Cyan"});
        multipleChoice.put("wrongChoices", new String[]{"Red", "Green"});

        mockMvc.perform(post("/multipleChoices").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(multipleChoice)))
                .andExpect(status().isCreated())
                .andDo(document("multipleChoice-create"));

        final Map<String, Object> singleChoice = new HashMap<>();
        singleChoice.put("description", "Choose Blue!");
        singleChoice.put("topicBloomLevel", Collections.singletonList(simpleTopicBloomLevel));
        singleChoice.put("correctChoice", "Blue");
        singleChoice.put("wrongChoices", new String[]{"Green", "Red"});

        mockMvc.perform(post("/singleChoices").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(singleChoice)))
                .andExpect(status().isCreated())
                .andDo(document("singleChoice-create"));

        final Map<String, Object> cloze = new HashMap<>();
        cloze.put("description", "Fill in the Gaps!");
        cloze.put("topicBloomLevel", Collections.singletonList(simpleTopicBloomLevel));
        cloze.put("text", "The correct color choice is always [[Blue]].");

        mockMvc.perform(post("/clozes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cloze)))
                .andExpect(status().isCreated())
                .andDo(document("cloze-create"));
    }

    @Test
    public void testTeacherCanCreateTopic() throws Exception {
        final HashMap<String,Object> topic = new HashMap<>();
        topic.put("name", "Golang");

        mockMvc.perform(post("/topics").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(topic)))
                .andExpect(status().isCreated())
                .andDo(document("topic-create"));
    }

    @Test
    public void testTeacherCanUpdateExercises() throws  Exception {

    }

    @Test
    public void testTeacherCanUpdateTopic() throws Exception {
        Topic a = new Topic("B");
        a = topicRepository.save(a);

        final HashMap<String, Object> updateTopic = new HashMap<>();
        updateTopic.put("name","BB");

        mockMvc.perform(put("/topics/" + a.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateTopic)))
                .andExpect(status().isNoContent())
                .andDo(document("topic-update"));
    }

    @Test
    public void testTeacherCanReadExercises() throws Exception {
        Topic a = new Topic("A");
        a = topicRepository.save(a);
        final List<TopicBloomLevel> levels = Collections.singletonList(new TopicBloomLevel(BloomLevel.ANWENDEN, a.getId()));
        final Set<String> correctChoices = new HashSet<>(Arrays.asList("1", "2", "3"));
        final Set<String> wrongChoices = new HashSet<>(Arrays.asList("1", "2", "3"));
        final MultipleChoice multipleChoice = new MultipleChoice(correctChoices, wrongChoices, "Have fun!", levels);
        multipleChoiceRepository.save(multipleChoice);

        final Map<String, Set<String>> associations = new HashMap<>();
        associations.put("2", new HashSet<>(Arrays.asList("Bird")));
        associations.put("4", new HashSet<>(Arrays.asList("Cat", "Dog")));
        final Association association = new Association("Please match the Animals to their amount of feet.", levels, associations);
        associationRepository.save(association);


        this.mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk())
                .andDo(document("exercises-get"));
    }

}
