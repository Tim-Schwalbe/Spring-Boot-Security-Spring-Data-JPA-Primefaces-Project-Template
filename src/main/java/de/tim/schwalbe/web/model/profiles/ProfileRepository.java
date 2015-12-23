package de.tim.schwalbe.web.model.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tim.schwalbe on 21.12.2015.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

        @Transactional
        List<Profile> findByEmailAndLastName(String email, String lastName);

        @Transactional
        Profile findByUsername(String username);

        @Transactional
        Profile findByEmail(String email);


        @Transactional
        Profile findById(Long id);

        @Transactional
        List<Profile> findAll();
}
