package com.ljqiii.hairlessmain.controller;

import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlesscommon.vo.Result;
import com.ljqiii.hairlessmain.form.AddFavoriteProblemForm;
import com.ljqiii.hairlessmain.form.NewFavoriteFolderForm;
import com.ljqiii.hairlessmain.service.FavoriteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.ljqiii.hairlesscommon.enums.ResultEnum.FAVORITE_FOLDER_EXIST;

@RestController
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    //所有收藏夹
    @GetMapping("/favoritefolderlist")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<List<FavoriteFolder>> favoriteFolderList(Principal principal,
                                                                     @RequestParam(value = "username", required = false, defaultValue = "") String username) {
        String name = principal.getName();
        if (StringUtils.isEmpty(username)) username = name;
        boolean publicFlag = false;
        if (!name.equals(username)) {
            publicFlag = true;
        }


        HairlessResponse<List<FavoriteFolder>> response = new HairlessResponse<>();

        List<FavoriteFolder> favoriteFolders = favoriteService.listFavoriteFolder(username, publicFlag);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(favoriteFolders);
        return response;
    }

    //修改问题的收藏夹
    @PostMapping("/favoriteproblem")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<Void> addFavoriteProblem(Principal principal,
                                                     @RequestBody AddFavoriteProblemForm favoriteProblemForm) {
        String userName = principal.getName();
        Result result = favoriteService.addFavaritePeoblem(userName, favoriteProblemForm.getFavoriteFolderList(), favoriteProblemForm.getProblemid());
        HairlessResponse<Void> response = new HairlessResponse<>();
        if (result.isSuccess()) {
            response.setCodeMsg(ResultEnum.OK);
            return response;
        } else {
            response.setMsg(result.getFailReason());
            response.setCode(500);
            return response;
        }
    }

    //一个问题的收藏夹
    @GetMapping("/problemfavoritefolderlist")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<List<Integer>> problemFavoriteFolderFist(Principal principal,
                                                                     @RequestParam("problemid") Integer problemid) {
        HairlessResponse<List<Integer>> response = new HairlessResponse<>();
        String userName = principal.getName();
        List<Integer> integers = favoriteService.problemFavoriteFolderFist(problemid, userName);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(integers);
        return response;
    }

    //新建收藏夹
    @PostMapping("/newfavoritefolder")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<Void> newFavoriteFolder(Principal principal,
                                                    @RequestBody NewFavoriteFolderForm newFavoriteFolderForm) {
        String userName = principal.getName();
        String folderbtnName = newFavoriteFolderForm.getFolderName();
        boolean isPublic = newFavoriteFolderForm.getIsPublic();

        HairlessResponse<Void> response = new HairlessResponse<>();
        try {
            favoriteService.newFavoriteFolder(userName, folderbtnName, isPublic);
            response.setCodeMsg(ResultEnum.OK);
        } catch (Exception e) {
            response.setCodeMsg(FAVORITE_FOLDER_EXIST);
        }
        return response;
    }

    @GetMapping("/favoritefolderproblems")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<PageData<List<ProblemListVO>>> problemList(Principal principal,
                                                                       @RequestParam("favoritefolderid") int favoritefolderid,
                                                                       @RequestParam(value = "username", required = false, defaultValue = "") String username,
                                                                       @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                                       @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {
        String name = principal.getName();
        if (StringUtils.isEmpty(username)) username = name;
        boolean publicFlag = false;
        if (!name.equals(username)) {
            publicFlag = true;
        }

        PageData<List<ProblemListVO>> problemListVOS = favoriteService.favoriteFolderProblems(username, favoritefolderid, pageNum, pageCount, publicFlag);
        HairlessResponse<PageData<List<ProblemListVO>>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(problemListVOS);
        return response;
    }
}
