package com.ljqiii.hairlesscompetition.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import com.ljqiii.hairlesscommon.apiform.GetProblemVoForm;
import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscompetition.client.MainClient;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionProblemMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionSubmitMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionUserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CompetitionUserMapper competitionUserMapper;

    @Autowired
    CompetitionProblemMapper competitionProblemMapper;

    @Autowired
    CompetitionSubmitMapper competitionSubmitMapper;

    @Autowired
    MainClient mainClient;


    public PageData<List<CompetitionVO>> listCompetition(Integer competitionId, String createUserName, String usernameIfJoin, Boolean includeDel, int pageNum, int pageCount) {
        PageData<List<CompetitionVO>> pageData = new PageData<>();
        Page<CompetitionVO> competitionVOs = PageMethod.startPage(pageNum, pageCount)
                .doSelectPage(() -> competitionMapper.selectCompetitionVO(competitionId, createUserName, includeDel, usernameIfJoin));
        pageData.setContent(competitionVOs.getResult());
        pageData.setPageInfo(PageInfo.builder()
                .pageSize(competitionVOs.getPageSize())
                .pageNum(competitionVOs.getPageNum())
                .total(competitionVOs.getTotal())
                .build());
        return pageData;
    }

    @Transactional
    public int newCompetition(String title, String description, String createUserName, boolean isPublic,
                              String password, Date startTime, Date endTime, List<Integer> problemIds) {
        Competition competition = Competition.builder().title(title)
                .description(description).isPublic(isPublic)
                .encodedPassword(passwordEncoder.encode(password))
                .startTime(startTime)
                .endTime(endTime)
                .createUsername(createUserName)
                .isDel(false)
                .build();
        competitionMapper.insertCompetition(competition);
        competitionProblemMapper.insertCompetitionProblem(competition, problemIds);
        return competition.getId();
    }

    @Transactional
    public void joinCompetition(List<String> usernames, Integer competitionId, String password) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);

        Date now = new Date();
        if (now.getTime() - competition.getStartTime().getTime() > 0) {
            throw new IllegalArgumentException("竞赛已开始，禁止加入");
        }

        if ((!competition.getIsPublic()) && (!verifyCompetitionPassword(competitionId, password))) {
            throw new IllegalArgumentException("竞赛密码错误");
        }
        competitionUserMapper.insertCompetitionUser(competition, usernames);
    }

    //验证密码是否正确
    public boolean verifyCompetitionPassword(Integer competitionId, String password) {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);
        return passwordEncoder.matches(password, competition.getEncodedPassword());
    }

    /**
     * 有没有加入竞赛
     *
     * @param competitionId
     * @param username
     * @return
     */
    public boolean isJoinedCompetition(Integer competitionId, String username) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);
        return competitionUserMapper.selectCountCompetitionUser(competition, username) >= 1;
    }


    public PageData<List<ProblemListVO>> getProblemSet(int competitionById, String username) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionById);

        GetProblemVoForm getProblemVoForm = GetProblemVoForm.builder().problemids(
                competitionProblemMapper.selectCompetitionProblemIds(competition))
                .username(username)
                .build();

        PageData<List<ProblemListVO>> problemVOs = mainClient.getProblems(getProblemVoForm);
        return problemVOs;
    }

    //添加提交id到竞赛表
    public void addSubmit(int submitId, int competitionId) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);

        if (competition == null) {
            throw new IllegalArgumentException("竞赛id" + competitionId + "不存在");
        }
        competitionSubmitMapper.insertCompetitionSubmitBySubmitId(competition, submitId);
    }
}
