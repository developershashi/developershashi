package com.shashi.wirelesscardemo.repository;

import com.shashi.wirelesscardemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
