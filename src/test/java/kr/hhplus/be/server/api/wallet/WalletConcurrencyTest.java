package kr.hhplus.be.server.api.wallet;


import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserJpaRepository;
import kr.hhplus.be.server.domain.wallet.dto.WalletRequest;
import kr.hhplus.be.server.domain.wallet.dto.WalletResponse;
import kr.hhplus.be.server.domain.wallet.model.TransactionType;
import kr.hhplus.be.server.domain.wallet.model.Wallet;
import kr.hhplus.be.server.domain.wallet.repository.WalletJpaRepository;
import kr.hhplus.be.server.domain.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WalletConcurrencyTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletJpaRepository walletJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;



    @Test
    public void 테스트_데이터_확인() {
        assertTrue(userJpaRepository.findById(1L).isPresent(), "User 데이터가 존재하지 않습니다.");
        assertTrue(walletJpaRepository.findById(1L).isPresent(), "Wallet 데이터가 존재하지 않습니다.");
    }

    @Test
    public void testConcurrentChargePoint_TwoRequests() throws InterruptedException {
        int THREAD_COUNT = 10;

        User user = new User(1L, "김재현");
        userJpaRepository.save(user);

        Wallet wallet = Wallet.createNewWallet(user, 100, 0, TransactionType.CHARGE);
        walletJpaRepository.save(wallet);


        WalletRequest walletRequest = new WalletRequest(1L, TransactionType.CHARGE, 100);

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

        // Assert: 최종 잔액 검증
        Wallet updatedWallet = walletJpaRepository.findById(1L).orElseThrow();
        assertNotNull(updatedWallet);
        assertEquals(10100, updatedWallet.getBalance());
        }

}