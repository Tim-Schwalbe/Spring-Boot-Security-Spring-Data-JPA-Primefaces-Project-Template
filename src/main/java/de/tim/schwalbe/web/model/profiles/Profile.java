package de.tim.schwalbe.web.model.profiles;

import de.tim.schwalbe.web.model.AbstractModel;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author tim.schwalbe
 *         <p>
 *         Copyright 2015 mindbit GmbH, Inc. All rights reserved
 */
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "username" }) })
@Entity(name = "profile")
@Inheritance(strategy = InheritanceType.JOINED)


public class Profile extends AbstractModel implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        private Long id;

        private String profileType;

        private String passwordResetToken;
        private Timestamp passwordResetExpiry;

        private String firstName;
        private String lastName;
        private String email;
        private String avatar;

        private boolean enabled;
        @Transient
        private boolean hasRole;

        @Column(nullable = false,
                unique = true)
        private String username;
        private String password;

        private String uuid;

        @OneToMany(fetch = FetchType.EAGER,
                   cascade = { CascadeType.ALL },
                   orphanRemoval = true,
                   mappedBy = "profile",
                   targetEntity = Role.class)
        private Set<Role> roles = new HashSet<>();

        public Profile(String username,
                       String password,
                       boolean enabled,
                       boolean accountNonExpired,
                       boolean credentialsNonExpired,
                       boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {

                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);

                this.username = username;
                this.password = hashedPassword;
                this.enabled = enabled;

                this.setUpdatedAt();
                this.setCreatedAt();

                for (GrantedAuthority auth : authorities) {
                        this.roles.add(new Role(auth.getAuthority()));
                }
        }

        public Profile(String username, String password, String firstName, String lastName, String email) {
                this(username, password, false, true, true, true, new ArrayList<GrantedAuthority>());
                this.addRole(Role.ROLE_PROFILE_STRING);
                this.addRole(Role.ROLE_PATIENT_STRING);
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.uuid = uuid;
                this.setUpdatedAt();
                this.setCreatedAt();

        }

        public Profile(String username, String password, String firstName, String lastName, String email, String uuid) {
                this(username, password, false, true, true, true, new ArrayList<GrantedAuthority>());
                this.addRole(Role.ROLE_PROFILE_STRING);
                this.addRole(Role.ROLE_DOCTOR_STRING);
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;

                this.uuid = uuid;
                this.setUpdatedAt();
                this.setCreatedAt();
        }

        public Profile() {

        }

        public String getUuid() {
                return uuid;
        }

        public void setUuid(String uuid) {
                this.uuid = uuid;
        }

        @Override
        public Collection<GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                Set<Role> userRoles = this.getRoles();

                if (userRoles != null) {
                        for (Role role : userRoles) {
                                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                                authorities.add(authority);
                        }
                }
                return authorities;
        }

        public Long getId() {
                return id;
        }

        @Override
        public String getPassword() {
                return this.password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        @Override
        public String getUsername() {
                return this.username;
        }

        public void setUsername(String username) {
                this.username = username;
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
                return this.enabled;
        }

        public void setEnabled(boolean enabled) {
                this.enabled = enabled;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }

        public void removeRole(String role) {
                CopyOnWriteArrayList<Role> roleCopy = new CopyOnWriteArrayList<>(this.roles);

                this.getRoles().clear();

                for (Role existingRole : roleCopy) {
                        if (existingRole.getName().equals(role) && !(username.equalsIgnoreCase("superadmin") && existingRole.getName()
                                .equals(Role.ROLE_SUPERADMIN_STRING))) {
                                roleCopy.remove(existingRole);
                        }
                }
                this.getRoles().addAll(roleCopy);
        }

        public void addRole(String role) {
                if (!hasRole(role)) {
                        Role newRole = new Role(role);
                        newRole.setProfile(this);
                        if (newRole.getRole() != Role.ROLE_ANONYMOUS_CODE) {
                                this.roles.add(newRole);
                        }
                }
        }

        public boolean hasRole(String role) {
                for (Role existingRole : this.roles) {
                        if (existingRole.getName().equals(role)) {
                                return true;
                        }
                }

                return false;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        //JSF Role setter & Getter (Placebo)
        public boolean isHasRole() {
                return hasRole;
        }

        public void setHasRole(boolean hasRole) {
                this.hasRole = hasRole;
        }

        public String getAvatar() {
                return avatar;
        }

        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }

        public String getProfileType() {
                return profileType;
        }

        public void setProfileType(String profileType) {
                this.profileType = profileType;
        }

        /**
         * @return First and last name of user (without title).
         */
        public String getDisplayName() {
                return this.firstName + " " + this.lastName;
        }


        //        public String getAvatarPath() {
        //                return StringUtils.isNotBlank(this.avatar) ? UPLOAD_FOLDER + AVATAR_FOLDER + "/" + this.avatar : "/resources/img/empty_avatar.png";
        //        }

        public String getPasswordResetToken() {
                return passwordResetToken;
        }

        public void setPasswordResetToken(String passwordResetToken) {
                this.passwordResetToken = passwordResetToken;
        }

        public Timestamp getPasswordResetExpiry() {
                return passwordResetExpiry;
        }

        public void setPasswordResetExpiry(Timestamp passwordResetExpiry) {
                this.passwordResetExpiry = passwordResetExpiry;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o)
                        return true;
                if (o == null || getClass() != o.getClass())
                        return false;

                Profile profile = (Profile) o;

                if (id != null ? !id.equals(profile.id) : profile.id != null)
                        return false;

                return true;
        }

        @Override
        public int hashCode() {
                return id != null ? id.hashCode() : 0;
        }

        @Override
        public JSONObject toJson() {
                return null;
        }
}
