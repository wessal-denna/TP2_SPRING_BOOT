package ma.ensaf.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ma.ensaf.model.Role;
import ma.ensaf.model.User;
import ma.ensaf.model.Medicament;
import ma.ensaf.repository.RoleRepository;
import ma.ensaf.repository.UserRepository;
import ma.ensaf.repository.MedicamentRepository;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // 1. Création des rôles s'ils n'existent pas
        Role roleAdmin = createRoleIfNotExists("ADMIN");
        Role rolePharmacien = createRoleIfNotExists("PHARMACIEN");
        Role roleCaissier = createRoleIfNotExists("CAISSIER");
        createRoleIfNotExists("CLIENT");

        // 2. Création des utilisateurs de test s'ils n'existent pas
        createUserIfNotExists("admin", "admin123", roleAdmin);
        createUserIfNotExists("pharma", "pharma123", rolePharmacien);
        createUserIfNotExists("caisse", "caisse123", roleCaissier);

        // 3. Insertion de médicaments de test si la table est vide
        if (medicamentRepository.count() == 0) {
            medicamentRepository.save(new Medicament("Doliprane 1000mg", "Antalgique et antipyrétique", 14.50, 120, LocalDate.of(2027, 6, 30)));
            medicamentRepository.save(new Medicament("Amoxicilline 500mg", "Antibiotique", 32.00, 60, LocalDate.of(2026, 12, 31)));
            medicamentRepository.save(new Medicament("Ventoline", "Bronchodilatateur", 45.75, 35, LocalDate.of(2027, 3, 15)));
            medicamentRepository.save(new Medicament("Spasfon", "Antispasmodique", 21.00, 80, LocalDate.of(2028, 1, 10)));
        }

        System.out.println(">>> InitData : rôles, utilisateurs et médicaments de test vérifiés / créés.");
    }

    private Role createRoleIfNotExists(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(name)));
    }

    private void createUserIfNotExists(String username, String rawPassword, Role role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setEnabled(true);
            user.setRoles(roles);

            userRepository.save(user);
        }
    }
}
