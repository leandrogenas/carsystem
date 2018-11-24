package atox;

import atox.model.Cliente;
import atox.model.Veiculo;

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

    ///Cliente
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

    ///Veiculo
    public static Veiculo getVeiculo(String placa) {
        Veiculo veiculo = null;

        try {
            Statement stmt = getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM Veiculo WHERE placa = '"+placa+"'");
            rSet.next();
            veiculo = new Veiculo(
                    rSet.getString("placa"),
                    rSet.getString("cpf_proprietario"),
                    rSet.getString("num_parcelas"),
                    rSet.getString("cor"),
                    rSet.getString("modelo"),
                    rSet.getString("marca"),
                    rSet.getBoolean("importado"),
                    rSet.getInt( "kilometragem")
            );
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return veiculo;
    }

    public static boolean setVeiculo(Veiculo veiculo) throws Exception {
        String insert = "INSERT INTO Cliente (cpf,nome,email,telefone,celular,endereco) VALUES ('";
        insert += veiculo.getPlaca()+"','";
        insert += veiculo.getCpfProprietario()+"','";
        insert += veiculo.getNumParcelas()+"','";
        insert += veiculo.getCor()+"','";
        insert += veiculo.getModelo()+"','";
        insert += veiculo.getMarca()+"','";
        insert += veiculo.isImportado()+"','";
        insert += veiculo.getKm()+"')";
        Statement stmt = getNewStatement();
        stmt.execute(insert);
        return true;
    }

    public static boolean updateVeiculo(Veiculo veiculo) throws Exception {
        String update = "UPDATE Cliente SET ";
        update += " = '" + veiculo.getPlaca()+"',";
        update += " = '" + veiculo.getCpfProprietario()+"',";
        update += " = '" + veiculo.getNumParcelas()+"',";
        update += " = '" + veiculo.getCor()+"',";
        update += " = '" + veiculo.getModelo()+"',";
        update += " = '" + veiculo.getMarca()+"',";
        update += " = '" + veiculo.isImportado()+"',";
        update += " = '" + veiculo.getKm()+"'";
        update += " WHERE cpf_proprietario = '"+veiculo.getCpfProprietario()+"'";
        Statement stmt = getNewStatement();
        stmt.execute(update);
        return true;
    }
}
