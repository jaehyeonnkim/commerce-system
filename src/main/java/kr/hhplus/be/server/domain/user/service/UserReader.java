package kr.hhplus.be.server.domain.user.service;

import kr.hhplus.be.server.domain.user.infra.UserCoreReaderRepository;
import kr.hhplus.be.server.domain.user.model.User;
import kr.hhplus.be.server.domain.user.repository.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReader {

    private final UserCoreReaderRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
