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

    // Objeto da própria instância (Singleton <- Gugol)
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
        return instancia.getConn().createStatement();
    }

    public Connection getConn(){
        return conn;
    }

    public static BancoDeDados getInstancia(){
        if(instancia == null)
            instancia = new BancoDeDados();

        return instancia;
    }

    public static Cliente getCliente(String cpf) {
        Cliente cliente = null;

        try {
            Statement stmt = getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM Cliente WHERE cpf = '"+cpf+"'");
            rSet.next();
            cliente = new Cliente(
                    rSet.getString("cpf"),
                    rSet.getString("nome"),
                    rSet.getString("endereco"),
                    rSet.getString("telefone"),
                    rSet.getString("celular"),
                    rSet.getString("email")
            );
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return cliente;
    }

    public static boolean setCliente(Cliente cliente) throws Exception {
        String insert = "INSERT INTO Cliente (cpf,nome,email,telefone,celular,endereco) VALUES ('";
        insert += cliente.getCPF()+"','";
        insert += cliente.getNome()+"','";
        insert += cliente.getEmail()+"','";
        insert += cliente.getTelefone()+"','";
        insert += cliente.getCelular()+"','";
        insert += cliente.getEndereco()+"')";
        Statement stmt = getNewStatement();
        stmt.execute(insert);
        return true;
    }

    public static boolean updateCliente(Cliente cliente) throws Exception {
        String update = "UPDATE Cliente SET ";
        update += "nome = '"+cliente.getNome()+"',";
        update += "email = '"+cliente.getEmail()+"',";
        update += "telefone = '"+cliente.getTelefone()+"',";
        update += "celular = '"+cliente.getCelular()+"',";
        update += "endereco = '"+cliente.getEndereco()+"'";
        update += " WHERE cpf = '"+cliente.getCPF()+"'";
        Statement stmt = getNewStatement();
        stmt.execute(update);
        return true;
    }
}
