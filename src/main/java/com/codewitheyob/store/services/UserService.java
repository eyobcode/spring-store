package com.codewitheyob.store.services;

import com.codewitheyob.store.entities.User;
import com.codewitheyob.store.repositories.AddressRepository;
import com.codewitheyob.store.repositories.ProfileRepository;
import com.codewitheyob.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;

    public void getAddressById(){
//        var user = User.builder()
//                .name("eyob")
//                .email("eyob@m.com")
//                .password("myPassword!")
//                .build();
//        userRepository.save(user);
//        System.out.println(user);

        var profileOptional = profileRepository.findById(1L).orElseThrow();
        System.out.println("==============");
        var address = addressRepository.findById(1L).orElseThrow();
//        System.out.println(address);
//
//        System.out.println(profileOptional.getBio());


    }

}
