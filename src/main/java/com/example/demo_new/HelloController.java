package com.example.demo_new; // お二人のパッケージ名に合わせて調整してください

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HelloController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label statusLabel; // 👈 ここに文字を表示します！

    // ⚠️【超重要】お二人の本物のRailwayのURLを入れてください（末尾に / を付ける）
    private final String SERVER_URL = "https://demonew-production.up.railway.app/";

    /**
     * MATCHING ボタンが押されたときの処理
     */
    @FXML
    protected void onMatchButtonClick() {
        statusLabel.setText("STARTING MATCHING...");
        sendRequestToServer("match");
    }

    /**
     * RANKING ボタンが押されたときの処理
     */
    @FXML
    protected void onRankButtonClick() {
        statusLabel.setText("FETCHING RANKING...");
        sendRequestToServer("ranking");
    }

    /**
     * サーバーへ非同期で通信を送る共通メソッド
     */
    private void sendRequestToServer(String endpoint) {
        Thread thread = new Thread(() -> {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL + endpoint))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String result = response.body(); // 👈 サーバーから返ってきた文字（例: MATCHING_SUCCESS）

                // 💡 【ここが消えてました！】サーバーからの返事を画面の statusLabel に絶対に表示する処理！
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText(result); // 👈 画面の文字をサーバーからの返事に書き換える！
                });

            } catch (Exception e) {
                // エラーが起きた場合も画面に原因を表示する！
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("CONNECTION ERROR: " + e.getMessage());
                });
            }
        });
        thread.start();
    }
}