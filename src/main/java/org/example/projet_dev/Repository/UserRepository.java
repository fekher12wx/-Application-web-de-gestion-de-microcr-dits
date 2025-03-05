package org.example.projet_dev.Repository;
import org.example.projet_dev.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {


}
