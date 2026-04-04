package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

}