package com.tessi.kyc.document.repository;

import com.tessi.kyc.document.entity.Document;
import com.tessi.kyc.document.entity.Folder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {

    Document findByUuid(String uuid);
}
