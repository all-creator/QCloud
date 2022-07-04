package com.qcloud.server.bigdata.mysql.repository;

import com.qcloud.server.bigdata.mysql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
