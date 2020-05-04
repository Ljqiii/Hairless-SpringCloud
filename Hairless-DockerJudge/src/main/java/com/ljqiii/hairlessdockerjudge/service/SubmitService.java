package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlessdockerjudge.dao.SubmitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubmitService {

    @Autowired
    SubmitMapper submitMapper;

    public List<Submit> getSubmits(List<Integer> submitids) {
        if (submitids.size() > 0) {
            return submitMapper.selectSubmit(submitids);
        } else {
            return new ArrayList<>();
        }
    }
}
