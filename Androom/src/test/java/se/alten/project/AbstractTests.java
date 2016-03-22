package se.alten.project;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AndroomApplication.class)
@TestPropertySource(locations="classpath:test.properties")
public abstract class AbstractTests {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

}
