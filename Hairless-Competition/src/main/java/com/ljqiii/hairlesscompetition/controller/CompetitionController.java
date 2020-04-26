package com.ljqiii.hairlesscompetition.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscompetition.form.JoinCompetitionForm;
import com.ljqiii.hairlesscompetition.form.NewCompetitionForm;
import com.ljqiii.hairlesscompetition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @GetMapping("/competitions")
    public HairlessResponse<PageData<List<CompetitionVO>>> competitions(Principal principal,
                                                                        @RequestParam(value = "owner", required = false, defaultValue = "") String owner,
                                                                        @RequestParam(value = "competitionId", required = false, defaultValue = "") Integer competitionId,
                                                                        @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                                        @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {
        HairlessResponse<PageData<List<CompetitionVO>>> response = new HairlessResponse<>();
        PageData<List<CompetitionVO>> listPageData = competitionService.listCompetition(
                competitionId,
                owner,
                principal != null ? principal.getName() : null,
                false, pageNum, pageCount);

        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);

        return response;
    }

    @GetMapping("/competition/{competitionId}")
    public HairlessResponse<CompetitionVO> competition(Principal principal,
                                                       @PathVariable(value = "competitionId") Integer competitionId,
                                                       @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {
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
    public HairlessResponse<JSONObject> pushCompetition(Principal principal,
                                                        @RequestBody NewCompetitionForm newCompetitionForm) {

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();
        int competitionId = competitionService.newCompetition(newCompetitionForm.getTitle(),
                newCompetitionForm.getDescription(),
                principal.getName(),
                newCompetitionForm.getIsPublic(),
                newCompetitionForm.getPassword(),
                newCompetitionForm.getStartTime(),
                newCompetitionForm.getEndTime(),
                newCompetitionForm.getProblemIds());
        responsejson.put("competitionid", competitionId);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/deletecompetition")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER','ROLE_ADMIN')")
    public HairlessResponse<JSONObject> deleteCompetition(Principal principal,
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
    public HairlessResponse<Void> joinCompetition(Principal principal,
                                                  @RequestBody JoinCompetitionForm joinCompetitionForm) {
        HairlessResponse<Void> response = new HairlessResponse<>();
        try {
            competitionService.joinCompetition(
                    Arrays.asList(principal.getName()),
                    joinCompetitionForm.getCompetitionid(),
                    joinCompetitionForm.getPassword());

            response.setCodeMsg(ResultEnum.OK);
        } catch (IllegalArgumentException e) {
            response.setCode(0);
            response.setMsg(e.getMessage());
        }
        return response;
    }

}
