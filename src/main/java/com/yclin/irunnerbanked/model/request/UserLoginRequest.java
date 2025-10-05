package com.yclin.irunnerbanked.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 3127475616380040292L;
    private String userAccount;

    private String userPassword;

}
