package com.ljqiii.hairlesscompetition.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlesscompetition.form.JoinCompetitionForm;
import com.ljqiii.hairlesscompetition.form.NewCompetitionForm;
import com.ljqiii.hairlesscompetition.service.CompetitionService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @GetMapping("/competitions")
    HairlessResponse<PageData<List<CompetitionVO>>> competitions(Principal principal,
                                                                 @RequestParam(value = "mineOnly", required = false, defaultValue = "false") Boolean mineOnly,
                                                                 @RequestParam(value = "competitionId", required = false, defaultValue = "") Integer competitionId,
                                                                 @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                                 @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {
        List<String> roles = principal != null ? ((OAuth2Authentication) principal).getUserAuthentication().getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()) : new ArrayList<>();

        HairlessResponse<PageData<List<CompetitionVO>>> response = new HairlessResponse<>();
        PageData<List<CompetitionVO>> listPageData = competitionService.listCompetition(
                competitionId,
                (mineOnly == true && !roles.contains(RoleConstants.Admin)) ? principal.getName() : null,
                principal != null ? principal.getName() : null,
                false, pageNum, pageCount);

        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);

        return response;
    }

    @GetMapping("/competition/{competitionId}")
    HairlessResponse<CompetitionVO> competition(Principal principal,
                                                @PathVariable(value = "competitionId") Integer competitionId,
                                                @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {
        List<String> roles = principal != null ? ((OAuth2Authentication) principal).getUserAuthentication().getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()) : new ArrayList<>();

        HairlessResponse<CompetitionVO> response = new HairlessResponse<>();
        PageData<List<CompetitionVO>> listPageData = competitionService.listCompetition(
                competitionId,
                null,
                principal != null ? principal.getName() : null,
                false, pageNum, pageCount);

        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData.getContent().size() == 1 ? listPageData.getContent().get(0) : null);

        return response;
    }


    @PostMapping("/pushcompetition")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    HairlessResponse<JSONObject> pushCompetition(Principal principal,
                                                 @RequestBody NewCompetitionForm newCompetitionForm) {

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        responsejson.put("competitionid", null);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/deletecompetition")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    HairlessResponse<JSONObject> deleteCompetition(Principal principal,
                                                   @RequestBody NewCompetitionForm newCompetitionForm) {

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        responsejson.put("competitionid", null);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/joincompetition")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<Void> joinCompetition(Principal principal,
                                           @RequestBody JoinCompetitionForm joinCompetitionForm) {
        HairlessResponse<Void> response = new HairlessResponse<>();
        competitionService.joinCompetition(Arrays.asList(principal.getName()), joinCompetitionForm.getCompetitionid());

        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

}
