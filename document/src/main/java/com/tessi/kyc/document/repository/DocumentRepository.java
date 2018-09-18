package com.tessi.kyc.document.repository;

import com.tessi.kyc.document.entity.Document;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

    Document findByDocumentId(String documentId);
}
