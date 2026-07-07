
CREATE TABLE address
(
  id                BIGINT       NOT NULL DEFAULT AUTO_INCREMNT,
  address_name      VARCHAR(100) NOT NULL,
  postal_code       VARCHAR(100) NOT NULL,
  address           VARCHAR(100) NOT NULL,
  detail_address    VARCHAR(100) NOT NULL,
  delevery_request  VARCHAR(100) NOT NULL,
  entrance_password VARCHAR(100),
  is_default        BOOLEAN     ,
  created_at        TIMESTAMP    NOT NULL,
  created_by        VARCHAR(100),
  updated_at        TIMESTAMP   ,
  updated_by        VARCHAR(100),
  deleted_at        TIMESTAMP   ,
  deleted_by        VARCHAR(100),
  user_id           BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE address IS '배송지';

COMMENT ON COLUMN address.id IS '배송ID';

COMMENT ON COLUMN address.user_id IS '회원ID';

CREATE TABLE admin
(
  id           BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  target_table        NOT NULL,
  target_id           NOT NULL,
  action_type         NOT NULL,
  reason             ,
  created_at          NOT NULL,
  created_by          NOT NULL,
  user_id      BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE admin IS '관리자';

COMMENT ON COLUMN admin.id IS '관리자ID';

COMMENT ON COLUMN admin.user_id IS '회원ID';

CREATE TABLE ai_log
(
  id               BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  request_prompt         ,
  response_content       ,
  response_content       ,
  status                 ,
  created_at             ,
  created_by             ,
  product_id       BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE ai_log IS 'AI';

COMMENT ON COLUMN ai_log.id IS 'AI ID';

COMMENT ON COLUMN ai_log.product_id IS '상품ID';

CREATE TABLE cart
(
  id         BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  created_at TIMESTAMP   ,
  created_by VARCHAR(100),
  user_id    BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  store_id   BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE cart IS '카트';

COMMENT ON COLUMN cart.user_id IS '회원ID';

COMMENT ON COLUMN cart.store_id IS '가게ID';

CREATE TABLE order
(
  id               BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  oder_no          VARCHAR(100) NOT NULL,
  customer_id      BIGINT      ,
  restaurant_id    BIGINT      ,
  menu_name        VARCHAR     ,
  delivery_address VARCHAR     ,
  quantity         INT         ,
  total_price      INT         ,
  request_message  VARCHAR(100),
  order_status     ENUM        ,
  created_at       TIMESTAMP   ,
  created_by       VARCHAR(100),
  updated_at       TIMESTAMP   ,
  updated_by       VARCHAR     ,
  user_id          BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  address_id       BIGINT       NOT NULL DEFAULT AUTO_INCREMNT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE order IS '주문';

COMMENT ON COLUMN order.id IS '주문ID';

COMMENT ON COLUMN order.user_id IS '회원ID';

COMMENT ON COLUMN order.address_id IS '배송ID';

CREATE TABLE order_status_history
(
  id           BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  order_number       ,
  order_status       ,
  etc                ,
  created_by         ,
  created_at         ,
                     ,
  PRIMARY KEY (id)
);

COMMENT ON TABLE order_status_history IS '주문 내역';

COMMENT ON COLUMN order_status_history.id IS '주문 내역 ID';

CREATE TABLE payment
(
  id                BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  payment_method          ,
  payment_status          ,
  amount                  ,
  pg_provider             ,
  pg_transaction_id       ,
  approved_at             ,
  canceled_at             ,
  created_at              ,
  created_by              ,
  updated_at              ,
  updated_by              ,
  user_id           BIGINT NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE payment IS '결제';

COMMENT ON COLUMN payment.id IS '결제 ID';

COMMENT ON COLUMN payment.user_id IS '회원ID';

CREATE TABLE product
(
  id           BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  store_id     INT          NOT NULL,
  pro_name     VARCHAR(100) NOT NULL,
  pro_descript VARCHAR(100),
  price        INT          NOT NULL,
  is_hidden    BOOLEAN      NOT NULL,
  created_at   TIMESTAMP    NOT NULL,
  created_by   VARCHAR(100) NOT NULL,
  updated_at   TIMESTAMP   ,
  updated_by   VARCHAR(100),
  deleted_at   TIMESTAMP   ,
  deleted_by   VARCHAR(100),
  id           BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE product IS '상품';

COMMENT ON COLUMN product.id IS '상품ID';

COMMENT ON COLUMN product.id IS 'AI ID';

CREATE TABLE store
(
  id                 BIGINT  NOT NULL DEFAULT AUTO_INCREMENT,
  owner              VARCHAR NOT NULL,
  name               VARCHAR,
  password           VARCHAR,
  category                  ,
  address                   ,
  phone                     ,
  opentime                  ,
  closetime                 ,
  minimum_orderprice        ,
  delivery_fee              ,
  avarage_rating            ,
  review_count              ,
  created_at                ,
  updated_at                ,
  deleted_at                ,
  PRIMARY KEY (id)
);

COMMENT ON TABLE store IS '스토어';

COMMENT ON COLUMN store.id IS '가게ID';

CREATE TABLE store_favorite
(
  id         BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  created_at TIMESTAMP   ,
  created_by VARCHAR(100),
  updated_at TIMESTAMP   ,
  updated_by VARCHAR(100),
  deleted_at TIMESTAMP   ,
  deleted_by VARCHAR(100),
  user_id    BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  store_id   BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE store_favorite IS '스토어 찜';

COMMENT ON COLUMN store_favorite.id IS '찜 ID';

COMMENT ON COLUMN store_favorite.user_id IS '회원ID';

COMMENT ON COLUMN store_favorite.store_id IS '가게ID';

CREATE TABLE user
(
  id         BIGINT       NOT NULL DEFAULT AUTO_INCREMENT,
  username   VARCHAR(50)  NOT NULL,
  password   VARCHAR(100) NOT NULL,
  nickname   VARCHAR(100) NOT NULL,
  phone      VARCHAR(100) NOT NULL,
  created_at DATETIME     NOT NULL,
  updated_at DATETIME    ,
  updated_by VARCHAR(100),
  deleted_at DATETIME    ,
  deleted_by VARCHAR(100),
  role       ENUM         NOT NULL,
  address_id BIGINT       NOT NULL DEFAULT AUTO_INCREMNT,
  PRIMARY KEY (id)
);

COMMENT ON TABLE user IS '사용자';

COMMENT ON COLUMN user.id IS '회원ID';

COMMENT ON COLUMN user.address_id IS '배송ID';

ALTER TABLE order
  ADD CONSTRAINT FK_user_TO_order
    FOREIGN KEY (user_id)
    REFERENCES user (id);

ALTER TABLE address
  ADD CONSTRAINT FK_user_TO_address
    FOREIGN KEY (user_id)
    REFERENCES user (id);

ALTER TABLE order
  ADD CONSTRAINT FK_address_TO_order
    FOREIGN KEY (address_id)
    REFERENCES address (id);

ALTER TABLE ai_log
  ADD CONSTRAINT FK_product_TO_ai_log
    FOREIGN KEY (product_id)
    REFERENCES product (id);

ALTER TABLE cart
  ADD CONSTRAINT FK_user_TO_cart
    FOREIGN KEY (user_id)
    REFERENCES user (id);

ALTER TABLE cart
  ADD CONSTRAINT FK_store_TO_cart
    FOREIGN KEY (store_id)
    REFERENCES store (id);

ALTER TABLE store_favorite
  ADD CONSTRAINT FK_user_TO_store_favorite
    FOREIGN KEY (user_id)
    REFERENCES user (id);

ALTER TABLE store_favorite
  ADD CONSTRAINT FK_store_TO_store_favorite
    FOREIGN KEY (store_id)
    REFERENCES store (id);

ALTER TABLE admin
  ADD CONSTRAINT FK_user_TO_admin
    FOREIGN KEY (user_id)
    REFERENCES user (id);

ALTER TABLE payment
  ADD CONSTRAINT FK_user_TO_payment
    FOREIGN KEY (user_id)
    REFERENCES user (id);
