package com.tessi.kyc.control.query.repository;

import com.tessi.kyc.control.query.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    Document findByDocumentId(UUID documentId);
}
