package docGenerate.Doc.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Entity
@Table(name="t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;



    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "count_download")
    private int countDownload;


    @Transient
    private String confirmPassword; // Для подтверждения пароля
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Здесь вы можете предоставить роли (GrantedAuthority) для пользователя
        // Например, можно использовать Set<GrantedAuthority> для хранения ролей пользователя
        // И вернуть их здесь
        return getRoles();
    }

    //------------------ Другие методы UserDetails -----------------------------------
    @Override
    public boolean isAccountNonExpired() {
        return true; // Здесь можно реализовать логику проверки срока действия учетной записи
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; // Здесь можно реализовать логику проверки блокировки учетной записи
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Здесь можно реализовать логику проверки срока действия учетных данных
    }
    @Override
    public boolean isEnabled() {
        return true; // Здесь можно реализовать логику проверки активности учетной записи
    }

    // ---------------------- Констуркторы ------------------------
    public User() {}

    // ---------------------- Гетеры и сетеры ------------------------

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCountDownload() {
        return countDownload;
    }
    public void setCountDownload(int countDownload) {
        this.countDownload = countDownload;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
