package com.nmuzychuk.store.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
