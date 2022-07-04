package com.qcloud.server.bigdata.mysql.repository;

import com.qcloud.server.bigdata.mysql.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
