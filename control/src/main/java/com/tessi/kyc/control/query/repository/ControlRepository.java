package com.tessi.kyc.control.query.repository;

import com.tessi.kyc.control.query.entity.Control;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ControlRepository extends JpaRepository<Control, UUID> {
}
