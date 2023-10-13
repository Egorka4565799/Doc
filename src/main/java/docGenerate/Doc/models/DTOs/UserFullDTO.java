package docGenerate.Doc.models.DTOs;

import java.util.List;

public class UserFullDTO {
    private Long id;
    private String username;
    private String name;
    private int countDownloadTemplates;

    private List<TemplateDTO> templates;

    public UserFullDTO(Long id, String username, String name, int countDownloadTemplates, List<TemplateDTO> templates) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.countDownloadTemplates = countDownloadTemplates;
        this.templates = templates;
    }

    public UserFullDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountDownloadTemplates() {
        return countDownloadTemplates;
    }

    public void setCountDownloadTemplates(int countDownloadTemplates) {
        this.countDownloadTemplates = countDownloadTemplates;
    }

    public List<TemplateDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateDTO> templates) {
        this.templates = templates;
    }
}
