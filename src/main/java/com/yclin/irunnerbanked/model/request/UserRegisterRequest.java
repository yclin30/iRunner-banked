package com.yclin.irunnerbanked.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest  implements Serializable {


    private static final long serialVersionUID = 7989660491247955119L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
