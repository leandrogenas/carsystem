package atox.model;


import atox.BancoDeDados;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Veiculo {

    private int id, codProprietario;
    private String placa, marca, modelo, ano, cor, numParcelas;
    private boolean importado;
    private float km;
    private Cliente proprietario;

    public Veiculo() {}
    public Veiculo(String placa, int codProprietario, String numParcelas, String cor, String modelo, String marca, String ano, boolean importado, float km) {
        setPlaca(placa);
        setCodProprietario(codProprietario);
        setNumParcelas(numParcelas);
        setCor(cor);
        setModelo(modelo);
        setMarca(marca);
        setAno(ano);
        setImportado(importado);
        setKm(km);
    }
    public Veiculo(int id, String placa, int codProprietario, String numParcelas, String cor, String modelo, String marca, String ano, boolean importado, float km) {
        this.id = id;
        setPlaca(placa);
        setCodProprietario(codProprietario);
        setNumParcelas(numParcelas);
        setCor(cor);
        setModelo(modelo);
        setMarca(marca);
        setAno(ano);
        setImportado(importado);
        setKm(km);
    }
    public Veiculo(String placa, String docCliente, String numParcelas, String cor, String modelo, String marca, String ano, boolean importado, float km) {
        setPlaca(placa);
        setCodProprietario(Cliente.getId(docCliente));
        setNumParcelas(numParcelas);
        setCor(cor);
        setModelo(modelo);
        setMarca(marca);
        setAno(ano);
        setImportado(importado);
        setKm(km);
    }

    public Veiculo(String placa){
        this.placa = placa;
    }

    public int getId(){ return id; }
    public String getPlaca() {
        return placa;
    }
    public int getCodProprietario() {
        return codProprietario;
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
    public float getKm() {
        return km;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setKm(float km) {
        this.km = km;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public boolean isImportado() {
        return importado;
    }
    public void setCodProprietario(int cpfProprietario) {
        this.codProprietario = cpfProprietario;
    }
    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }
    public void setImportado(boolean importado) {
        this.importado = importado;
    }
    public String getNumParcelas() { return numParcelas; }
    public void setNumParcelas(String numParcelas) { this.numParcelas = numParcelas; }
    public String getMarca() { return marca; }

    public void setMarca(String marca) { this.marca = marca; }

    public static Veiculo buscaPorPlaca(String placa){
        Veiculo veiculo = null;
        try {
            String sql = "SELECT * FROM Veiculo WHERE placa= '" + placa + "'";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            rSet.next();

            veiculo = new Veiculo(
                    rSet.getInt("cod_veiculo"),
                    rSet.getString("placa"),
                    rSet.getInt("cod_proprietario"),
                    rSet.getString("num_parcelas"),
                    rSet.getString("cor"),
                    rSet.getString("modelo"),
                    rSet.getString("marca"),
                    rSet.getString("ano"),
                    rSet.getBoolean("importado"),
                    rSet.getFloat("kilometragem")
            );
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return veiculo;
    }

    public static List<Veiculo> buscaPorCliente(String docProprietario){
        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        try {
            int idCliente = Cliente.getId(docProprietario);
            String sql = "SELECT * FROM veiculo WHERE cod_proprietario= '" + idCliente+ "'";
            ResultSet rSet = BancoDeDados.getNewStatement().executeQuery(sql);
            while(rSet.next()) {
                Veiculo veiculo = new Veiculo(
                        rSet.getString("placa"),
                        rSet.getInt("cod_proprietario"),
                        rSet.getString("num_parcelas"),
                        rSet.getString("cor"),
                        rSet.getString("modelo"),
                        rSet.getString("marca"),
                        rSet.getString("ano"),
                        rSet.getBoolean("importado"),
                        rSet.getFloat("kilometragem"));
                veiculos.add(veiculo);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        return veiculos;
    }
    public static boolean inserir(Veiculo veiculo) throws Exception {
        String insert = "INSERT INTO Veiculo (placa, cod_proprietario, num_parcelas, cor, modelo, marca, ano, importado, kilometragem) VALUES ('";
        insert += veiculo.getPlaca()+"','";
        insert += veiculo.getCodProprietario()+"','";
        insert += veiculo.getNumParcelas()+"','";
        insert += veiculo.getCor()+"','";
        insert += veiculo.getModelo()+"','";
        insert += veiculo.getMarca()+"','";
        insert += veiculo.getAno()+"','";
        insert += veiculo.isImportado()+"','";
        insert += veiculo.getKm()+"')";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(insert);
    }

    public static boolean alterar(Veiculo veiculo) throws Exception {
        String update = "UPDATE Veiculo SET ";
        update += "placa = '"+veiculo.getPlaca()+"',";
        update += "cod_proprietario = '"+veiculo.getCodProprietario()+"',";
        update += "num_parcelas = '"+veiculo.getNumParcelas()+"',";
        update += "cor = '"+veiculo.getCor()+"',";
        update += "modelo = '"+veiculo.getModelo()+"',";
        update += "marca = '"+veiculo.getMarca()+"',";
        update += "ano = '"+veiculo.getAno()+"',";
        update += "importado = '"+veiculo.isImportado()+"',";
        update += "kilometragem = '"+veiculo.getKm()+"'";
        update += " WHERE placa = '"+veiculo.getPlaca()+"'";

        Statement stmt = BancoDeDados.getNewStatement();
        return stmt.execute(update);
    }
}
