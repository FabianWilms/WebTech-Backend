package edu.hm.webtec.configurations;

import edu.hm.webtec.ItsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * @author peter-mueller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ItsApplication.class)
@WebAppConfiguration
public class DataRESTCorsConfigurationTest {

    @Test
    public void testCors() throws Exception {
        //TODO
    }
}