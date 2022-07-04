package com.qcloud.server.bigdata.mysql.repository;

import com.qcloud.server.bigdata.mysql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
