package edu.hm.webtech.configurations;

import edu.hm.webtech.ItsApplicationTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CorsFilter;

/**
 * Provides simple Tests to check if the CORS headers are correct.
 * @author peter-mueller
 */
public class DataRESTCorsConfigurationTest extends ItsApplicationTests {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    CorsFilter corsFilter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(corsFilter).build();
    }

    /**
     * Test if a CORS Preflight-Request works.
     * @throws Exception
     */
    @Test
    public void testPreflight() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/")
                .header(HttpHeaders.ORIGIN, "*")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "test");

        final ResultMatcher origin = MockMvcResultMatchers
                .header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        final ResultMatcher methods = MockMvcResultMatchers
                .header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS");
        final ResultMatcher headers = MockMvcResultMatchers
                .header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "test");
        mockMvc.perform(requestBuilder)
                .andExpect(origin).andExpect(methods).andExpect(headers);
    }

    /**
     * Test for the CORS Origin specific header in every Response.
     * @throws Exception
     */
    @Test
    public void testCors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/").header(HttpHeaders.ORIGIN, "*"))
          .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"));
    }
}