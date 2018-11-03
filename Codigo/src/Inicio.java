import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Inicio extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/atox/view/tela_login.fxml"));
        Scene sceneLogin = new Scene(root, 300, 200);
        stage.setTitle("Login");
        stage.setScene(sceneLogin);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
