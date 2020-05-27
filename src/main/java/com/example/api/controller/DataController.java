package com.example.api.controller;

import com.example.api.service.DataRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    Environment env;

    @Autowired
    private DataRepository repository;

    @GetMapping("/{tableName}")
//    public Map<String, Object> getData(@PathVariable String tableName) throws SQLException, ClassNotFoundException, JSONException {
//
//        JSONArray jsonArray = repository.findAll(tableName);
//        Map<String, Object> result = new HashMap<>();
//        JSONObject jsonObject = (JSONObject)jsonArray.get(0);
//        result.put("ID", jsonObject.get("ID"));
    public String getData(@PathVariable String tableName) throws SQLException, ClassNotFoundException, JSONException {

        JSONArray jsonArray = repository.findAll(tableName);
//        Map<Object, String> result = new HashMap<>();
//        JSONObject jsonObject = (JSONObject)jsonArray.get(0);
//        result.put("ID", jsonObject.get("ID"));

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(jsonArray));

        return String.valueOf(jsonArray);
    }
}
