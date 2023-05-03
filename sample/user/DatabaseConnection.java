// package sample.user;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseConnection {
//     private static final String DB_URL = "jdbc:mysql://localhost:3306/school_management";
//     private static final String DB_USER = "root";
//     private static final String DB_PASSWORD = "omen0405";

//     public class DatabaseConnection{
//         public static Connection getConnection() {
//             Connection conn = null;
//             try {
//                 Class.forName("com.mysql.cj.jdbc.Driver");
//                 conn = DriverManager.getConnection("jdbc:mysql://localhost/school_management", "your_user_name",
//                         "your_password");
//             } catch (ClassNotFoundException | SQLException e) {
//                 e.printStackTrace();
//             }
//             return conn;
//         }
//     }

//     public static String getDbUrl() {
//         return DB_URL;
//     }

//     public static String getDbUser() {
//         return DB_USER;
//     }

//     public static String getDbPassword() {
//         return DB_PASSWORD;
//     }

//     @Override
//     public String toString() {
//         return "DatabaseConnection []";
//     }

//     public static Connection getConnection() {
//         return null;
//     }

//     @Override
//     protected Object clone() throws CloneNotSupportedException {
//         // TODO Auto-generated method stub
//         return super.clone();
//     }

//     @Override
//     public boolean equals(Object arg0) {
//         // TODO Auto-generated method stub
//         return super.equals(arg0);
//     }

//     @Override
//     protected void finalize() throws Throwable {
//         // TODO Auto-generated method stub
//         super.finalize();
//     }

//     @Override
//     public int hashCode() {
//         // TODO Auto-generated method stub
//         return super.hashCode();
//     }

    // public static Connection getConnection() throws SQLException {
    //     try {
    //         Class.forName("com.mysql.cj.jdbc.Driver");
    //     } catch (ClassNotFoundException e) {
    //         e.printStackTrace();
    //         throw new SQLException("MySQL JDBC driver not found.");
    //     }

    //     return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    // }
//}



package sample.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/school_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "omen0405";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    @Override
    public String toString() {
        return "DatabaseConnection []";
    }

    // The other overridden methods are not necessary for this class, so you can
    // remove them.
}
