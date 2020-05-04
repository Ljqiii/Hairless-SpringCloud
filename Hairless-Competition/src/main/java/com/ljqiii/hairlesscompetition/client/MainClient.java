package com.ljqiii.hairlesscompetition.client;

import com.ljqiii.hairlesscommon.apiform.GetProblemVoForm;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "main-service")
public interface MainClient {

    @PostMapping("/getproblem")
    PageData<List<ProblemListVO>> getProblems(@RequestBody GetProblemVoForm getProblemVoForm);

}
