package ma.ensao.backend_pfa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;


@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name= "competences", nullable = true)
    private String competences;

    @Column(name= "annee", nullable = true)
    private String annee;

    @PrePersist
    public void setDefaultAnnee() {
        if (this.annee == null) {
            int annee_actuel = LocalDate.now().getYear();
            int mois_actuel = LocalDate.now().getMonthValue();

            if (mois_actuel >= 1 && mois_actuel <= 5) { // Janvier à Mai → annee actuel
                this.annee = (annee_actuel - 1) + "/" + annee_actuel;
            } else {
                this.annee = (annee_actuel - 2 ) + "/" + (annee_actuel - 1); // Juin à Décembre → anne precedante
            }
        }
    }

    //pour entrer un date par default qui est lanné actuel ,

    @Column(name="statut")
    private String statut;


    @Column(name = "rapport_url",nullable = true)
    private String rapportUrl;


    @Column(name= "enabled_project",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean enabledProject = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + titre + "', description='" + description + "'}"+ user;
    }


    @OneToOne(mappedBy = "project")
    private Groupe groupe;


}
