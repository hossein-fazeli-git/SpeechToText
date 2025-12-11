package com.stt.speechtotext.controllers;

import com.stt.speechtotext.processors.SpeechProcessor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {

    @Autowired
    private SpeechProcessor speechProcessor;

    static Logger logger = LoggerFactory.getLogger(MainController.class);

    final static String WAV_FORMAT_EXTENSION = "wav";

    @RequestMapping(
            value ="/api/convert/speech/to/text",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    ResponseEntity<String> convertSpeechToText(
            @RequestPart MultipartFile audioFile) {
        String fileExtension = FilenameUtils.getExtension(audioFile.getOriginalFilename());
        // If the file format is not supported return 400
        if(!WAV_FORMAT_EXTENSION.equals(fileExtension)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("We detected unsupported file format. " +
                    "Please note that our API only supports WAV audio format at this time.");
        }
        logger.info("Received a request to convert speech to text for file {}", audioFile.getOriginalFilename());
        return speechProcessor.convertSpeechToText(audioFile);
    }

}
