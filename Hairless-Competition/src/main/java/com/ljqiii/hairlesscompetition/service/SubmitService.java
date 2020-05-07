package com.ljqiii.hairlesscompetition.service;

import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlesscommon.vo.SubmitedItemVO;
import com.ljqiii.hairlesscompetition.client.SubmitClient;
import com.ljqiii.hairlesscompetition.dao.CompetitionSubmitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ljqiii.hairlesscommon.utils.Submit2VOUtils.convertSubmit2VO;

@Service
public class SubmitService {

    @Autowired
    SubmitClient submitClient;

    @Autowired
    CompetitionSubmitMapper competitionSubmitMapper;

    public List<SubmitedItemVO> getSubmits(int competitionId, String username) {
        List<Integer> submitIds = competitionSubmitMapper.selectSubmitIdbycompetitionIdAndUserName(competitionId, username);
        List<Submit> submits = submitClient.getSubmits(submitIds);
        return convertSubmit2VO(submits);
    }
}
