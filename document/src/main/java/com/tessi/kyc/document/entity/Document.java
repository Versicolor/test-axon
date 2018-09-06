package com.tessi.kyc.document.entity;

import lombok.Data;
import lombok.Value;
import org.axonframework.commandhandling.model.EntityId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String folderId;

    private String documentTypeId;

    private Date dateCreate;

    private String name;
}
