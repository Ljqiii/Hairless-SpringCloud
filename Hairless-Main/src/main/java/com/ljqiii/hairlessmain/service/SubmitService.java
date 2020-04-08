package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;

import java.util.HashMap;
import java.util.List;

public interface SubmitService {
    List<CorrectLeaderboard> correctLeaderboard();
    HashMap<String, Integer> userAccuracyData(String username);
}
