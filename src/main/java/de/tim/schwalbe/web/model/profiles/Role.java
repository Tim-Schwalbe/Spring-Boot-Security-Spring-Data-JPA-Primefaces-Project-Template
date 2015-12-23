package de.tim.schwalbe.web.model.profiles;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Tim Schwalbe
 */
@Embeddable
@Entity(name = "role")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "profile_id", "role" }) })
public class Role implements GrantedAuthority {

        public static final String ROLE_ANONYMOUS_STRING = "ROLE_ANONYMOUS";
        public static final String ROLE_USER_STRING = "ROLE_USER";
        public static final String ROLE_PROFILE_STRING = "ROLE_PROFILE";
        public static final String ROLE_ADMIN_STRING = "ROLE_ADMIN";
        public static final String ROLE_SUPERADMIN_STRING = "ROLE_SUPERADMIN";
        public static final String ROLE_DOCTOR_STRING = "ROLE_DOCTOR";
        public static final String ROLE_PATIENT_STRING = "ROLE_PATIENT";

        public static final int ROLE_ANONYMOUS_CODE = 0;
        public static final int ROLE_DOCTOR_CODE = 1;
        public static final int ROLE_PATIENT_CODE = 2;
        public static final int ROLE_PROFILE_CODE = 3;
        public static final int ROLE_ADMIN_CODE = 4;
        public static final int ROLE_SUPERADMIN_CODE = 5;

//        public static final VisibilityType ROLE_ANONYMOUS_TYPE = VisibilityType.ANONYMOUS;
//        public static final VisibilityType ROLE_PROFILE_TYPE = VisibilityType.PROFILE;
//        public static final VisibilityType ROLE_ADMIN_TYPE = VisibilityType.ADMIN;
//        public static final VisibilityType ROLE_SUPERADMIN_TYPE = VisibilityType.SUPERADMIN;

        private int role;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Profile profile;

        public Role(String roleName) {
                this.role = roleNameToCode(roleName);
        }

        public Role() {
        }

        public Role(int roleCode) {
                this.role = roleCode;
        }

        public static String roleCodeToName(int code) {
                switch (code) {

                case ROLE_PROFILE_CODE:
                        return ROLE_PROFILE_STRING;
                case ROLE_ADMIN_CODE:
                        return ROLE_ADMIN_STRING;
                case ROLE_SUPERADMIN_CODE:
                        return ROLE_SUPERADMIN_STRING;
                case ROLE_DOCTOR_CODE:
                        return ROLE_DOCTOR_STRING;
                case ROLE_PATIENT_CODE:
                        return ROLE_PATIENT_STRING;
                default:
                        return ROLE_ANONYMOUS_STRING;
                }
        }

        public static int roleNameToCode(String roleName) {
                switch (roleName) {
                case ROLE_PROFILE_STRING:
                        return ROLE_PROFILE_CODE;
                case ROLE_ADMIN_STRING:
                        return ROLE_ADMIN_CODE;
                case ROLE_SUPERADMIN_STRING:
                        return ROLE_SUPERADMIN_CODE;
                case ROLE_DOCTOR_STRING:
                        return ROLE_DOCTOR_CODE;
                case ROLE_PATIENT_STRING:
                        return ROLE_PATIENT_CODE;
                default:
                        return ROLE_ANONYMOUS_CODE;
                }
        }

        public int getRole() {
                return role;
        }

        public void setRole(int role) {
                this.role = role;
        }

        public String getName() {
                return roleCodeToName(this.role);
        }

        @Override
        public String getAuthority() {
                return this.getName();
        }

        public Profile getProfile() {
                return profile;
        }

        public void setProfile(Profile profile) {
                this.profile = profile;
        }

        public Long getId() {
                return id;
        }

        @Override
        public boolean equals(Object other) {
                if (other == null)
                        return false;
                if (other == this)
                        return true;
                if (!(other instanceof Role))
                        return false;
                Role otherRole = (Role) other;
                return (otherRole.getRole() == this.role && otherRole.getProfile().getId() == this.profile.getId());
        }
}

