package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class RuleController {

    //  ホーム（戻る）ボタンが押されたときの処理
    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            System.out.println("[システム] ホーム画面に戻ります...");

            // クリックされたボタンから現在のウィンドウ（Stage）を取得
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();

            // 元のホーム画面（hello-view.fxml）を読み込む
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);

            // 画面を切り替える
            stage.setScene(scene);
            System.out.println("[システム] ホーム画面に戻りました。");

        } catch (Exception e) {
            System.err.println("[エラー] ホーム画面に戻る際に問題が発生しました：");
            e.printStackTrace();
        }
    }
}
