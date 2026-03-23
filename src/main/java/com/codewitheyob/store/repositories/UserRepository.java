package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
