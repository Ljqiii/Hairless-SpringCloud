package com.ljqiii.hairlessmain.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewFavoriteFolderForm {

    @NotEmpty(message = "名称不能为空")
    String folderName;
    Boolean isPublic;
}
