package com.gdantimi.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SECTORS")
public class Sector {

    @GeneratedValue
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_SECTOR_ID")
    private Long parentSectorId;

    @OneToMany(mappedBy = "parentSectorId")
    private List<Sector> children;

}
