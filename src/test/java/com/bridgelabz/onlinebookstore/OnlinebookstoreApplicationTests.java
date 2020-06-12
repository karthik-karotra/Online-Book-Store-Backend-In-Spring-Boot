package com.bridgelabz.onlinebookstore;

import com.bridgelabz.onlinebookstore.properties.ApplicationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OnlinebookstoreApplicationTests {

    @MockBean
    public ApplicationProperties applicationProperties;

    @Test
    void contextLoads() {
    }

}
