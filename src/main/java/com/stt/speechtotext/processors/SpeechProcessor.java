package com.stt.speechtotext.processors;

import com.stt.speechtotext.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vosk.Model;
import org.vosk.Recognizer;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.Instant;

@Service
public class SpeechProcessor {

    @Autowired
    private Recognizer recognizer;

    static Logger logger = LoggerFactory.getLogger(SpeechProcessor.class);

    public ResponseEntity<String> convertSpeechToText(MultipartFile audioFile) {
        long startEpochMillis = Instant.now().toEpochMilli();
        logger.info("Starting to process file {}", audioFile.getOriginalFilename());
        StringBuilder sb = new StringBuilder();
        try (InputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(audioFile.getInputStream()))) {
            byte[] b = new byte[4096];
            int nbytes;
            while ((nbytes = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, nbytes)) {
                    String detectedString = recognizer.getResult();
                    logger.info("Detected String is :{} ", detectedString);
                    sb.append(CommonUtils.getTextValueFromResult(detectedString));
                    sb.append(" ");
                }
            }
            String detectedString = recognizer.getFinalResult();
            logger.info("Detected String is :{} ", detectedString);
            sb.append(CommonUtils.getTextValueFromResult(detectedString));
        } catch (Exception e) {
            logger.info("Encountered error when processing file {}", audioFile.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We Encountered Some Internal Error in Our System. Please try again later.");
        }
        logger.info("Finished processing file {} in {} ms", audioFile.getOriginalFilename(),  Instant.now().toEpochMilli() - startEpochMillis);
        return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
    }

}
