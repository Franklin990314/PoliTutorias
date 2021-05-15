package co.edu.poli.tutorias.entity.repository;


import co.edu.poli.tutorias.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String userName) throws Exception;
}