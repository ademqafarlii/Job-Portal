package com.example.ingressjobs.model;


import com.example.ingressjobs.enums.WorkType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String street;
    private String city;
    private Integer postalCode;

    @Enumerated(EnumType.STRING)
    private WorkType workType;
}
