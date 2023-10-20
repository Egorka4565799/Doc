package docGenerate.Doc.models;

import java.util.List;
import java.util.Map;


public  class ValidationResponse {
    private String message;
    private Map<String,String> errors;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}