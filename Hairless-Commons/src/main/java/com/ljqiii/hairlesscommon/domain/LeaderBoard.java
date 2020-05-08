package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaderBoard {

    List<LeaderBoardItem> leaderBoardItems = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubmitedProblemItem implements Comparable<SubmitedProblemItem> {
        boolean isSucess;//当前是否正确
        int waTimes;//错误次数
        int problemId;//题目id
        String problemNumber;//在当前场的竞赛中题目号
        boolean firstblood;//整场比赛中此此人题目是否是第一个正确的
        Long timecost;//本题目花费时间

        //如果有题号，根据题号排序，没有根据id排序
        @Override
        public int compareTo(SubmitedProblemItem o) {
            if (this.problemNumber == null || o.problemNumber == null) {
                return this.problemId - o.problemId;
            } else {
                return this.problemNumber.compareTo(o.problemNumber);
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LeaderBoardItem implements Comparable<LeaderBoardItem> {

        String userName;
        SortedSet<SubmitedProblemItem> submitedProblemItems = new TreeSet<>();

        /**
         * 添加
         *
         * @param submitedProblemItem
         */
        public void addSolvedProblemItem(SubmitedProblemItem submitedProblemItem) {
            if (submitedProblemItems == null) {
                submitedProblemItems = new TreeSet<>();
            }
            this.submitedProblemItems.add(submitedProblemItem);
        }

        public SubmitedProblemItem findSubmitedProblemItemBySubmitId(int submitId) {
            for (SubmitedProblemItem submitedProblemItem : this.submitedProblemItems) {
                if (submitedProblemItem.getProblemId() == submitId) {
                    return submitedProblemItem;
                }
            }
            return null;
        }

        //根据解出所有题目所用时间排序
        @Override
        public int compareTo(LeaderBoardItem o) {
            //解决问题数量
            int currentSolved;
            int comparedSolved;

            //所用时间
            long currentSumtime = 0;
            long comparedSumtime = 0;

            if (this.submitedProblemItems != null && o.getSubmitedProblemItems() != null) {
                currentSolved = (int) this.submitedProblemItems.stream().filter(SubmitedProblemItem::isSucess).count();
                comparedSolved = (int) o.submitedProblemItems.stream().filter(SubmitedProblemItem::isSucess).count();

                if (currentSolved != comparedSolved) {
                    return comparedSolved - currentSolved;
                }
                currentSumtime = this.submitedProblemItems.stream().mapToLong(SubmitedProblemItem::getTimecost).sum();
                comparedSumtime = o.submitedProblemItems.stream().mapToLong(SubmitedProblemItem::getTimecost).sum();
            }
            return (int) (comparedSumtime - currentSumtime);
        }
    }

    /**
     * 根据username查找item
     *
     * @param username
     * @return
     */
    public LeaderBoardItem findItemByUserName(String username) {
        for (LeaderBoardItem leaderBoardItem : leaderBoardItems) {
            if (leaderBoardItem.getUserName().equals(username)) {
                return leaderBoardItem;
            }
        }
        return null;
    }

    /**
     * 添加一项item
     *
     * @param leaderBoardItem
     */
    public void addLeaderBoardItem(LeaderBoardItem leaderBoardItem) {
        if (this.leaderBoardItems == null) {
            this.leaderBoardItems = new ArrayList<>();
        }
        //TODO:根据username检查当前是否存在
        this.leaderBoardItems.add(leaderBoardItem);
    }


}
