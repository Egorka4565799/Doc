package docGenerate.Doc;

import docGenerate.Doc.controllers.TemplateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TemplateController.class)
@AutoConfigureMockMvc
@SpringJUnitConfig
public class TemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadTemplate() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.docx",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Test content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/templates/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}