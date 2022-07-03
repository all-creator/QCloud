package easystars.quasarcloudserver.bigdata.mysql.repository;

import easystars.quasarcloudserver.bigdata.mysql.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
