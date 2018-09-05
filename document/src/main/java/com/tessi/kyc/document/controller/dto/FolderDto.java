package com.tessi.kyc.document.controller.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class FolderDto {

    private UUID folderTypeId;

    private long sleep;

}
