package com.shashi.wirelesscardemo.repositories;

import com.shashi.wirelesscardemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User>  {



}
