/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cunoc.minierp_backend.repositories;

import com.cunoc.minierp_backend.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gabrielh
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByUserName(String userName);
    
}
