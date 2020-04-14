package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.Result;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteService {

    List<FavoriteFolder> listFavoriteFolder(String username,boolean publicFlag);

    Result addFavoriteFolder(FavoriteFolder favoriteFolder);

    Result addFavaritePeoblem(String userName, ArrayList<Integer> favoriteFolderId, int problemid);

    List<Integer> problemFavoriteFolderFist(int problemId, String userName);

    int newFavoriteFolder(String username, String folderName, boolean isPublic);

    PageData<List<ProblemListVO>> favoriteFolderProblems(String username, int favoritefolderid, int pageNum, int pageCount,boolean publicFlag);

}
