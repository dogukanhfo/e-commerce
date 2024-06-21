package com.project.ecommerce.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ecommerce.models.Cart;
import com.project.ecommerce.models.OrderItem;
import com.project.ecommerce.services.OrderService;
import com.project.ecommerce.services.ProductService;
import com.project.ecommerce.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public String checkout(HttpSession session, Authentication authentication, @RequestParam String paymentMethod) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			String username = authentication.getName();
			Long customerId = userService.getUserByUsername(username).getId();
			List<OrderItem> orderItems = cart.getItems().stream()
					.map(item -> new OrderItem(null, productService.getProductById(item.getProduct().getId()), item.getQuantity(), item.getTotalPrice()))
					.collect(Collectors.toList());
			orderService.createOrder(customerId, orderItems, paymentMethod);
            session.removeAttribute("cart");
		}
		return "redirect:/checkout/order-confirmation";
	}
	
	@GetMapping
    public String showCheckoutForm(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        model.addAttribute("cart", cart);
        return "checkout";
    }
	
	@GetMapping("/order-confirmation")
    public String showOrderConfirmation() {
        return "order-confirmation";
    }
}
