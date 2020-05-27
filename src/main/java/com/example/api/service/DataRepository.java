package com.example.api.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;


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


    //테이블 조회
    public JSONArray findAll(String tableName) throws SQLException, ClassNotFoundException, JSONException {
        java.sql.Statement statement;
        JSONArray jsonArray = new JSONArray();

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();

        String sql = "SELECT * FROM ";
        sql += tableName;
        rs = stmt.executeQuery(sql);
        Integer i =0;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()){
            JSONObject jsonObject = new JSONObject();
            for (int ii = 1; ii < columnCount + 1; ii++ ) {
                String alias = rsmd.getColumnLabel(ii);
                jsonObject.put(alias, rs.getObject(alias));
            }
            jsonArray.put(i, jsonObject);
            i++;
        }

        return jsonArray;
    }
}
