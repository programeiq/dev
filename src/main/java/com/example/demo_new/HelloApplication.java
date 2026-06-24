package com.example.demo_new;

import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    private Socket socket;

    @Override
    public void start(Stage stage) {
        try {
            // 1. 画面の読み込み（JavaFXのいつもの処理）
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("JavaFX Game - Online");
            stage.setScene(scene);
            stage.show();

            System.out.println("🎮 ゲーム画面を起動しました！");

            // 2. OkHttpClientの設定（通信を安定させるお二人の優秀な部品）
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();

            // 3. Socket.IOのオプション設定
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;

            // 🔒 【超重要】Renderの仕様（最初はpolling、次にwebsocket）に100%適合させます！
            opts.transports = new String[]{"polling", "websocket"};
            opts.secure = true; // 暗号化(HTTPS/WSS)を有効化

            // 通信の土台にokHttpClientをセット
            opts.webSocketFactory = okHttpClient;
            opts.callFactory = okHttpClient;

            // 4. RenderのサーバーURL（ここに繋ぎにいきます！）
            String serverUrl = "https://demo-new-1.onrender.com";
            System.out.println("🚀 Renderサーバーに接続を試みています... URL: " + serverUrl);

            // 5. ソケット初期化
            socket = IO.socket(serverUrl, opts);

            // 🟢 サーバーとガチッと接続成功したときのイベントログ！
            socket.on(Socket.EVENT_CONNECT, args -> {
                Platform.runLater(() -> {
                    System.out.println("🟢🟢🟢 [大成功] Renderサーバーとの常時接続が確立しました！！！");
                });
            });

            // 🔴 万が一接続エラーが出たときのイベントログ
            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                Platform.runLater(() -> {
                    System.out.println("❌ [接続エラー] 理由: " + (args.length > 0 ? args[0] : "不明"));
                });
            });

            // 6. 接続開始！！！
            socket.connect();

        } catch (Exception e) {
            System.out.println("💥 初期化中に予期せぬ例外が発生しました");
            e.printStackTrace();
        }
    }

    // ゲーム（画面）を閉じたときに、接続も綺麗に切断する処理
    @Override
    public void stop() {
        if (socket != null) {
            System.out.println("🔌 サーバーとの接続を切断して安全に終了します。");
            socket.disconnect();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
