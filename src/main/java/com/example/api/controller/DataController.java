package com.example.api.controller;

import com.example.api.service.DataRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/{tableName}")
public class DataController {


    @Autowired
    private DataRepository repository;

    //사용자 전체 조회
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getData(@PathVariable String tableName) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = repository.findAll(tableName);

        return String.valueOf(jsonArray);

    }

    @GetMapping(value = "/detail",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getDataByColNum(
            @PathVariable String tableName,
            HttpServletRequest request
    ) throws SQLException, ClassNotFoundException, JSONException, UnsupportedEncodingException {
        String queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");

        List<String> queryList = Arrays.asList(queryString.split("&"));
        Map<String, Object> queryMap = new HashMap<>();
        for (String query : queryList) {
            String[] queryTuple = query.split("=");
            queryMap.put(queryTuple[0], queryTuple[1]);
        }

        JSONArray jsonArrayCol = repository.findByColNum(tableName,queryMap);
        return String.valueOf(jsonArrayCol);


    }

}


