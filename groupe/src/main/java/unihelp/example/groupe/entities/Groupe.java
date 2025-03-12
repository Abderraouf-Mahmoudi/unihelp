package unihelp.example.groupe.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Groupe
{
    @Id
    @GeneratedValue()
    private int groupeid;
    private String name;
    private String description;
    private Date datecreated;
    @ManyToOne
    private Reunion reunion;
    private Long userId;
}
