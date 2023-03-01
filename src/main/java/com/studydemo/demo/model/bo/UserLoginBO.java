package com.studydemo.demo.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author teronb
 * @Date 2023/1/30 0:48
 */

@Data
@ApiModel("用户详情BO类")
public class UserLoginBO implements UserDetails {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 8073662434406951441L;

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    @ApiModelProperty(value = "用户信息")
    private UserDetailBO userDetailBO;

    @ApiModelProperty(value = "token")
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

//    @ApiModelProperty(value = "token时效")
//    private Date tokenTime;
}
