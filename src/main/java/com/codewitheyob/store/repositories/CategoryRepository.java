package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}