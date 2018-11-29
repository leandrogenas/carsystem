package atox.model;


import atox.BancoDeDados;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Veiculo {

    private int id;
    private String placa;
    private Cliente proprietario;
    private String modelo;
    private String marca;
    private String ano;
    private String cor;
    private boolean importado;
    private int km;

    public Veiculo(String placa){
        this.placa = placa;
    }

    public Veiculo(String placa, Cliente proprietario, String marca, String modelo, String ano, String cor, boolean importado, int km){
        this.placa = placa;
        this.proprietario = proprietario;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.importado = importado;
        this.km = km;
    }

    public Veiculo(int id, String placa, Cliente proprietario, String marca, String modelo, String ano, String cor, boolean importado, int km){
        this(placa, proprietario, marca, modelo, ano, cor, importado, km);
        this.id = id;
    }


    public int getId(){ return id; }

    public String getPlaca() {
        return placa;
    }
    public String getMarca() { return marca; }
    public Cliente getProprietario() {
        return proprietario;
    }
    public String getModelo() {
        return modelo;
    }
    public String getAno() {
        return ano;
    }
    public String getCor() {
        return cor;
    }
    public int getKm() { return km; }
    public boolean isImportado() { return importado; }

    public void setPlaca(String placa){ this.placa = placa; }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setMarca(String marca){ this.marca = marca; }
    public void setKm(int km) {
        this.km = km;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }
    public void setImportado(boolean importado) {
        this.importado = importado;
    }
    public SimpleStringProperty cpfPropr(){ return new SimpleStringProperty(getProprietario().getDocumento()); }

    public static Veiculo buscaPorPlaca(String placa){
        Veiculo veiculo = null;
        try {
            String sql = "SELECT * FROM veiculo WHERE placa='" + placa + "'";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);

            if(!rSet.next())
                return null;

            veiculo = new Veiculo(
                    rSet.getInt("cod_veiculo"),
                    rSet.getString("placa"),
                    Cliente.buscaPorId(rSet.getInt("cod_proprietario")),
                    rSet.getString("marca"),
                    rSet.getString("modelo"),
                    rSet.getString("ano"),
                    rSet.getString("cor"),
                    rSet.getBoolean("importado"),
                    rSet.getInt("kilometragem")
            );

        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return veiculo;
    }

    public static List<Veiculo> todos() {
        ArrayList<Veiculo> saida = new ArrayList<>();
        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM veiculo");

            while(rSet.next()) {
                Veiculo veiculo = new Veiculo(
                        rSet.getInt("cod_veiculo"),
                        rSet.getString("placa"),
                        Cliente.buscaPorId(rSet.getInt("cod_proprietario")),
                        rSet.getString("marca"),
                        rSet.getString("modelo"),
                        rSet.getString("ano"),
                        rSet.getString("cor"),
                        rSet.getBoolean("importado"),
                        rSet.getInt("kilometragem")
                );

                saida.add(veiculo);
            }

            rSet.close();

        }catch (SQLException e){
            System.err.println("Erro ao obter os serviços");
        }catch (Exception e){
            e.printStackTrace();
        }

        return saida;
    }

    public static List<Veiculo> buscaPorCliente(String docProprietario){
        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        try {
            Cliente cli = Cliente.buscaPorDocumento(docProprietario);
            String sql = "SELECT * FROM veiculo WHERE cod_proprietario= '" + cli.getId() + "'";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            while(rSet.next()) {
                Veiculo veiculo = new Veiculo(
                        rSet.getString("placa"),
                        cli,
                        rSet.getString("marca"),
                        rSet.getString("modelo"),
                        rSet.getString("ano"),
                        rSet.getString("cor"),
                        rSet.getBoolean("importado"),
                        rSet.getInt("kilometragem"));
                veiculos.add(veiculo);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return veiculos;
    }

    public static boolean inserir(Veiculo veiculo) throws Exception {
        String insert = "INSERT INTO Veiculo (placa, cod_proprietario, cor, modelo, marca, ano, importado, kilometragem) VALUES ('";
        insert += veiculo.getPlaca()+"','";
        insert += veiculo.getProprietario().getId()+"','";
        insert += veiculo.getCor()+"','";
        insert += veiculo.getModelo()+"','";
        insert += veiculo.getMarca()+"','";
        insert += veiculo.getAno()+"','";
        insert += ((veiculo.isImportado()) ? "1" : "0")  +"',";
        insert += veiculo.getKm()+")";
        Statement stmt = BancoDeDados.getNewStatement();

        return stmt.execute(insert);
    }

    public static void alterar(Veiculo veiculo) throws SQLException {
        String update = "UPDATE veiculo SET ";
        update += "placa = '"+veiculo.getPlaca()+"',";
        update += "cod_proprietario = '"+veiculo.getProprietario().getId()+"',";
        update += "cor = '"+veiculo.getCor()+"',";
        update += "modelo = '"+veiculo.getModelo()+"',";
        update += "marca = '"+veiculo.getMarca()+"',";
        update += "ano = '"+veiculo.getAno()+"',";
        update += "importado = '"+ ((veiculo.isImportado()) ? "1" : "0") +"',";
        update += "kilometragem = '"+veiculo.getKm()+"'";
        update += " WHERE cod_veiculo = '"+veiculo.getId()+"'";
        Statement stmt = BancoDeDados.getNewStatement();
        stmt.execute(update);
    }

    public static boolean excluir(int id) {
        String delete = "DELETE FROM veiculo WHERE cod_veiculo="+id;
        try {
            return BancoDeDados.getNewStatement().execute(delete);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static Veiculo buscaPorId(int id){
        Veiculo veic = null;

        try {
            Statement stmt = BancoDeDados.getNewStatement();
            ResultSet rSet = stmt.executeQuery("SELECT * FROM cliente WHERE cod_veiculo="+id+"");
            rSet.next();
            veic = new Veiculo(
                    rSet.getString("placa"),
                    Cliente.buscaPorId(Integer.parseInt(rSet.getString("cod_proprietario"))),
                    rSet.getString("marca"),
                    rSet.getString("modelo"),
                    rSet.getString("ano"),
                    rSet.getString("cor"),
                    rSet.getBoolean("importado"),
                    rSet.getInt("kilometragem")
            );
        }
        catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return veic;
    }

}
