package com.example.demo_new;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

public class HelloController {

    private Socket socket;

    // 💡 サーバー側の PlayerInfo と合わせるためのクラス
    public static class PlayerInfo {
        public String name;
        public int level;

        public PlayerInfo(String name, int level) {
            this.name = name;
            this.level = level;
        }
    }

    // ⚔️ マッチングボタンが押されたときの処理
    @FXML
    protected void onMatchingButtonClick(ActionEvent event) {
        Button srcButton = (Button) event.getSource();
        srcButton.setDisable(true);
        srcButton.setText("Socket.IO 接続中... ");

        // 自分の情報をセット（テスト用としてレベル50）
        PlayerInfo myInfo = new PlayerInfo("あなた(Player)", 50);

        try {
            Socket socket = IO.socket("wss://my-game-server-v7nc.onrender.com");

            socket.on("waiting", args -> {
                Platform.runLater(() -> srcButton.setText("他のプレイヤーを検索中... "));
            });

            // 🤝 マッチング成功時の処理
            socket.on("match_success", args -> {
                JSONObject partnerData = (JSONObject) args[0];
                String enemyName = partnerData.optString("name", "謎のプレイヤー");
                int enemyLevel = partnerData.optInt("level", 1);

                Platform.runLater(() -> {
                    try {
                        Stage stage = (Stage) srcButton.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("battle-view.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
                        scene.getRoot().setStyle("-fx-base: #FADBD8; -fx-background: #FDEDEC;");

                        BattleController battleController = fxmlLoader.getController();
                        battleController.setMatchInfo(enemyName, enemyLevel);

                        stage.setScene(scene);
                        System.out.println("[Socket.IOアプリ] マッチング成功！相手: " + enemyName + " (Lv." + enemyLevel + ")");

                        socket.disconnect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            });

            socket.connect();

            // 自分の情報を JSON にしてサーバーに送信
            JSONObject myJson = new JSONObject();
            myJson.put("name", myInfo.name);
            myJson.put("level", myInfo.level);
            socket.emit("match_request", myJson);

        } catch (Exception ex) {
            srcButton.setDisable(false);
            srcButton.setText("接続失敗 ❌ 再試行");
            ex.printStackTrace();
        }
    }

    // 📖 ルール説明ボタンが押されたときの処理（これが必要でした！）
    @FXML
    protected void onRuleButtonClick(ActionEvent event) {
        try {
            System.out.println("[システム] ルール画面を表示します...");
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rule-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 420, 320);
            scene.getRoot().setStyle("-fx-base: #FFF0F5; -fx-background: #FFF5F7;");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 👑 ランキングボタンが押されたときの処理（これも必要でした！）
    @FXML
    protected void onRankingButtonClick(ActionEvent event) {
        try {
            System.out.println("[システム] ランキング画面を表示します...");
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ranking-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 460, 360);
            scene.getRoot().setStyle("-fx-base: #E8F8F5; -fx-background: #F4FBF9;");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}