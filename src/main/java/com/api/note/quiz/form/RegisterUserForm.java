package com.api.note.quiz.form;

import java.io.Serializable;

import com.api.note.quiz.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * TODO AccountCreateFormに統合
 */
@Data
public class RegisterUserForm implements Serializable {

    @JsonProperty("email")
    private String userMail;

    private String userName;

    private String password;

    private String userImgUrl;

    private UserType userType;

    private String thirdPartyId;

    private String userGender;
}
