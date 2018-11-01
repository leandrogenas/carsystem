package atox.controller;

import atox.tela.Tela;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class Principal {

    @FXML
    private ScrollPane paneConteudo;

    @FXML
    private Text lblTitulo;

    private void carregaPagina(Tela pag){
        try {
            lblTitulo.setText(pag.getTitulo());
            paneConteudo.setContent(pag.getNode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        inicio();
    }

    @FXML
    private void inicio(){ carregaPagina(new atox.tela.Inicial());}
    @FXML
    private void novoOrcamento(){ carregaPagina(new atox.tela.NovoOrcamento()); }
    @FXML
    private void carregaServicos(){ carregaPagina(new atox.tela.Servicos()); }
    @FXML
    private void carregaFinanceiro(){ carregaPagina(new atox.tela.Financeiro()); }
    @FXML
    private void carregaEstoque(){ carregaPagina(new atox.tela.Estoque());}
    @FXML
    private void carregaSelecaoCadastros(){ carregaPagina(new atox.tela.SelecaoCadastros());}
    @FXML
    private void carregaHistoricoOrcamentos(){ carregaPagina(new atox.tela.HistoricoOrcamentos());}
    @FXML
    private void carregaHistoricoServicos(){ carregaPagina(new atox.tela.HistoricoServicos());}
    @FXML
    private void carregaCadastroCliente(){ carregaPagina(new atox.tela.CadastroCliente());}
    @FXML
    private void carregaCadastroFornecedor(){ carregaPagina(new atox.tela.CadastroFornecedor());}
    @FXML
    private void carregaCadastroVeiculo(){ carregaPagina(new atox.tela.CadastroVeiculo());}


}
