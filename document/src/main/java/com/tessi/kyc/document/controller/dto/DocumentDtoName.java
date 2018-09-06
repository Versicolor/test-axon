package com.tessi.kyc.document.controller.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class DocumentDtoName {

    private UUID folderId;

    private UUID id;

    private String name;
}
