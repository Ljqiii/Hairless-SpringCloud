package com.ljqiii.hairlessmain.form;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class AddFavoriteProblemForm {

    @NotEmpty(message = "问题不能为空")
    Integer problemid;

    ArrayList<Integer> favoriteFolderList;

}
