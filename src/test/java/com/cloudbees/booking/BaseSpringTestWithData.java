package com.cloudbees.booking;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"classpath:/static/db/clean-up.sql",
    "classpath:/static/db/test-direction-data.sql",
    "classpath:/static/db/test-seat-data.sql"
}, executionPhase = BEFORE_TEST_CLASS)
public abstract class BaseSpringTestWithData {

}