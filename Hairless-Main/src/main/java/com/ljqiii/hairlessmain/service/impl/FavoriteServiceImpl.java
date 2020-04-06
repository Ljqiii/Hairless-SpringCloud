package com.ljqiii.hairlessmain.service.impl;

import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.Result;
import com.ljqiii.hairlessmain.dao.FavoriteMapper;
import com.ljqiii.hairlessmain.dao.ProblemMapper;
import com.ljqiii.hairlessmain.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Override
    public List<FavoriteFolder> listFavoriteFolder(String username) {
        List<FavoriteFolder> allFavoriteFolders = new ArrayList<>();
        allFavoriteFolders.add(FavoriteFolder.builder().id(-1).name("默认收藏夹").userName(username).isPublic(true).build());
        List<FavoriteFolder> favoriteFolders = favoriteMapper.selectFavoriteFolder(username);
        allFavoriteFolders.addAll(favoriteFolders);
        return allFavoriteFolders;
    }

    @Override
    public Result addFavoriteFolder(FavoriteFolder favoriteFolder) {
        Result result = new Result();
        int i = favoriteMapper.deleteFavoriteFolder(favoriteFolder);
        if (i == 1) {
            result.setSuccess(true);
        }
        return result;
    }


    @Override
    public Result addFavaritePeoblem(String userName, ArrayList<Integer> favoriteFolderIds, int problemid) {
        Result result = new Result();

        Problem problem = problemMapper.selectProblemById(problemid);
        if (problem == null) {
            result.setSuccess(false);
            result.setFailReason("问题不存在");
            return result;
        }

        //查之前的否收藏
        List<Integer> previousFavoriteFolders = favoriteMapper.selectFavoriteFoldersByUserNameAndProblem(userName, problem);

        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(previousFavoriteFolders);
        temp.removeAll(favoriteFolderIds);
        //删除
        if (temp.size() != 0 && delFav(problem, userName, temp).isSuccess() == false) {
            result.setSuccess(false);
            result.setFailReason("收藏失败");
            return result;
        }

        temp = new ArrayList<>();
        temp.addAll(favoriteFolderIds);
        temp.removeAll(previousFavoriteFolders);
        if (temp.size() != 0 && addFav(problem, userName, temp).isSuccess() == false) {//添加
            result.setSuccess(false);
            result.setFailReason("收藏失败");
            return result;
        }
        result.setSuccess(true);

        return result;
    }

    @Override
    public List<Integer> problemFavoriteFolderFist(int problemId, String userName) {
        return favoriteMapper.selectProblemFavoriteFolderList(userName, problemId);
    }

    private Result addFav(Problem problem, String userName, ArrayList<Integer> favoriteFolderIds) {
        Result result = new Result();
        int i = favoriteMapper.batchInsertFavoriteProblem(userName, problem, favoriteFolderIds);
        if (i != favoriteFolderIds.size()) {
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        return result;
    }

    private Result delFav(Problem problem, String userName, ArrayList<Integer> favoriteFolderIds) {
        Result result = new Result();
        int i = favoriteMapper.batchDeleteFavoriteProblem(userName, problem, favoriteFolderIds);
        if (i != favoriteFolderIds.size()) {
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        return result;
    }
}
