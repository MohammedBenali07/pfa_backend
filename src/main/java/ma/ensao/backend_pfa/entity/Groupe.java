package ma.ensao.backend_pfa.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groupe")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Groupe  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nom;
    private String subject;


    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(
            mappedBy = "groupe",
            cascade = CascadeType.MERGE, // Only merge (no persist/delete)
            orphanRemoval = false // Optional: delete users when removed from list
    )
    private List<User> users = new ArrayList<>();
    public void addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            user.setGroupe(this);
        }
    }

    public void removeUser(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
            user.setGroupe(null);
        }
    }
    @OneToOne
    @JoinColumn(name = "representant_id")
    private User representant;// The representative of this group


    // Validation to ensure representant is in users list
    @PrePersist //Exécuté avant qu'un nouveau groupe ne soit sauvegardé dans la base.
    @PreUpdate  //Exécuté avant qu'un groupe existant ne soit mis à jour.
    private void validateRepresentative() {
        if (representant != null && !users.contains(representant)) {
            throw new IllegalStateException("Representative must be a group member");
        }
    }
}





