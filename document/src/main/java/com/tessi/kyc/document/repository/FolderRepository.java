package com.tessi.kyc.document.repository;

import com.tessi.kyc.document.entity.Folder;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FolderRepository extends PagingAndSortingRepository<Folder, Long> {

    Folder findByUuid(String uuid);
}
