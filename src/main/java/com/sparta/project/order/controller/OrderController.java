package com.sparta.project.order.controller;

import com.sparta.project.order.dto.request.OrderCreateRequestDto;
import com.sparta.project.order.dto.response.OrderCreateResponseDto;
import com.sparta.project.order.dto.response.OrderDetailResponseDto;
import com.sparta.project.order.dto.response.OrderListResponseDto;
import com.sparta.project.order.dto.response.OrderResponseDto;
import com.sparta.project.order.entity.OrderStateEnum;
import com.sparta.project.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성 - 고객
     */
    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(requestDto));
    }

    /**
     * 내 주문 목록 조회 (페이징) - 고객
     */
    @GetMapping
    public ResponseEntity<Page<OrderListResponseDto>> getMyOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
            @RequestParam String userId
    ) {
        Page<OrderListResponseDto> response = orderService.getMyOrders(userId, page-1, size, sortBy, isAsc);

        return ResponseEntity.ok(response);
    }

    /**
     * 주문 상세 조회 - 고객
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getOrder(@PathVariable String orderId, @RequestParam String userId) {
        // 주문 상세 조회
        return ResponseEntity.ok(orderService.getOrder(orderId, userId));
    }

    /**
     * 주문 취소 - 고객
     */
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable String orderId, @RequestParam String userId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, userId));
    }

    /**
     *  전체 주문 목록 조회 (페이징) - 관리자
     */
    @GetMapping("/admin/allOrders")
    public ResponseEntity<Page<OrderListResponseDto>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc
    ) {

        return ResponseEntity.ok(orderService.getAllOrders(page, size, sortBy, isAsc));
    }

    /**
     *  가맹점 주문 목록 조회 (페이징) - 가맹점주
     */
    @GetMapping("/store")
    public ResponseEntity<Page<OrderListResponseDto>> getStoreOrders(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc
    ) {

        return ResponseEntity.ok(orderService.getStoreOrders(storeId, page, size, sortBy, isAsc));
    }

    /**
     *  주문 수락 - 가맹점주
     */
    @PatchMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponseDto> acceptOrder(
            @PathVariable String orderId,
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(
                orderService.changeOrderStatus(orderId, storeId, OrderStateEnum.ACCEPTED)
        );
    }

    /**
     *  주문 거절 - 가맹점주
     */
    @PatchMapping("/{orderId}/reject")
    public ResponseEntity<OrderResponseDto> rejectOrder(
            @PathVariable String orderId,
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(
                orderService.changeOrderStatus(orderId, storeId, OrderStateEnum.REJECTED)
        );
    }

    /**
     *  조리 시작 - 가맹점주
     */
    @PatchMapping("/{orderId}/cooking")
    public ResponseEntity<OrderResponseDto> cookingOrder(
            @PathVariable String orderId,
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(
                orderService.changeOrderStatus(orderId, storeId, OrderStateEnum.COOKING)
        );
    }

    /**
     *  배달 시작 - 가맹점주
     */
    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<OrderResponseDto> deliveryOrder(
            @PathVariable String orderId,
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(
                orderService.changeOrderStatus(orderId, storeId, OrderStateEnum.DELIVERING)
        );
    }

    /**
     *  배달 완료 - 가맹점주
     */
    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponseDto> completeOrder(
            @PathVariable String orderId,
            @RequestParam Long storeId
    ) {
        return ResponseEntity.ok(
                orderService.changeOrderStatus(orderId, storeId, OrderStateEnum.COMPLETED)
        );
    }


}