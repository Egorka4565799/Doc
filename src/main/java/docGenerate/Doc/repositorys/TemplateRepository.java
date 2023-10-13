package docGenerate.Doc.repositorys;


import docGenerate.Doc.models.Template;
import docGenerate.Doc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

}
