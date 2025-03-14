package unihelp.example.groupe.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int reunionid;
    private String name;
    private Date date;
    private String objectif;
    private int groupeid;
    private String partipant;
    @OneToMany(mappedBy = "reunion")
    private List<Groupe> groupes;
    @OneToMany(mappedBy = "reunion")
    private List<User>users;
}
