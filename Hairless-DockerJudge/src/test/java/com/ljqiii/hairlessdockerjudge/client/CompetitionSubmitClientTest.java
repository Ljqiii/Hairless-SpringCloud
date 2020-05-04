package com.ljqiii.hairlessdockerjudge.client;

import com.ljqiii.hairlesscommon.apiform.AddCompetitionSubmitForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompetitionSubmitClientTest {

    @Autowired
    CompetitionSubmitClient competitionSubmitClient;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addSubmit() {
        competitionSubmitClient.addSubmit(AddCompetitionSubmitForm.builder()
                .submitId(1)
                .competitionId(2)
                .build());
    }
}
