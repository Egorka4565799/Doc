package docGenerate.Doc.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_name")
    private String templateName;

    @Lob
    @Column(name = "template_data")
    private byte[] templateData;

    @Column(name = "replace_word")
    private List<String> replaceWord;

    @ManyToOne(fetch = FetchType.LAZY) // Множество шаблонов принадлежит одному пользователю
    @JoinColumn(name = "user_id") // Связь по полю user_id
    private User user; // Ссылка на пользователя

    //--------------- Геттеры, сеттеры и конструкторы ---------------------

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(List<String> replaceWord) {
        this.replaceWord = replaceWord;
    }
}
