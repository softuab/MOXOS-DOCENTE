package com.moxos.uab.model.service.moodle;

import com.moxos.uab.model.jsonmodels.MessageResultRest;
import org.springframework.web.client.RestTemplate;

public class MoodleSectionService {

    private String updateection = "/api/section/";
    private String servidor = "";

    public MoodleSectionService(String servidor) {
        this.servidor = servidor;
    }

    public MessageResultRest setSection(int cursoid) {
        String uri = servidor + updateection + "updatesection?cursoid=" + cursoid;
        RestTemplate restTemplate = new RestTemplate();
        MessageResultRest message = restTemplate.getForObject(uri, MessageResultRest.class);
        return message;
    }
}
