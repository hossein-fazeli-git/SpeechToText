package com.stt.speechtotext.processors;


import com.stt.speechtotext.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.vosk.Recognizer;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
public class SpeechProcessorTest {

    @Mock
    Recognizer recognizer;

    @InjectMocks
    private SpeechProcessor speechProcessor;

    @BeforeEach
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSuccessfulAudioProcessing() throws Exception{
        try (MockedStatic<CommonUtils> mockedStatic = Mockito.mockStatic(CommonUtils.class)) {
            mockedStatic.when(() -> CommonUtils.getTextValueFromResult(anyString())).thenReturn("Parsed Input");
            byte[] validWavBytes;
            validWavBytes = Files.readAllBytes(Paths.get("src/test/resources/sample_audio.wav"));
            InputStream mockInputStream = new ByteArrayInputStream(validWavBytes);
            MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);
            when(mockMultipartFile.getOriginalFilename()).thenReturn("sample_audio.wav");
            when(mockMultipartFile.getInputStream()).thenReturn(mockInputStream);
            when(recognizer.getFinalResult()).thenReturn("\"{\"test\":\"Parsed Input\"}\"");
            ResponseEntity<String> response = speechProcessor.convertSpeechToText(mockMultipartFile);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
            Assertions.assertEquals(response.getBody(), "Parsed Input");
        }
    }

    @Test
    public void testFailedAudioProcessing() throws Exception{
            MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);
            when(mockMultipartFile.getOriginalFilename()).thenReturn("sample_audio.wav");
            when(mockMultipartFile.getInputStream()).thenThrow(IOException.class);
            ResponseEntity<String> response = speechProcessor.convertSpeechToText(mockMultipartFile);
            Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
