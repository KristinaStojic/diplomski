package com.example.posta.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "email", unique = true, nullable = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

//    @OneToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
//    private Address address;

    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    public User() {
        super();
    }

    public void setPassword(String password) {

        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> collection = new ArrayList<>();
        collection.add(this.role);
        return collection;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    public User(String password, String email, String name, String surname, String phoneNumber) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.deleted = false;
    }

    public User(User u){
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.name = u.getName();
        this.surname = u.getSurname();
        this.phoneNumber = u.getPhoneNumber();
        this.deleted = u.isDeleted();
        this.enabled = u.isEnabled();
    }
}
