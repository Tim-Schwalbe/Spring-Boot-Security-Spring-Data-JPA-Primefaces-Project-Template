package de.tim.schwalbe.web.service;

import de.tim.schwalbe.web.model.profiles.Profile;
import de.tim.schwalbe.web.model.profiles.ProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @author tim schwalbe
 */
@Repository
public class ProfileService {

        static Logger logger = Logger.getLogger(ProfileService.class);

        @PersistenceContext
        EntityManager em;

        @Autowired
        ProfileRepository profileRepository;

        public synchronized Profile findUserByEmail(String email) {
                Profile user = null;

                try {
                        user = profileRepository.findByEmail(email);
                } catch (NoResultException e) {
                        logger.warn("No user found with email: " + email);
                }
                return user;
        }

        public synchronized Profile findByUsername(String username) {
                Profile user = null;

                try {

                        user = profileRepository.findByUsername(username);
                } catch (NoResultException e) {
                        logger.warn("No user found with username: " + username);
                }
                return user;
        }

        public synchronized List<Profile> getAllProfiles() {
                List<Profile> userList;
                userList = profileRepository.findAll();
                
                return userList;
        }
        //
        //        @Transactional(readOnly = true)
        //        public synchronized List<Profile> getAllUsersWithRole(String role) {
        //                List<Profile> userList = new BigList<>();
        //
        //                int roleId = Role.roleNameToCode(role);
        //
        //                if (roleId > 0) {
        //                        Query query = (Query) em.createNamedQuery("Profile.findAllWithRole", Profile.class).setParameter("role", roleId);
        //                        userList.addAll(query.getResultList());
        //                }
        //
        //                return userList;
        //        }

        public synchronized Profile findProfileById(Long id) {
                Profile profile = null;

                try {

                        profile = profileRepository.findById(id);
                } catch (NoResultException e) {
                        logger.warn("No profile found with id: " + id);
                }
                return profile;
        }

        @Transactional
        public synchronized Profile createProfile(String username, String password, String firstName, String lastName, String email, String uuid) {
                Profile profile = null;

                profile = new Profile(username, password, firstName, lastName, email);
                profile.setUuid(uuid);
                em.persist(profile);
                //
                //                if (!isEmailRegistered(email) && !isUsernameRegistered(username)) {
                //                        profile = new Profile(username, password, firstName, lastName, email);
                //                        profile.setUuid(uuid);
                //                        em.persist(profile);
                //                }
                return profile;
        }

        //
        //        private boolean isUsernameRegistered(String username) {
        //                if (findByUsername(username) == null) {
        //                        return false;
        //                } else {
        //                        return true;
        //                }
        //        }
        //
        //        private boolean isEmailRegistered(String email) {
        //                if (findUserByEmail(email) == null) {
        //                        return false;
        //                } else {
        //                        return true;
        //                }
        //        }
        //

        public synchronized void updatePassword(Profile profile, String password) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(password);

                if (profile != null && em.contains(profile)) {
                        if (StringUtils.isNotBlank(hashedPassword)) {
                                profile.setPassword(hashedPassword);

                                em.merge(profile);
                        } else {
                                this.logger.warn("Password not specified.");
                        }
                } else {
                        this.logger.warn("Given Profile does not exist.");
                }
        }

        public synchronized void remove(Long profileId) {

                Profile profile = profileRepository.findById(profileId);

                if (profile != null && !profile.getUsername().equals("admin")) {
                        em.remove(profile);
                } else {
                        logger.error("Tried to deactivate user, but given is null or admin");
                }
        }

        //
        //        @Transactional
        //        public synchronized void deleteRole(Role role) {
        //                logger.info("Delete Role " + role);
        //
        //                if (role != null) {
        //                        logger.info("Role found in DB for user: " + role.getProfile().getUsername() + " and role: " + role.getName());
        //
        //                        em.remove(role);
        //                } else {
        //                        logger.error("Tried to deactivate user, but given is null or admin");
        //                }
        //        }
        //
        //        @Transactional(readOnly = true)
        //        public synchronized List<Profile> findAllProfilesByCreationDate(Timestamp createdAt) {
        //                List<Profile> profiles = null;
        //                try {
        //                        Query query = em.createNamedQuery("Profile.findAllProfilesByCreationDate", Profile.class);
        //                        profiles = (List<Profile>) query.setParameter("createdAt", createdAt).getResultList();
        //                } catch (NoResultException e) {
        //                        logger.warn("No profiles found.");
        //                }
        //                return profiles;
        //        }
        //
        //        @Transactional(readOnly = true)
        //        public synchronized List<Profile> findAllProfilesByUpdateDate(Timestamp updatedAt) {
        //                List<Profile> profiles = null;
        //
        //                try {
        //                        Query query = em.createNamedQuery("Profile.findAllProfilesByUpdateDate", Profile.class);
        //                        profiles = (List<Profile>) query.setParameter("updatedAt", updatedAt).getResultList();
        //                } catch (NoResultException e) {
        //                        logger.warn("No profiles found.");
        //                }
        //
        //                return profiles;
        //        }
        //
        @Transactional
        public synchronized void persist(Profile profile) {
                if (profile != null) {
                        em.persist(profile);
                }
        }

        //
        @Transactional
        public synchronized void update(Profile profile) {
                if (profile != null) {
                        Profile p = profileRepository.findById(profile.getId());
                        if (p != null) {
                                profile.setUpdatedAt();
                                em.merge(profile);
                        }
                } else {
                        logger.warn("Profile does not exist.");
                }
        }

        //
        @Transactional
        public String passwordResetToken(Profile profile) {
                String resetToken = UUID.randomUUID().toString();

                profile.setPasswordResetToken(resetToken);
                profile.setPasswordResetExpiry(this.getTimestampPlusDays(1));
                em.persist(profile);
                return resetToken;
        }

        //
        private Timestamp getTimestampPlusDays(int daysToAdd) {
                Timestamp timestamp = Timestamp.from(Instant.now());
                Calendar cal = Calendar.getInstance();
                cal.setTime(timestamp);
                cal.add(Calendar.DAY_OF_WEEK, daysToAdd);
                timestamp.setTime(cal.getTime().getTime());
                return timestamp;
        }

        //
        @Transactional(readOnly = true)
        public boolean checkOldPassword(Profile profile, String oldPassword) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                if (profile != null && passwordEncoder.matches(oldPassword, profile.getPassword())) {
                        return true;
                }

                return false;
        }
        //
        //        @Transactional(readOnly = true)
        //        public void refresh(Profile profile) {
        //                if (em.contains(profile)) {
        //                        em.refresh(profile);
        //                } else {
        //                        profile = em.find(Profile.class, profile.getId());
        //                }
        //        }
}