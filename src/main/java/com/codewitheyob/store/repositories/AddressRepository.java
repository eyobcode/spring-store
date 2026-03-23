package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}