package docGenerate.Doc.models.DTOs;

public class TemplateDTO {
    private Long id;
    private String name;

    public TemplateDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TemplateDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
