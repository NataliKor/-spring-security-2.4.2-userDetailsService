package web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "The field must not be empty.")
    @Size(min = 2, max = 30, message = "The allowed number of characters is from 2 to 30.")
    private String username;

    @Column
    @NotEmpty(message = "The field must not be empty.")
    @Size(min = 2, max = 30, message = "The allowed number of characters is from 2 to 30.")
    private String lastName;

    @Column
    @Min(value = 0, message = "Age cannot be less than zero.")
    private int age;

    @Column
    @NotEmpty(message = "The field must not be empty.")
    @Size(min = 4, max = 30, message = "The allowed number of characters is from 4 to 30.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //FetchType.EAGER – «жадная» загрузка, т.е. список
    // ролей загружается вместе с пользователем сразу (не ждет пока к нему обратятся).
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String name, String lastName, int age, String password, Set<Role> roles) {
        this.username = name;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStringRoles(){
        String result = "";
        for (Role role: roles){
            result += role.getName() + " ";
        }
        return result;
    }
}
