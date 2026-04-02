package com.codewitheyob.store.repositories;

import com.codewitheyob.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}