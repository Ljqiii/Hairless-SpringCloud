package com.ljqiii.hairlessdockerjudge.client;

import com.ljqiii.hairlesscommon.apiform.AddCompetitionSubmitForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "competition-service")
public interface CompetitionSubmitClient {
    @PostMapping("/addsubmit")
    void addSubmit(@RequestBody AddCompetitionSubmitForm addCompetitionSubmitForm);
}
