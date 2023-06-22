package com.example.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class QueryController {
    // GET /path?query=keyword&limit=20
    @GetMapping("/path")
    public Map<String, Object> queryParams(
//        @RequestParam("query") String query,
        @RequestParam(value = "query") String query,
        @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ){
        log.info("query = " + query);
        log.info("limit = " + limit);

        Map<String, Object> response = new HashMap<>();
        response.put("query", query);
        response.put("limit", limit);
        return response;
    }
}
