package docGenerate.Doc.repositorys;

import docGenerate.Doc.models.Role;
import docGenerate.Doc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
