package com.example.demo.homework03.jdbc;
import org.springframework.web.servlet.view.script.ScriptTemplateConfig;

import java.sql.*;
import java.util.*;
public class Example {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private void createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testssm","root","123456");
            if (connection!=null) {
                System.out.println("connection successful!");
            } else {
                System.out.println("connection failed!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("no driver");
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            System.out.println("connection failed!");
            e.printStackTrace();
        }
    }

    public boolean insert(String table,String column,List<Object> values) {
        try {
            String insertStatement =buildInsert(table,column,values.size());
            preparedStatement = connection.prepareStatement(insertStatement);
            for (int i = 1; i < values.size()+1; i++) {
                preparedStatement.setObject(i,values.get(i-1));
            }
            System.out.println(preparedStatement.toString());
            preparedStatement.execute();
            System.out.println("insert successful!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String buildInsert(String table,String column,int count){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + table + " " + column + " values (?" );
        count = count-1;
        while (count>0) {
            sb.append(",?");
            count--;
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public List<Map<String,Object>> query(String table, Map<String,Object> values,String condition) {
        try {
            String sql = buildQuery(table,values,condition);
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map<String,Object>> list = new ArrayList<>();
            while (resultSet.next()) {
                for (String key:values.keySet()) {
                    values.put(key,resultSet.getObject(key));
                }
                list.add(new HashMap<>(values));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String buildQuery(String table,Map<String,Object> values,String condition) {
        //values查询的字段
        String sql = "select "+values.keySet().toString().substring(1,values.keySet().toString().length()-1)+" from "+table;
        if (condition!=null) {
            sql = " where "+condition;
        }
        return  sql;
    }
    private void close() throws SQLException {
        preparedStatement.close();
        connection.close();
        System.out.println("连接关闭");
    }

    public static void main(String[] args) throws SQLException {
        Map<String,Object> values = new TreeMap<>();
        values.put("id",0);
        values.put("name","lisa");
        values.put("age",23);
        values.put("sex","女");
        values.put("address","天津");
        //System.out.println(values.keySet().toString());
        //System.out.println(values.keySet().toString().substring(1,values.keySet().toString().length()-1));
        Example example = new Example();
        example.createConnection();
        String table = "user";
        String columns = "(name,age,sex,address)";
        List<Object> param = Arrays.asList("mary",18,"nv","beijing");
        //example.insert(table,columns,param);

        List<Map<String,Object>> result = example.query(table,values,null);
        for (Map r:result) {
            System.out.println(r.toString());
        }
        example.close();
    }
}
