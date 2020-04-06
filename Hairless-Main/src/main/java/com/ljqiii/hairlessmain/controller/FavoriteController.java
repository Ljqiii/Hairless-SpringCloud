package com.ljqiii.hairlessmain.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.FavoriteFolder;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.Result;
import com.ljqiii.hairlessmain.form.AddFavoriteProblemForm;
import com.ljqiii.hairlessmain.service.FavoriteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    //所有收藏夹
    @GetMapping("/favoritefolderlist")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    public HairlessResponse<List<FavoriteFolder>> favoriteFolderList(Principal principal) {
        HairlessResponse<List<FavoriteFolder>> response = new HairlessResponse<>();

        List<FavoriteFolder> favoriteFolders = favoriteService.listFavoriteFolder(principal.getName());
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

}
