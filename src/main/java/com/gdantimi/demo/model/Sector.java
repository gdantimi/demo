package com.gdantimi.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


}
