package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlessmain.dao.SubmitMapper;
import com.ljqiii.hairlessmain.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitServiceImpl implements SubmitService {
    @Autowired
    SubmitMapper submitMapper;

    @Override
    public List<CorrectLeaderboard> correctLeaderboard() {
        return submitMapper.selectCorrectLeaderboard();
    }
}
