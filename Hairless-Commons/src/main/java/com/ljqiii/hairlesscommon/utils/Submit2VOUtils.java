package com.ljqiii.hairlesscommon.utils;

import com.ljqiii.hairlesscommon.domain.Submit;
import com.ljqiii.hairlesscommon.vo.SubmitedItemVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Submit2VOUtils {

    //submit转换成vo
    public static List<SubmitedItemVO> convertSubmit2VO(List<Submit> submits) {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        List<SubmitedItemVO> submitedItemVOS = new ArrayList<>();
        submits.stream().forEach(s -> {
            SubmitedItemVO submitedItemVO = SubmitedItemVO.builder()
                    .id(s.getId())
                    .problemid(s.getProblemid())
                    .submitedTime(dateFmt.format(s.getSubmitedTime()))
                    .result(s.getResult())
                    .build();
            submitedItemVOS.add(submitedItemVO);
        });
        return submitedItemVOS;
    }
}
