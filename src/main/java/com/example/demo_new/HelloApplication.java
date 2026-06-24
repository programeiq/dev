package com.example.demo_new;

import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    private Socket socket;

    @Override
    public void start(Stage stage) {
        try {
            // 1. 画面の読み込み（既存のJavaFXの処理）
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("JavaFX Game - Online");
            stage.setScene(scene);
            stage.show();

            // 2. Renderの環境変数に合わせてポートを自動判定する処理（お二人の完璧なコード！）
            String portEnv = System.getenv("PORT");
            int port = (portEnv != null) ? Integer.parseInt(portEnv) : 10000;
            System.out.println("使用するポート番号: " + port);

            // 3. OkHttpClientの設定（型エラー対策済み）
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build();

// 4. Socket.IOのオプション設定（★暗号化 HTTPS/WSS 対応版！）
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            opts.transports = new String[]{"polling", "websocket"};

            // 🔒【超・決定版！】Javaを怒らせずに、暗号化(HTTPS/WSS)を強制するシンプルな方法
            opts.secure = true;
            // ⭕ 面倒なTrustManagerの設定を一切やめて、OkHttpClientのデフォルトに全てを任せる！
            opts.webSocketFactory = okHttpClient;
            opts.callFactory = okHttpClient;
            // 5. 接続先URL（RenderのサーバーURL）
            String serverUrl = "https://demo-new-1.onrender.com";
            System.out.println("🚀 サーバーに接続を試みています... URL: " + serverUrl);

            // 6. ソケット初期化とイベント登録
            socket = IO.socket(serverUrl, opts);

            // 🟢 接続成功時のイベント
            socket.on(Socket.EVENT_CONNECT, args -> {
                Platform.runLater(() -> {
                    System.out.println("🟢🟢🟢 [成功] Renderサーバーとの常時接続が確立しました！！！");
                });
            });

            // 🔴 接続エラー時のイベント
            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                Platform.runLater(() -> {
                    System.out.println("❌ [エラー] 接続に失敗しました。理由: " + (args.length > 0 ? args[0] : "不明"));
                });
            });

            // 7. 接続開始！
            socket.connect();

        } catch (Exception e) {
            System.out.println("💥 初期化中に予期せぬ例外が発生しました");
            e.printStackTrace();
        }
    }

    // ゲーム終了時にソケットを綺麗に閉じる処理
    @Override
    public void stop() {
        if (socket != null) {
            System.out.println("🔌 サーバーとの接続を切断して終了します。");
            socket.disconnect();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}