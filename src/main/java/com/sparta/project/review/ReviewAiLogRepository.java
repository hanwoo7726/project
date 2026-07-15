package com.sparta.project.review;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewAiLogRepository extends JpaRepository<ReviewAiLog, UUID> {
}