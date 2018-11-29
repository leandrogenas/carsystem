package atox.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Atendimentos {

    public VBox container;
    public AnchorPane a;

    class LinhaAtendimento extends AnchorPane{

        private int id;
        private String marcaModelo;
        private String cor;
        private String etapa;

        LinhaAtendimento(String marcaModelo, String cor, String etapa){
            super();

            this.marcaModelo = marcaModelo;
            this.cor = cor;
            this.etapa = etapa;

            prepararPane();
        }

        private void prepararPane(){
            setStyle("-fx-border-color: #c1c1c1");
            setStyle("-fx-border-width: 0 0 1 0");

            Label lblMM = new Label(marcaModelo);
            lblMM.setFont(Font.font("System", 22));
            lblMM.setLayoutX(14);
            lblMM.setLayoutY(7);

            Label lblEtapa = new Label(etapa);
            lblEtapa.setFont(Font.font("System", FontWeight.BOLD, 16));
            lblEtapa.setPrefSize(202, 25);
            lblEtapa.setLayoutX(385);
            lblEtapa.setLayoutY(14);
            lblEtapa.setTextFill(Color.web("#574eae"));

            Label lblCor = new Label(cor);
            lblEtapa.setFont(Font.font("System", 12));
            lblEtapa.setLayoutX(14);
            lblEtapa.setLayoutY(45);

            Label lblDtInicio = new Label("Data de inicio");
            lblEtapa.setFont(Font.font("System", 12));
            lblEtapa.setLayoutX(14);
            lblEtapa.setLayoutY(63);

            Button btnIniciar = new Button("Iniciar");
            btnIniciar.setPrefSize(62, 25);
            btnIniciar.setLayoutX(385);
            btnIniciar.setLayoutY(47);

            Button btnProx = new Button("Próxima");
            btnProx.setPrefSize(73, 25);
            btnProx.setLayoutX(450);
            btnProx.setLayoutY(47);

            Button btnFinalizar = new Button("Finalizar");
            btnFinalizar.setPrefSize(62, 25);
            btnFinalizar.setLayoutX(525);
            btnFinalizar.setLayoutY(47);

            getChildren().setAll(lblMM, lblCor, lblDtInicio, lblEtapa, btnIniciar, btnProx, btnFinalizar);

        }

    }

    public void initialize(){
        container.getChildren().add(new LinhaAtendimento("Marca", "preto", "inicial"));
    }

}
