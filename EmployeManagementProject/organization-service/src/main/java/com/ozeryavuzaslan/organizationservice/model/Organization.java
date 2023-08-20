package com.ozeryavuzaslan.organizationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "organizations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"organizationCode"})
        },
        indexes = {
                @Index(name = "organization_code_index", columnList = "organizationCode"),
                @Index(name = "create_date_index", columnList = "createdDate")
        }
)
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false)
    private String organizationDescription;

    @Column(unique = true, nullable = false)
    private String organizationCode;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;
}
