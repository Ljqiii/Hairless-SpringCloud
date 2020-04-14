package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlesscommon.vo.SubmitedItemVO;

import java.util.HashMap;
import java.util.List;

public interface SubmitService {
    List<CorrectLeaderboard> correctLeaderboard();

    HashMap<String, Integer> userAccuracyData(String username);

    List<SubmitedItemVO> getAllSubmit(String username, int problemId);

}
