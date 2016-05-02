package edu.hm.webtech.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.webtech.BloomLevel;
import edu.hm.webtech.ItsApplicationTests;
import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.entities.Association;
import edu.hm.webtech.entities.Topic;
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

import static org.junit.Assert.assertEquals;
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

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testTeacherCanCreateExercises() throws Exception {
        final Set<TopicBloomLevel> levels = Collections.singleton(new TopicBloomLevel(BloomLevel.ANWENDEN, new Topic("A")));
        final Map<String, Set<String>> associations = new HashMap<>();
        associations.put("2", new HashSet<>(Arrays.asList("Bird")));
        associations.put("4", new HashSet<>(Arrays.asList("Cat", "Dog")));
        final Association association = new Association("Please match the Animals to their amount of feet.", levels, associations);

        this.mockMvc.perform(post("/associations").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(association)))
                .andExpect(status().isCreated())
                .andDo(document("association-create"));
    }

}
