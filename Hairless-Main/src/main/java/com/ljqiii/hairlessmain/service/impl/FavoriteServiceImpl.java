package com.ljqiii.hairlessmain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.Result;
import com.ljqiii.hairlessmain.dao.FavoriteMapper;
import com.ljqiii.hairlessmain.dao.ProblemMapper;
import com.ljqiii.hairlessmain.service.FavoriteService;
import com.ljqiii.hairlessmain.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    ProblemService problemService;

    @Override
    public List<FavoriteFolder> listFavoriteFolder(String username, boolean publicFlag) {
        List<FavoriteFolder> allFavoriteFolders = new ArrayList<>();
        allFavoriteFolders.add(FavoriteFolder.builder().id(-1).name("默认收藏夹").userName(username).isPublic(true).build());
        List<FavoriteFolder> favoriteFolders = favoriteMapper.selectFavoriteFolder(username,publicFlag);
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

    @Override
    public int newFavoriteFolder(String username, String folderName, boolean isPublic) {
        int i1 = favoriteMapper.selectCountUserNameFavoriteFolder(username, folderName);
        if (i1 == 1) {
            throw new IllegalArgumentException("文件夹已存在");
        }
        FavoriteFolder favoriteFolder = FavoriteFolder.builder()
                .name(folderName)
                .userName(username)
                .isPublic(isPublic)
                .createtime(new Date())
                .build();

        int i = favoriteMapper.insertFavoriteFolder(favoriteFolder);
        return favoriteFolder.getId();
    }

    @Override
    public PageData<List<ProblemListVO>> favoriteFolderProblems(String username, int favoritefolderid, int pageNum, int pageCount, boolean publicFlag) {
        Page<Problem> problems = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> favoriteMapper.selectFavoriteProblemByUserNameAndFavoriteFolderId(username, favoritefolderid,publicFlag));
        PageData<List<ProblemListVO>> listPageData = problemService.setProblemVOData(username, problems);
        return listPageData;
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
