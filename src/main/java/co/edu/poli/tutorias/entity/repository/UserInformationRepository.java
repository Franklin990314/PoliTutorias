package co.edu.poli.tutorias.entity.repository;

import co.edu.poli.tutorias.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {

}
