package docGenerate.Doc.models;


import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Template> templates = new HashSet<>(); // Связь один ко многим с шаблонами

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
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

    public void userUpdate(String username, String name) {
        if(username!=null)
            this.username = username;
        if(name!=null)
            this.name = name;

    }

    // ---------------------- Гетеры и сетеры ------------------------

    public void plusDownload(){
        countDownload++;
    }

    // Геттер и сеттер для связи с шаблонами

    public int GetCountTemplates(){
        return templates.size();
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    // Метод для добавления шаблона к пользователю
    public void addTemplate(Template template) {
        template.setUser(this);
        templates.add(template);
    }

    // Метод для удаления шаблона у пользователя
    public void removeTemplate(Template template) {
        templates.remove(template);
        template.setUser(null);
    }


    public void addRole(Role role) {
        roles.add(role);
    }
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
