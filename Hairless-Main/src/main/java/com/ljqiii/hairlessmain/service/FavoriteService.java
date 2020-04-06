package com.ljqiii.hairlessmain.service;

import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.Result;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteService {

    List<FavoriteFolder> listFavoriteFolder(String username);

    Result addFavoriteFolder(FavoriteFolder favoriteFolder);

    Result addFavaritePeoblem(String userName, ArrayList<Integer> favoriteFolderId, int problemid);

    List<Integer> problemFavoriteFolderFist(int problemId, String userName);
}
