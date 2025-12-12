package com.stt.speechtotext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.vosk.Model;
import org.vosk.Recognizer;

import org.springframework.core.io.Resource;

@SpringBootApplication
@ComponentScan("com.stt")
public class SpeechToTextApplication {

    private static final Logger log = LoggerFactory.getLogger(SpeechToTextApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(SpeechToTextApplication.class, args);
	}

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(2000000);
        return multipartResolver;
    }

    @Bean(name = "voskModel")
    public Model getVoskModel() throws Exception{
        Model model = new Model("/tmp/vosk-model");
        return model;
    }

    @Bean(name = "voskRecognizer")
    public Recognizer getVoskRecognizer(Model model) throws Exception{
        Recognizer recognizer = new Recognizer(model, 16000);
        return recognizer;
    }

}
