package com.ljqiii.hairlesscompetition.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    CompetitionMapper competitionMapper;


    @Autowired
    CompetitionUserMapper competitionUserMapper;

    public int addCompetition(Competition competition) {
        return 0;
    }

    public PageData<List<CompetitionVO>> listCompetition(Integer competitionId, String createUserName, String usernameIfJoin, Boolean includeDel, int pageNum, int pageCount) {
        PageData<List<CompetitionVO>> pageData = new PageData<>();
        Page<CompetitionVO> CompetitionVOs = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> competitionMapper.selectCompetitionVO(competitionId, createUserName, includeDel, usernameIfJoin));
        pageData.setContent(CompetitionVOs.getResult());
        pageData.setPageInfo(PageInfo.builder()
                .pageSize(CompetitionVOs.getPageSize())
                .pageNum(CompetitionVOs.getPageNum())
                .total(CompetitionVOs.getTotal())
                .build());
        return pageData;
    }

    @Transactional
    public void joinCompetition(List<String> usernames, Integer competitionId) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);
        competitionUserMapper.insertCompetitionUser(competition, usernames);
    }

}
