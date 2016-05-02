package edu.hm.webtech.entities;

import edu.hm.webtech.BloomLevel;
import edu.hm.webtech.ItsApplicationTests;
import edu.hm.webtech.TopicBloomLevel;
import edu.hm.webtech.repositories.AssociationRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author peter-mueller
 */
public class AssociationTest extends ItsApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    AssociationRepository associationRepository;

    @Before
    @Autowired
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
               .build();

        final TopicBloomLevel o = new TopicBloomLevel();
        final Set<TopicBloomLevel> levels = Collections.singleton(o);
        final Association association = new Association("Question?", )
    }
}