package atox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/tela_login.fxml"));

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("CarSystem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
