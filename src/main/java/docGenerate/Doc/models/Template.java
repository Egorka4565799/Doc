package docGenerate.Doc.models;

import jakarta.persistence.*;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Lob
    @Column(name = "template_data")
    private byte[] templateData;

    //--------------- Геттеры, сеттеры и конструкторы ---------------------

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public byte[] getTemplateData() {
        return templateData;
    }

    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

}
