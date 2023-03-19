package com.studydemo.demo.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Description
 * @Author teronb
 * @Date 2023/3/8 8:35
 */
@Data
public class UserTestBo implements Serializable {


    private static final long serialVersionUID = -3403860147993710064L;
    private int id;

    private String username;

    private String password;

    private LocalDate birthday;

    @Override
    public String toString() {
        return "UserTestBo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
