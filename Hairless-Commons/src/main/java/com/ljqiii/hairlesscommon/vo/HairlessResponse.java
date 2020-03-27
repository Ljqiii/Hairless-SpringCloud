package com.ljqiii.hairlesscommon.vo;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairlessResponse<T> {
    Integer code;
    String msg;
    T data;

    public void setCodeMsg(ResultEnum resultEnum) {
        code = resultEnum.getCode();
        msg = resultEnum.getMessage();
    }

    public boolean hasError() {
        return !this.msg.equals("success");
    }

    public void ok(){
        this.code=ResultEnum.OK.getCode();
        this.msg=ResultEnum.OK.getMessage();
    }

}
