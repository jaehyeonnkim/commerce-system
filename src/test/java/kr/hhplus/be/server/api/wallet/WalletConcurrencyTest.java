package kr.hhplus.be.server.api.wallet;


import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.dto.WalletRequest;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WalletConcurrencyTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletJpaRepository walletJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void 동시성_테스트_성공() throws InterruptedException {
        int THREAD_COUNT = 10;

        User user = User.builder().name("김재현").build();
        userJpaRepository.save(user);

        Wallet wallet = Wallet.createNewWallet(user, 100, 100, TransactionType.CHARGE);
        walletJpaRepository.save(wallet);

        WalletRequest walletRequest = new WalletRequest(user.getId(), TransactionType.CHARGE, 1000);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    walletService.chargePoint(user, walletRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Wallet updatedWallet = walletJpaRepository.findBalanceById(user.getId()).orElseThrow();
        assertNotNull(updatedWallet);
        assertEquals(10100, updatedWallet.getBalance()); //초기값 100
        }

}