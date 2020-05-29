package com.example.api.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;


@Repository
public class DataRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private ResultSetMetaData rsmd;


    //테이블 전체 조회
    public JSONArray findAll(String tableName) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = new JSONArray();

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();

        String sql = "SELECT * FRM ";
        sql += tableName;
        rs = stmt.executeQuery(sql);
        Integer i = 0;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while (rs.next()){
            JSONObject jsonObject = new JSONObject();
        for (int ii = 1; ii < columnCount + 1; ii++) {
                String alias = rsmd.getColumnLabel(ii);
                jsonObject.put(alias, rs.getObject(alias));
        }
            jsonArray.put(i, jsonObject);
            i++;
        }

        return jsonArray;
    }

    //테이블 특정 파라미터 값 조회
    public JSONArray findByColNum(String tableName, Map<String, Object> queryMap) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = new JSONArray();

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();



        Integer i = 0;


        String sql = "SELECT *";
        sql += " FROM "+tableName;
        sql += " where ";
        int j = 0;
        for(String mapkey : queryMap.keySet()){
            sql += mapkey + " = " +  '"' + queryMap.get(mapkey).toString() + '"';
            j ++;
            if (queryMap.size() != j) {
                sql += " AND ";
                continue;
            }
        }
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmdAll = rs.getMetaData();
        int columnCountAll = rsmdAll.getColumnCount();

        jsonArray = new JSONArray();
        while (rs.next()){
            JSONObject jsonObject = new JSONObject();
            for (int ii = 1; ii < columnCountAll + 1; ii++) {
                String alias = rsmdAll.getColumnLabel(ii);
                jsonObject.put(alias, rs.getObject(alias));
            }
            jsonArray.put(i, jsonObject);
            i++;
        }
        return jsonArray;
    }
}
