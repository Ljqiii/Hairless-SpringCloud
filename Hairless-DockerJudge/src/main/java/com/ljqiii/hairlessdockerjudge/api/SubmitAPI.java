package com.ljqiii.hairlessdockerjudge.api;

import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlessdockerjudge.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubmitAPI {

    @Autowired
    SubmitService submitService;

    @PostMapping("/getsubmit")
    public List<Submit> getSubmits(@RequestBody List<Integer> submitids) {
        return submitService.getSubmits(submitids);
    }
}
