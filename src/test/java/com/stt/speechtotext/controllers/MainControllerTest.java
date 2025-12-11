package com.stt.speechtotext.controllers;

import com.stt.speechtotext.processors.SpeechProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Mock
    private SpeechProcessor speechProcessor;

    @InjectMocks
    private MainController mainController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnsupportedFileFormat() throws Exception{
        MockMultipartFile file = new MockMultipartFile(
                "audioFile", // Name of the request part (must match the controller's @RequestParam or @RequestPart name)
                "sample_test.mp3", // Original file name
                MediaType.MULTIPART_FORM_DATA_VALUE, // Content type
                "Hello, World!".getBytes() // Content as bytes
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/convert/speech/to/text")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest()); // Expect HTTP 400 Bad_Request;
    }

    @Test
    public void testSuccessfulRequest() throws Exception{
        MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

        when(mockMultipartFile.getOriginalFilename()).thenReturn("sample_test.wav");
        when(speechProcessor.convertSpeechToText(any())).
                thenReturn(ResponseEntity.status(HttpStatus.OK).body("detected text"));
        ResponseEntity<String> response = mainController.convertSpeechToText(mockMultipartFile);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("detected text", response.getBody());
    }

}
