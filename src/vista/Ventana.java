package vista;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.print.DocFlavor.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ventana extends Application {

	

	public static void main(String[] args) {
		
		launch(args);
	}
	
	@Override
	public void start(Stage windows) throws IOException {
		windows.setTitle("");
	     try {
	    	 Parent root = FXMLLoader.load(getClass().getResource("ventana.fxml"));
	         Scene scene = new Scene(root);
			 windows.setScene(scene);
			 windows.show();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
