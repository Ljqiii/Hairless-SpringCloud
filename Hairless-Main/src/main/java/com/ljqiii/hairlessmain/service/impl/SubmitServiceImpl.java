package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlesscommon.vo.SubmitedItemVO;
import com.ljqiii.hairlessmain.dao.ProblemMapper;
import com.ljqiii.hairlessmain.dao.SubmitMapper;
import com.ljqiii.hairlessmain.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.ljqiii.hairlesscommon.utils.Submit2VOUtils.convertSubmit2VO;

@Service
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    SubmitMapper submitMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<CorrectLeaderboard> correctLeaderboard() {
        return submitMapper.selectCorrectLeaderboard();
    }

    @Override
    public HashMap<String, Integer> userAccuracyData(String username) {
        HashMap<String, Integer> data = new HashMap<>();
        int submitCount = submitMapper.selectSubmitCount(username);
        int distinctSuccessCount = submitMapper.selectDistinctSuccessCount(username);
        int successCount = submitMapper.selectSuccessCount(username);
        data.put("successcount", distinctSuccessCount);
        data.put("errorcount", submitCount - successCount);
        return data;
    }

    @Override
    public List<SubmitedItemVO> getAllSubmit(String username, int problemId) {
        Problem problem = problemMapper.selectProblemById(problemId);
        if (problem == null) {
            throw new IllegalArgumentException("问题不存在");
        }
        List<Submit> submits = submitMapper.selectAllSubmitByUserNameAndProblem(username, problem);
        return convertSubmit2VO(submits);
    }

}
