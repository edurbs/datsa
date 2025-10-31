package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.filter.OrderFilter;
import com.github.edurbs.datsa.domain.model.*;
import com.github.edurbs.datsa.domain.repository.OrderRepository;
import com.github.edurbs.datsa.domain.repository.spec.OrderSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderRegistryService {

    private final OrderRepository orderRepository;
    private final ProductRegistryService productRegistryService;
    private final CityRegistryService cityRegistryService;
    private final UserRegistryService userRegistryService;
    private final RestaurantRegistryService restaurantRegistryService;
    private final PaymentMethodRegistryService paymentMethodRegistryService;

    public OrderRegistryService(OrderRepository orderRepository, ProductRegistryService productRegistryService, CityRegistryService cityRegistryService, UserRegistryService userRegistryService, RestaurantRegistryService restaurantRegistryService, PaymentMethodRegistryService paymentMethodRegistryService) {
        this.orderRepository = orderRepository;
        this.productRegistryService = productRegistryService;
        this.cityRegistryService = cityRegistryService;
        this.userRegistryService = userRegistryService;
        this.restaurantRegistryService = restaurantRegistryService;
        this.paymentMethodRegistryService = paymentMethodRegistryService;
    }

    public Order getById(String uuid) {
        return orderRepository.findByUuid(uuid)
                .orElseThrow(()-> new ModelNotFoundException("Order %s does not exists".formatted(uuid)));
    }

    public Page<Order> getAll(OrderFilter orderFilter, Pageable pageable) {
        return orderRepository.findAll(OrderSpecs.withFilter(orderFilter), pageable);
    }

    @Transactional
    public Order newOrder(Order order) {
        validateOrder(order);
        validateItems(order);
        order.setShippingFee(order.getRestaurant().getShippingFee());
        order.calcTotalAmount();
        return orderRepository.save(order);
    }

    private void validateOrder(Order order){
        City city = cityRegistryService.getById(order.getAddress().getCity().getId());
        MyUser user = userRegistryService.getById(order.getUser().getId());
        Restaurant restaurant = restaurantRegistryService.getById(order.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodRegistryService.getById(order.getPaymentMethod().getId());
        order.getAddress().setCity(city);
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setPaymentMethod(paymentMethod);
        if(restaurant.notAcceptPaymentMethod(paymentMethod)){
            throw new ModelValidationException("Payment method '%s' it not accepted by this restaurant".formatted(paymentMethod.getDescription()));
        }

    }

    private void validateItems(Order order){
        order.getItems().forEach(item->{
            Product product = productRegistryService.getByRestaurant(order.getRestaurant().getId(), item.getProduct().getId());
            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
    });
    }

}
