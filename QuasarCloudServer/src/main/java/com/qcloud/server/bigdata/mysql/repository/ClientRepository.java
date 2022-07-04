package com.qcloud.server.bigdata.mysql.repository;

import com.qcloud.server.bigdata.mysql.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
