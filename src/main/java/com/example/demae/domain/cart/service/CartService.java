package com.example.demae.domain.cart.service;

import com.example.demae.domain.menu.entity.Menu;
import com.example.demae.domain.menu.service.MenuService;
import com.example.demae.domain.cart.dto.request.CartRequestDto;
import com.example.demae.domain.cart.dto.response.*;
import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.cart.entity.CartState;
import com.example.demae.domain.cart.repository.CartRepository;
import com.example.demae.domain.store.service.StoreService;
import com.example.demae.domain.cart.dto.request.OrderRequestDto;
import com.example.demae.domain.cart.entity.OrderItem;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.cart.repository.OrderItemRepository;
import com.example.demae.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartRepository cartRepository;
	private final OrderItemRepository orderItemRepository;
	private final UserService userService;
	private final StoreService storeService;
	private final MenuService menuService;

	public void createCart(OrderRequestDto orderRequestDto, String userEmail) {
		User user = userService.findUser(userEmail);

		Cart cart = cartRepository.findByUser_UserIdAndCartState(user.getUserId(), CartState.READY);

		if (cart == null) {
			cart = new Cart(user);
			cartRepository.save(cart);
		}

		Store store = storeService.findStore(orderRequestDto.getStoreId());
		Menu menu = menuService.findMenu(orderRequestDto.getMenuId());
		OrderItem orderItem = new OrderItem(orderRequestDto, store, menu, cart);
		orderItemRepository.save(orderItem);

		cart.addTotalPrice(orderRequestDto.getOrderItemPrice() * orderRequestDto.getOrderItemQuantity());
	}

	public void addOrderItem(CartRequestDto cartRequestDto, String userEmail) {
		User user = userService.findUser(userEmail);

		Cart cart = cartRepository.findByUser_UserIdAndCartState(user.getUserId(), CartState.READY);

		CartState cartState = CartState.valueOf(cartRequestDto.getCartState().toUpperCase());
		cart.updateCartState(cartState);
	}

	public void deleteCart(Long cartId, String userEmail) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("장바구니가 없습니다."));
		if(!userEmail.equals(cart.getUser().getUserEmail())){
			throw new IllegalArgumentException("본인의 장바구니가 아닙니다.");
		}
		cartRepository.delete(cart);
	}

	@Transactional(readOnly = true)
	public List<OrderAllResponseDto> getAllOrderInfo(String userEmail) {
		User user = userService.findUser(userEmail);
		List<Cart> orderList = cartRepository.findByUser_UserId(user.getUserId());
		return orderList.stream().map(OrderAllResponseDto::new).toList();
	}

	@Transactional(readOnly = true)
	public OrderResponseDto getCartOne(Long cartId, String userEmail) {
		User user = userService.findUser(userEmail);
		Cart findOrder = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("카트가 없습니다."));
		if (user.getStore() != null && user.getStore().getStoreId().equals(findOrder.getOrderItems().get(0).getStore().getStoreId()))  {
			List<OrderItem> orderList = orderItemRepository.findByCart_CartId(cartId);

			OrderResponseDto orderResponseDto = new OrderResponseDto();
			for (OrderItem orderItem : orderList) {
				OrderMenuResponseDto menuDto = new OrderMenuResponseDto(orderItem);
				orderResponseDto.addItem(menuDto);
				orderResponseDto.addToTotalPrice(orderItem.getPrice() * orderItem.getQuantity());
			}
			return orderResponseDto;
		}
		throw new IllegalStateException("본인 가게 정보만 조회가 가능합니다.");
	}


	@Transactional(readOnly = true)
	public CartListResponseDto getCart(Long userId) {
		Cart cart = cartRepository.findByUser_UserIdAndCartState(userId, CartState.READY);
		List<OrderItem> orderItems = cart.getOrderItems();
		CartListResponseDto cartResponseDto = new CartListResponseDto();
		Long userIdFromCart = cart.getUser().getUserId();

		for (OrderItem orderItem : orderItems) {
			Menu menu = orderItem.getMenu();
			CartResponseDto menuDto = new CartResponseDto(menu, orderItem.getPrice(), userIdFromCart);
			cartResponseDto.addItem(menuDto);
			cartResponseDto.addToTotalPrice(orderItem.getPrice() * orderItem.getQuantity());
		}
		return cartResponseDto;
	}

	@Transactional(readOnly = true)
	public Cart findCart(Long cartId){
		return cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("카트가 없습니다."));
	}

	public Cart getOrderForUser(Long cartId, String userEmail) {
		User user = userService.findUser(userEmail);
		Cart findOrder = cartRepository.findById(cartId).orElseThrow();
		if (findOrder.getCartId().equals(cartId)  && findOrder.getUser().getUserId().equals(user.getUserId()))  {
			return findOrder;
		}
		throw new IllegalStateException("본인 주문 정보만 조회가 가능합니다.");
	}

	public Cart completeOrder(Long orderId, String userEmail) {
		User user = userService.findUser(userEmail);
		Cart findOrder = cartRepository.findById(orderId).orElseThrow();
		if (user.getStore() != null && user.getStore().getStoreId().equals(findOrder.getOrderItems().get(0).getStore().getStoreId()))  {
			return cartRepository.findById(orderId).orElseThrow();
		}
		return null;
	}
}
