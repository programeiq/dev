package com.example.demo_new; // お二人のパッケージ名に合わせて調整してください

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 420); // サイズを仕様書用に調整しました
        stage.setTitle("SPEED DEBATE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("🚨 【確認】今間違いなくHelloApplicationが動いています！！！");
        launch();
    }
} // 👈 この最後のカッコが消えていたのがエラーの原因です！