package com.example.demo_new;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BattleController {

    @FXML private Label matchInfoLabel;

    // 前の画面から対戦相手の情報をセットするための関数
    public void setMatchInfo(String enemyName, int enemyLevel) {
        matchInfoLabel.setText("対戦相手: " + enemyName + " (Lv." + enemyLevel + ")");
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
