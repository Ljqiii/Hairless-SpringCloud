package com.ljqiii.hairlesscompetition.api;

import com.ljqiii.hairlesscommon.apiform.AddCompetitionSubmitForm;
import com.ljqiii.hairlesscompetition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CompetitionSubmitApiController {


    @Autowired
    CompetitionService competitionService;

    @PostMapping("/addsubmit")
    public void addSubmit(@RequestBody AddCompetitionSubmitForm addCompetitionSubmitForm) {
        competitionService.addSubmit(addCompetitionSubmitForm.getSubmitId(), addCompetitionSubmitForm.getCompetitionId());
    }
}
