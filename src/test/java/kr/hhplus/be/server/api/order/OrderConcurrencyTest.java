package kr.hhplus.be.server.api.order;


import kr.hhplus.be.server.application.order.dto.request.OrderFacadeRequest;
import kr.hhplus.be.server.application.order.facade.OrderFacade;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.model.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderConcurrencyTest {

    @Autowired
    private OrderFacade orderFacade;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 동시성_테스트_성공() throws InterruptedException {
        int THREAD_COUNT = 10;

        User user = User.builder().name("김재현").build();
        userJpaRepository.save(user);
        Product product = Product.builder().name("멋진가방").price(10000).stock(100).build();
        productRepository.save(product);

        OrderFacadeRequest orderRequest = new OrderFacadeRequest(user.getId(), product.getId(), 1);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    orderFacade.orderProducts(orderRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(90, updatedProduct.getStock()); // 100 - 10 * 1 = 90
    }

}