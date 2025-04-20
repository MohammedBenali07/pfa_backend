package ma.ensao.backend_pfa.service.csvlist;

import ma.ensao.backend_pfa.entity.Role;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.enums.RoleType;
import ma.ensao.backend_pfa.repository.RoleRepository;
import ma.ensao.backend_pfa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
    public class EleveImportService {
/*
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        public EleveImportService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.roleRepository = roleRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public void importFromCSV(MultipartFile file) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // ignore header
                        continue;
                    }
                    //hna l ficheier csv ghadi fach yt9ra ywli ligne fiha kola champs mfr9 3la lakhor b ,
                    //mabghitch npushi 7ta nverifi wach khdama
                    String[] parts = line.split(",");

                    if (parts.length < 6) continue; // skip invalid rows

                    User user = new User();
                    user.setFirstName(parts[0].trim());
                    user.setLastName(parts[1].trim());
                    user.setEmail(parts[2].trim());
                    user.setPassword(passwordEncoder.encode(parts[3].trim()));
                    user.setCin(parts[4].trim());
                    user.setBranch(parts[5].trim());
                    user.setEnabled(true);


                    Role userRole = new Role();
                    userRole.setName(RoleType.USER);

                    user.setRole(userRole);




                    if (!userRepository.existsByEmail(user.getEmail())) {
                        userRepository.save(user);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("Erreur de lecture du fichier CSV", e);
            }
        }

 */
    }


