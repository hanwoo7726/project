package com.sparta.project.product.ai.repository;

import com.sparta.project.product.ai.entity.AiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiLogRepository extends JpaRepository<AiLog, UUID> {
}
