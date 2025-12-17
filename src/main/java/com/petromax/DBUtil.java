package com.petromax;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    
    private static final String URL =
        "jdbc:postgresql://ep-lively-art-ad24kt4a-pooler.c-2.us-east-1.aws.neon.tech/neondb?sslmode=require";

    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_0hBkrzescx6t";

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
