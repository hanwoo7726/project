package com.sparta.project.entity;

public enum OrderStateEnum {
    PENDING(OrderState.PENDING),        // 주문 대기중
    ACCEPTED(OrderState.ACCEPTED),      // 주문 수락
    COOKING(OrderState.COOKING),        // 조리중
    DELIVERING(OrderState.DELIVERING),  // 배달 중
    COMPLETED(OrderState.COMPLETED),    // 배달 완료
    CANCELED(OrderState.CANCELED);      // 주문 취소

    private final String orderState;

    OrderStateEnum(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderState() {
        return this.orderState;
    }

    public static class OrderState {
        public static final String PENDING = "STATE_PENDING";
        public static final String ACCEPTED = "STATE_ACCEPTED";
        public static final String COOKING = "STATE_COOKING";
        public static final String DELIVERING = "STATE_DELIVERING";
        public static final String COMPLETED = "STATE_COMPLETED";
        public static final String CANCELED = "STATE_CANCELED";
    }
}