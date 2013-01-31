package org.springframework.examples;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * org.springframework.examples.MvcServerSideTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring-servlet.xml")
public class MvcServerSideTest {

//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mvc;
//
//    @Before
//    public void setup() {
//        this.mvc = webApplicationSetup(this.wac).build();
//    }
//
//    @Test
//    public void getFoo() throws Exception{
//        this.mvc.perform(get("/foo").accept("application/json"))
//                .andExpect(status().isOk())
//                .andExpected(content().mimeType("application/json"))
//                .andExpected(jsonPath("$.name").value("Lee"));
//    }
}
