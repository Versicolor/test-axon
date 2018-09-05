package com.tessi.kyc.document.controller.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class DocumentDto {

    private UUID folderId;

    private UUID documentTypeId;

    private String name;

}
