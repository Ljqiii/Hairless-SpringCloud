package com.ljqiii.hairlesscompetition.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.domain.LeaderBoard;
import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlesscommon.utils.TimeUtils;
import com.ljqiii.hairlesscompetition.client.SubmitClient;
import com.ljqiii.hairlesscompetition.client.UserClient;
import com.ljqiii.hairlesscompetition.dao.CompetitionLeaderBoardMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionProblemMapper;
import com.ljqiii.hairlesscompetition.dao.CompetitionSubmitMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 排行榜计算
 */

@Service
@Slf4j
public class CompetitionLeaderService extends QuartzJobBean {

    @Autowired
    SubmitClient submitClient;

    @Autowired
    UserClient userClient;

    @Autowired
    CompetitionMapper competitionMapper;

    @Autowired
    CompetitionSubmitMapper competitionSubmitMapper;

    @Autowired
    CompetitionProblemMapper competitionProblemMapper;

    @Autowired
    CompetitionLeaderBoardMapper competitionLeaderBoardMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        String name = jobKey.getName();
        String group = jobKey.getGroup();
        log.info("Quartz executing job,time:{}, key:{}, group:{}", new Date(), name, group);

        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        calcLeaderBoard(((Integer) mergedJobDataMap.get("competitionId")));
    }

    /**
     * 计算排行榜
     *
     * @param competitionId
     */
    public void calcLeaderBoard(int competitionId) {

        Competition competition = competitionMapper.selecetCompetitionById(competitionId);
        if (competition == null) {
            return;
        }

        List<Integer> submitIds = competitionSubmitMapper.selectSubmitIdbycompetitionId(competitionId);
        List<Submit> submits = submitClient.getSubmits(submitIds);

        HashMap<Integer, String> firstBlood = calcFirstBlood(submits);
        LeaderBoard leaderBoard = new LeaderBoard();

        submits.stream().filter(s -> StringUtils.isNotEmpty(s.getResult())).forEach(submit -> {

            LeaderBoard.LeaderBoardItem leaderBoardItem = leaderBoard.findItemByUserName(submit.getUsername());

            //不存在人，创建一个
            if (leaderBoardItem == null) {
                LeaderBoard.LeaderBoardItem newLeaderBoardItem = LeaderBoard.LeaderBoardItem
                        .builder()
                        .userName(submit.getUsername())
                        .build();


                LeaderBoard.SubmitedProblemItem submitedProblemItem = LeaderBoard.SubmitedProblemItem.builder().
                        isSucess(submit.getResult().equals("success"))
                        .problemId(submit.getProblemid())
                        .firstblood(false)
                        .timecost(submit.getResult().equals("success") ?
                                (submit.getSubmitedTime().getTime() - competition.getStartTime().getTime()) : null)
                        .waTimes(submit.getResult().equals("success") ? 0 : 1)
                        .build();

                //add
                leaderBoard.addLeaderBoardItem(newLeaderBoardItem);
                newLeaderBoardItem.addSolvedProblemItem(submitedProblemItem);

            } else {
                //存在人，更新

                LeaderBoard.SubmitedProblemItem submitedProblemItem = leaderBoardItem.findSubmitedProblemItemBySubmitId(submit.getProblemid());
                //不存在题号
                if (submitedProblemItem == null) {
                    LeaderBoard.SubmitedProblemItem newSubmitedProblemItem = LeaderBoard.SubmitedProblemItem.builder().
                            isSucess(submit.getResult().equals("success"))
                            .problemId(submit.getProblemid())
                            .firstblood(submit.getResult().equals("success") && firstBlood.get(submit.getProblemid()).equals(submit.getUsername()))
                            .timecost(submit.getResult().equals("success") ?
                                    (submit.getSubmitedTime().getTime() - competition.getStartTime().getTime()) : null)
                            .waTimes(submit.getResult().equals("success") ? 0 : 1)
                            .build();
                    leaderBoardItem.addSolvedProblemItem(newSubmitedProblemItem);
                } else {
                    //存在更新
                    if (submit.getResult().equals("success")) {
                        submitedProblemItem.setSucess(true);
                        submitedProblemItem.setFirstblood(firstBlood.get(submit.getProblemid()).equals(submit.getUsername()));
                        submitedProblemItem.setTimecost(submit.getSubmitedTime().getTime() - competition.getStartTime().getTime());
                    } else {
                        submitedProblemItem.setWaTimes(submitedProblemItem.getWaTimes() + 1);
                    }
                }

            }
        });
        Collections.sort(leaderBoard.getLeaderBoardItems());
        ArrayList<LeaderBoardVO> leaderBoardVOS = convert2LeaderBoardItemVO(leaderBoard);
        String s1 = JSON.toJSONString(leaderBoardVOS);
        competitionLeaderBoardMapper.softDeleteInfoByCompetitionId(competition);
        competitionLeaderBoardMapper.insertInfo(competition, s1, generateLeaderBoardMetaInfo(competition, submits).toJSONString());
        int a = 1;
    }

    private ArrayList<LeaderBoardVO> convert2LeaderBoardItemVO(LeaderBoard leaderBoard) {
        ArrayList<LeaderBoardVO> leaderBoardVOS = new ArrayList<>();
        int rank = 1;
        for (LeaderBoard.LeaderBoardItem item : leaderBoard.getLeaderBoardItems()) {
            LeaderBoardVO vo = new LeaderBoardVO();
            vo.setRank(rank++);
            vo.setUserName(item.getUserName());
            vo.setUserNickName(userClient.getUserInfo(item.getUserName()).getNickName());//TODO:批量获取
            vo.setSolved(item.getSubmitedProblemItems().stream().filter(LeaderBoard.SubmitedProblemItem::isSucess).count());

            for (LeaderBoard.SubmitedProblemItem submitedProblemItem : item.getSubmitedProblemItems()) {
                LeaderBoardItemProblem leaderBoardItemProblem = new LeaderBoardItemProblem();
                leaderBoardItemProblem.setProblemId(submitedProblemItem.getProblemId());
                leaderBoardItemProblem.setProblemNumber(submitedProblemItem.getProblemNumber());
                leaderBoardItemProblem.setFirstBlood(submitedProblemItem.isFirstblood());
                leaderBoardItemProblem.setSuccess(submitedProblemItem.isSucess());
                leaderBoardItemProblem.setWaTimes(submitedProblemItem.getWaTimes());
                if (submitedProblemItem.isSucess()) {
                    leaderBoardItemProblem.setTimeCost(TimeUtils.convertMilliSecond2TimeHmsStr(submitedProblemItem.getTimecost()));
                }
                vo.getLeaderBoardItemProblems().put(String.valueOf(submitedProblemItem.getProblemId()) + "_costTime", leaderBoardItemProblem);
            }
            leaderBoardVOS.add(vo);
        }
        return leaderBoardVOS;
    }

    @Data
    static class LeaderBoardVO {
        int rank;
        String userName;
        String userNickName;
        long solved;
        HashMap<String, LeaderBoardItemProblem> leaderBoardItemProblems = new HashMap<>();
    }

    @Data
    static class LeaderBoardItemProblem {
        int problemId;
        String problemNumber;
        boolean success;
        int waTimes;
        boolean firstBlood;
        String timeCost;
    }


    /**
     * 计算firstblood
     *
     * @param submits
     * @return
     */
    HashMap<Integer, String> calcFirstBlood(List<Submit> submits) {
        HashMap<Integer, String> firstBlood = new HashMap<>();
        Map<Integer, List<Submit>> groupByProblemId = submits.stream().collect(Collectors.groupingBy(Submit::getProblemid));
        groupByProblemId.forEach((problemid, problemIdsubmits) -> {
            Submit submit = problemIdsubmits.stream().min((o1, o2) -> (int) (o1.getSubmitedTime().getTime() - o2.getSubmitedTime().getTime())).get();
            firstBlood.put(submit.getProblemid(), submit.getUsername());
        });
        return firstBlood;
    }


    /**
     * 生成metainfo
     *
     * @param competition
     * @return
     */
    public JSONArray generateLeaderBoardMetaInfo(Competition competition, List<Submit> submits) {

        HashMap<Integer, String> correctRate = new HashMap<>();
        Map<Integer, List<Submit>> groupByProblemId = submits.stream().collect(Collectors.groupingBy(Submit::getProblemid));
        groupByProblemId.forEach((problemid, probelmSubmitList) -> {
            long submitCount = probelmSubmitList.stream().count();
            long successCount = probelmSubmitList.stream().filter(s -> s.getResult().equals("success")).count();
            correctRate.put(problemid, "(" + successCount + "/" + submitCount + ")");
        });

        List<Integer> competitionProblemIds = competitionProblemMapper.selectCompetitionProblemIds(competition);
        competitionProblemIds.sort((o1, o2) -> o1 - o2);
        JSONArray leaderBoardMeta = new JSONArray();
        leaderBoardMeta.add(newMetaItem("排名", "rank"));
        leaderBoardMeta.add(newMetaItem("用户名", "userName"));
        leaderBoardMeta.add(newMetaItem("昵称", "userNickName"));
        leaderBoardMeta.add(newMetaItem("正确数量", "solved"));

        for (Integer competitionProblemId : competitionProblemIds) {
            leaderBoardMeta.add(newMetaItem(competitionProblemId + " " + correctRate.get(competitionProblemId), competitionProblemId + "_costTime"));
        }
        return leaderBoardMeta;
    }

    /**
     * 新的meteitem
     *
     * @param name
     * @param propName
     * @return
     */
    public JSONObject newMetaItem(String name, String propName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("columnName", name);
        jsonObject.put("dataprop", propName);
        return jsonObject;
    }

    /**
     * 获得结果
     *
     * @param competitionId
     * @return
     */
    public HashMap<String, String> getLeaderBoard(Integer competitionId) {
        Competition competition = competitionMapper.selecetCompetitionById(competitionId);
        if (competition == null) {
            return new HashMap<>();
        }
        return competitionLeaderBoardMapper.selectLeaderBoardByCompetition(competition);
    }
}
