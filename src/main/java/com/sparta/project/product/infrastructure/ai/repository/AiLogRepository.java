package com.sparta.project.product.infrastructure.ai.repository;

import com.sparta.project.product.infrastructure.ai.entity.AiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiLogRepository extends JpaRepository<AiLog, UUID> {
}
