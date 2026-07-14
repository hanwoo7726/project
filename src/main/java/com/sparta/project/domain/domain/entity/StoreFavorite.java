package com.sparta.project.domain.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(
    name = "p_store_favorite",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_p_store_favorite_user_store",
            columnNames = {"user_id", "store_id"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreFavorite {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @JoinColumn(name = "user_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @JoinColumn(name = "store_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Store store;

  private StoreFavorite(User user, Store store) {
    this.user = user;
    this.store = store;
  }

  public static StoreFavorite create(User user, Store store) {
    return new StoreFavorite(user, store);
  }


}
