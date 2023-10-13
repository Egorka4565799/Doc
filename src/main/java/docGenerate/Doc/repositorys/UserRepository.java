package docGenerate.Doc.repositorys;

import docGenerate.Doc.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
//    @Query("SELECT COUNT(t) FROM User u JOIN u.templates t WHERE u.id = :userId")
//    int getTemplateCount(@Param("userId") Long userId);
}
