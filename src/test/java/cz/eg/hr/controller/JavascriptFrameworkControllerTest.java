package cz.eg.hr.controller;

import com.google.gson.Gson;
import cz.eg.hr.data.JavascriptFramework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JavascriptFrameworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests GET, POST, PUT and DELETE operations on /api/v1/frameworks resource
     *
     * @throws Exception
     */
    @Test
    public void testFrameworks() throws Exception {
        mockMvc.perform(get("/api/v1/frameworks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));

        Map<String, String> endOfSupportByDate1 = new HashMap<>();
        endOfSupportByDate1.put("16.13.1", "2020-10-14");

        JavascriptFramework javascriptFramework1 = new JavascriptFramework();
        javascriptFramework1.setName("React");
        javascriptFramework1.setRating(4.5f);
        javascriptFramework1.setEndOfSupportByVersion(endOfSupportByDate1);

        Gson gson = new Gson();
        String json1 = gson.toJson(javascriptFramework1);

        mockMvc.perform(post("/api/v1/frameworks")
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"));

        mockMvc.perform(get("/api/v1/frameworks/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json(json1))
            .andExpect(jsonPath("$.id", is(1)));

        Map<String, String> endOfSupportByDate2 = new HashMap<>();
        endOfSupportByDate2.put("5.1.1", "2021-01-08");
        endOfSupportByDate2.put("6.2.3", "2023-06-14");

        JavascriptFramework javascriptFramework2 = new JavascriptFramework();
        javascriptFramework2.setName("Angular");
        javascriptFramework2.setRating(4.0f);
        javascriptFramework2.setEndOfSupportByVersion(endOfSupportByDate2);

        String json2 = gson.toJson(javascriptFramework2);

        mockMvc.perform(post("/api/v1/frameworks")
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"));

        mockMvc.perform(get("/api/v1/frameworks/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json(json2))
            .andExpect(jsonPath("$.id", is(2)));

        javascriptFramework1.setRating(3.1f);
        json1 = gson.toJson(javascriptFramework1);

        mockMvc.perform(put("/api/v1/frameworks/1")
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType("application/json"));

        mockMvc.perform(get("/api/v1/frameworks/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().json(json1))
            .andExpect(jsonPath("$.id", is(1)));

        mockMvc.perform(get("/api/v1/frameworks?search=rea"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].name", is("React")));

        mockMvc.perform(delete("/api/v1/frameworks/1"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/frameworks"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id", is(2)));
    }
}
