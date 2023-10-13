package docGenerate.Doc.models.DTOs;

public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private int countTemplates;
    private int countDownloadTemplates;


    public UserDTO(Long id, String username, String name, int countTemplates, int countDownloadTemplates) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.countTemplates = countTemplates;
        this.countDownloadTemplates = countDownloadTemplates;
    }

    public UserDTO() {
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

    public int getCountTemplates() {
        return countTemplates;
    }

    public void setCountTemplates(int countTemplates) {
        this.countTemplates = countTemplates;
    }

    public int getCountDownloadTemplates() {
        return countDownloadTemplates;
    }

    public void setCountDownloadTemplates(int countDownloadTemplates) {
        this.countDownloadTemplates = countDownloadTemplates;
    }
}
