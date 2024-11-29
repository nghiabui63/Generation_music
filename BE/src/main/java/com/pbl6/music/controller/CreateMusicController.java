package com.pbl6.music.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl6.music.entity.MidiInput;

import java.util.Base64;

@RestController
@RequestMapping("/api/generate-music")
public class CreateMusicController {

    @PostMapping("/create")
    public ResponseEntity<String> generateMusic(@RequestParam("midi_input") MultipartFile midiFile) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            if (midiFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Missing midi_input file");
            }

            // Convert the uploaded file to a byte array
            byte[] midiBytes = midiFile.getBytes();

            // Encode the byte array to Base64
            String midiInputBase64 = Base64.getEncoder().encodeToString(midiBytes);

            // Create request body for Flask API
            ObjectMapper objectMapper = new ObjectMapper();
            MidiInput midiInputObj = new MidiInput(midiInputBase64);
            String jsonBody = objectMapper.writeValueAsString(midiInputObj);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            String apiUrl = "http://127.0.0.1:5000/generate-music";  // URL of your Flask app

            // Send request to Flask API
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Return response from Flask API to the client
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating music: " + e.getMessage());
        }
    }
}
