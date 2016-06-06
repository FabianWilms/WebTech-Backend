package edu.hm.webtech;

import edu.hm.webtech.businessLogic.TestdataInitializer;
import edu.hm.webtech.exercise.ExerciseRepository;
import edu.hm.webtech.topic.TopicRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;

/**
 * Created by Fabian on 31.05.2016.
 */
public class TestdataInitializerTest extends ItsApplicationTests{


    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TestdataInitializer testdataInitializer;

    @Before
    @After
    public void clearDBAfter(){
        exerciseRepository.deleteAll();
        topicRepository.deleteAll();
    }

    @Test
    public void testTestDataInitializer(){
        ResponseEntity<?> testdata = testdataInitializer.createTestdata();
        assertEquals(testdata.getStatusCode(), HttpStatus.CREATED);
    }

}
