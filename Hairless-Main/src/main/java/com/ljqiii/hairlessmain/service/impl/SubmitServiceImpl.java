package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlessmain.dao.SubmitMapper;
import com.ljqiii.hairlessmain.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    SubmitMapper submitMapper;

    @Override
    public List<CorrectLeaderboard> correctLeaderboard() {
        return submitMapper.selectCorrectLeaderboard();
    }

    @Override
    public HashMap<String, Integer> accuracyData(String username) {
        HashMap<String, Integer> data = new HashMap<>();
        int submitCount = submitMapper.selectSubmitCount(username);
        int distinctSuccessCount = submitMapper.selectDistinctSuccessCount(username);
        int successCount = submitMapper.selectSuccessCount(username);
        data.put("successcount", distinctSuccessCount);
        data.put("errorcount", submitCount - successCount);
        return data;
    }
}
