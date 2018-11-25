package atox;

import atox.model.Cliente;

import java.sql.*;


public class BancoDeDados {

    private static String DRIVER = "sqlserver";
    private static String HOST = "127.0.0.1\\SQLEXPRESS01";
    private static String USER = "sa";
    private static String PASS = "Password123";
    private static String DB = "CarSystem";
    private static int PORTA = 1433;

    private static BancoDeDados instancia;

    // Objeto de conexão
    private Connection conn;

    public BancoDeDados(){
        try {
            conn = DriverManager.getConnection(getStringConn(), USER, PASS);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private String getStringConn(){
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:").append(DRIVER).append("://")
                .append(HOST).append(":")
                .append(PORTA).append(";")
                .append("databaseName=").append(DB);

        return sb.toString();
    }

    public static Statement getNewStatement() throws SQLException {
        if(instancia == null)
            instancia = new BancoDeDados();

        return instancia.getConn().createStatement();
    }

    public Connection getConn(){
        return conn;
    }

}