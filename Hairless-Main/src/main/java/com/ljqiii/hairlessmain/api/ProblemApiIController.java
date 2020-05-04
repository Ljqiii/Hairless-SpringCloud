package com.ljqiii.hairlessmain.api;

import com.ljqiii.hairlesscommon.apiform.GetProblemVoForm;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProblemApiIController {

    @Autowired
    ProblemService problemService;


    @PostMapping("/getproblem")
    public PageData<List<ProblemListVO>> getProblems(@RequestBody GetProblemVoForm getProblemVoForm) {
        return problemService.getProblemByProblemIds(
                getProblemVoForm.getProblemids(),
                getProblemVoForm.getUsername(),
                1, Integer.MAX_VALUE);
    }
}
