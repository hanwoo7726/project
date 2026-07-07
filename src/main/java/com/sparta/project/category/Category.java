package com.sparta.project.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_category") // 데이터베이스에 생성될 테이블 이름
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 카테고리 고유 식별 번호 (1, 2, 3...)

    @Column(name = "food_category", nullable = false, length = 50)
    private String foodCategory; // 카테고리 이름 (예: 치킨, 피자)

    @Column(name = "category_image_url", length = 255)
    private String categoryImageUrl; // 카테고리 아이콘 이미지 주소

    @Column(name = "category_is_visible", nullable = false)
    private boolean categoryIsVisible = true; // 화면 노출 여부 (기본값 활성화)

    // 새로운 카테고리를 등록할 때 쓸 생성자
    public Category(String foodCategory, String categoryImageUrl) {
        this.foodCategory = foodCategory;
        this.categoryImageUrl = categoryImageUrl;
        this.categoryIsVisible = true;
    }
}