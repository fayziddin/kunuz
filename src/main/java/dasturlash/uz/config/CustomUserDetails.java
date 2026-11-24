package dasturlash.uz.config;

import dasturlash.uz.entity.ProfileRoleEntity;
import dasturlash.uz.enums.ProfileRoleEnum;
import dasturlash.uz.enums.ProfileStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private ProfileStatusEnum status;
    private List<ProfileRoleEntity> profileRoleList;

    public CustomUserDetails(Long id, String name, String surname, String username, String password, ProfileStatusEnum status, List<ProfileRoleEntity> profileRoleList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.status = status;
        this.profileRoleList = profileRoleList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (ProfileRoleEntity role : this.profileRoleList) {
            roles.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return roles;
    }


    @Override
    public boolean isAccountNonLocked() {
        return this.status.equals(ProfileStatusEnum.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ProfileStatusEnum getStatus() {
        return status;
    }

    public List<ProfileRoleEnum> getRoleList() {
        List<ProfileRoleEnum> roles = new ArrayList<>();
        for (ProfileRoleEntity profileRole : this.profileRoleList) {
            roles.add(profileRole.getRole());
        }
        return roles;
    }

    public Long getId() {
        return id;
    }
}
