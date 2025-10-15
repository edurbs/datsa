package com.github.edurbs.datsa.api.v1.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserPasswordInput  {

    @Schema(example = "OldBadPassword")
    @NotBlank
    private String oldPassword;

    @Schema(example = "NewGoodPassword")
    @Size(min = 8)
    private String newPassword;
}
