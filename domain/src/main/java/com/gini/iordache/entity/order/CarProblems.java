package com.gini.iordache.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class CarProblems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Column(name = "car_problems")
    private String problems;


    @Override
    public String toString() {
        return problems;

    }
}
