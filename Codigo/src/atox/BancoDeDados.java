package atox;

import atox.model.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class BancoDeDados {

    private static String DRIVER = "sqlserver";
    private static String HOST = "localhost";
    private static String USER = "CarSystemSvcUsr";
    private static String PASS = "_svcusrCarSystem";
    private static String DB = "CarSystem";
    private static int PORTA = 1433;

    private static BancoDeDados instancia;

    // Objeto de conexão
    private Connection conn;

    public BancoDeDados(){
        try {
            conn = DriverManager.getConnection(getStringConn());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private String getStringConn(){
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:").append(DRIVER).append("://")
          .append(HOST).append(":")
          .append(PORTA).append(";")
          .append("databaseName=").append(DB).append(";")
          .append("user=").append(USER).append(";")
          .append("password=").append(PASS);

        return sb.toString();
    }

    public static Statement getNewStatement() throws Exception{
        if(instancia == null) {
            instancia = new BancoDeDados();
        }
        return instancia.getConn().createStatement();
    }

    public Connection getConn(){
        return conn;
    }

}
