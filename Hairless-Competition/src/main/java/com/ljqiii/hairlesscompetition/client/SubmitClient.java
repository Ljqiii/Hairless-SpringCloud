package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.domain.Submit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "dockerjudge-service")
public interface SubmitClient {

    @PostMapping("/getsubmit")
    List<Submit> getSubmits(@RequestBody List<Integer> submitids);


}
