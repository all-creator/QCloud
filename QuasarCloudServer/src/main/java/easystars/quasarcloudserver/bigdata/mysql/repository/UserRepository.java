package easystars.quasarcloudserver.bigdata.mysql.repository;

import easystars.quasarcloudserver.bigdata.mysql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
