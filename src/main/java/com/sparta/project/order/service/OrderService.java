package com.sparta.project.order.service;

import com.sparta.project.order.dto.request.OrderCreateRequestDto;
import com.sparta.project.order.dto.request.OrderProductRequestDto;
import com.sparta.project.order.dto.response.*;
import com.sparta.project.order.entity.Order;
import com.sparta.project.order.entity.OrderProduct;
import com.sparta.project.order.entity.OrderStateEnum;
import com.sparta.project.order.entity.OrderStatusHistory;
import com.sparta.project.order.repository.OrderProductRepository;
import com.sparta.project.order.repository.OrderRepository;
import com.sparta.project.order.repository.OrderStatusHistoryRepository;
import com.sparta.project.product.entity.Product;
import com.sparta.project.product.repository.ProductRepository;
import com.sparta.project.store.entity.Store;
import com.sparta.project.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // ==========================
        // 1. Store 조회
        // ==========================
        Store store = storeRepository.findById(Long.valueOf(requestDto.getStoreId()))
                .orElseThrow(() ->
                        new IllegalArgumentException("가게를 찾을 수 없습니다.")
                );

        // ==========================
        // 2. 상품 조회
        // ==========================
        List<OrderProduct> orderProducts = new ArrayList<>();

        int totalPrice = 0;

        for(OrderProductRequestDto productDto : requestDto.getOrderProducts()) {
            Product product = productRepository.findById(
                            productDto.getProductId()
                    ).orElseThrow(() ->
                            new IllegalArgumentException("상품을 찾을 수 없습니다.")
                    );


            int productTotalPrice =
                    product.getPrice() * productDto.getQuantity();


            OrderProduct orderProduct = OrderProduct.builder()
                    .productId(product.getProductId())
                    .productName(product.getName())
                    .quantity(productDto.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(productTotalPrice)
                    .createdAt(LocalDateTime.now())
                    .createdBy(requestDto.getUserId())
                    .updatedAt(LocalDateTime.now())
                    .updatedBy(requestDto.getUserId())
                    .build();


            orderProducts.add(orderProduct);


            totalPrice += productTotalPrice;
        }

        // ==========================
        // 3. 주문번호 생성
        // ==========================
        String orderNumber = generateOrderNumber();

        // ==========================
        // 4. 주문 금액 초기화
        // (상품 조회 후 다시 계산 예정)
        // ==========================
        int deliveryFee = 0;
        int discountAmount = 0;
        int finalAmount = 0;


        // ==========================
        // 5. Order 생성
        // ==========================
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .orderStatus(OrderStateEnum.PENDING)
                .totalPrice(totalPrice)
                .deliveryFee(deliveryFee)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .deliveryAddress(requestDto.getDeliveryAddress())
                .deliveryRequest(requestDto.getDeliveryRequest())
                .orderedAt(LocalDateTime.now())
                .userId(requestDto.getUserId())
                .storeId(Long.valueOf(requestDto.getStoreId()))
                .createdAt(LocalDateTime.now())
                .createdBy(requestDto.getUserId())
                .updatedAt(LocalDateTime.now())
                .updatedBy(requestDto.getUserId())
                .build();

        // ==========================
        // 6. OrderProduct 저장
        // ==========================
        for(OrderProduct orderProduct : orderProducts){
            orderProduct.setOrder(order);
            order.getOrderProducts().add(orderProduct);
        }

        // ==========================
        // 7. 주문 저장
        // ==========================
        orderRepository.save(order);

        // ==========================
        // 8. 주문 상태 이력 저장
        // ==========================
        OrderStatusHistory history =
                OrderStatusHistory.builder()
                .order(order)
                .orderNumber(orderNumber)
                .orderStatus(OrderStateEnum.PENDING)
                .etc(requestDto.getDeliveryRequest())
                .createdAt(LocalDateTime.now())
                .createdBy(requestDto.getUserId())
                .updatedAt(LocalDateTime.now())
                .updatedBy(requestDto.getUserId())
                .build();

        historyRepository.save(history);

        // ==========================
        // 9. Response 반환
        // ==========================
        return OrderCreateResponseDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .finalAmount(order.getFinalAmount())
                .orderedAt(order.getOrderedAt())
                .build();
    }

    /**
     * 내 주문 목록 조회
     */
    public Page<OrderListResponseDto> getMyOrders(String userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // 주문 목록 조회
        return orderRepository.findByUserId(userId, pageable).map(OrderListResponseDto::from);
    }

    /**
     * 주문 상세 조회
     */
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrder(String orderId, String userId) {
        // 주문 조회
        Order order = orderRepository
                .findByOrderIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("주문을 찾을 수 없습니다.")
                );

        // 주문 상품 조회
        List<OrderProductResponseDto> products = orderProductRepository.findByOrder(order)
                        .stream()
                        .map(OrderProductResponseDto::from)
                        .toList();

        return OrderDetailResponseDto.from(order, products);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public OrderResponseDto cancelOrder(String orderId, String userId) {

        // 1. 주문 조회
        Order order = orderRepository.findByOrderIdAndUserId(orderId, userId).orElseThrow(
                () -> new IllegalArgumentException("주문을 찾을 수 없습니다.")
        );

        // 2. 이미 취소 여부
        if (order.getOrderStatus() == OrderStateEnum.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        // 3. 완료 여부
        if (order.getOrderStatus() == OrderStateEnum.COMPLETED) {
            throw new IllegalArgumentException("완료된 주문은 취소할 수 없습니다.");
        }

        // 4. 조리 시작 여부
        if (order.getOrderStatus() == OrderStateEnum.COOKING
         || order.getOrderStatus() == OrderStateEnum.DELIVERING)
        {
            throw new IllegalArgumentException("조리 시작 이후에는 취소할 수 없습니다.");
        }

        // 5. 이전 상태 저장
        OrderStateEnum previousStatus = order.getOrderStatus();

        // 6. 상태 변경
        order.setOrderStatus(OrderStateEnum.CANCELED);
        order.setCanceledAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setUpdatedBy(userId);

        // 7. 저장
        orderRepository.save(order);

        // 8. History 저장
        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .orderNumber(order.getOrderNumber())
                .orderStatus(OrderStateEnum.CANCELED)
                .etc(order.getDeliveryRequest())
                .createdAt(LocalDateTime.now())
                .createdBy(userId)
                .updatedAt(LocalDateTime.now())
                .updatedBy(userId)
                .build();

        historyRepository.save(history);

        // 9. Response
        return OrderResponseDto.from(order);
    }

    /**
     * 주문 번호 생성
     */
    private String generateOrderNumber() {

        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String random = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));

        return date + random;
    }


    /**
     *  전체 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getAllOrders(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        return orderRepository.findAll(pageable).map(OrderListResponseDto::from);
    }

    /**
     *  가맹점 주문 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getStoreOrders(Long storeId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page - 1,size,Sort.by(direction, sortBy));

        return orderRepository.findByStoreId(storeId, pageable).map(OrderListResponseDto::from);
    }

    /**
     *  주문 상태 변경
     */
    @Transactional
    public OrderResponseDto changeOrderStatus(
            String orderId,
            Long storeId,
            OrderStateEnum nextStatus
    ) {
        // 주문 조회
        Order order = orderRepository.findByOrderIdAndStoreId(orderId, storeId).orElseThrow(()
                        -> new IllegalArgumentException("주문을 찾을 수 없습니다.")
                );

        order.setOrderStatus(nextStatus);

        historyRepository.save(
                OrderStatusHistory.builder()
                        .order(order)
                        .orderNumber(order.getOrderNumber())
                        .orderStatus(nextStatus)
                        .etc(order.getDeliveryRequest())
                        .createdAt(LocalDateTime.now())
                        .createdBy(String.valueOf(storeId))
                        .updatedAt(LocalDateTime.now())
                        .updatedBy(String.valueOf(storeId))
                        .build()
        );

        return OrderResponseDto.from(order);
    }
}