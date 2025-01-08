package kr.hhplus.be.server.domain.user.repository;

import kr.hhplus.be.server.domain.user.model.User;

public interface UserReaderRepository {

    public User findById(Long id);
}
