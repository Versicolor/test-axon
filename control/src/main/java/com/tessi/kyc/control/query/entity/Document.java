package com.tessi.kyc.control.query.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID documentId;
    private UUID documentTypeId;

    @OneToMany
    private List<Field> fields;
}
