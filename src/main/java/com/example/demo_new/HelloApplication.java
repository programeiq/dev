package com.example.demo_new;

import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Socket socket;

    @Override
    public void start(Stage stage) throws IOException {
        // 1. サーバーへの接続を開始
        connectToServer();

        // 2. 画面（メニュー画面）の読み込み
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("ネット対戦ゲーム！");
        stage.setScene(scene);
        stage.show();

        // 3. アプリを閉じたら通信も安全に切断する設定
        stage.setOnCloseRequest(windowEvent -> {
            if (socket != null) {
                socket.disconnect();
                System.out.println("アプリ終了に伴い、ソケットを安全に切断しました。");
            }
            Platform.exit();
            System.exit(0);
        });
    }

    private void connectToServer() {
        try {
            // 1. 安全なSSL（HTTPS用）のファクトリを準備
            javax.net.ssl.SSLContext sslContext = javax.net.ssl.SSLContext.getDefault();
            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // 2. 信頼できる証明書マネージャーを取得
            javax.net.ssl.TrustManagerFactory trustManagerFactory = javax.net.ssl.TrustManagerFactory.getInstance(
                    javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
            );
            trustManagerFactory.init((java.security.KeyStore) null);
            javax.net.ssl.X509TrustManager trustManager = (javax.net.ssl.X509TrustManager) trustManagerFactory.getTrustManagers()[0];

            // 3. 【ここが決定打】エラーの原因だったOkHttpClientを正しく組み立てる
            okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .build();

            // 4. Socket.IOのオプションを設定
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = true;
            opts.transports = new String[]{"websocket"};

            // ✨【これで解決】型エラー（'okhttp3.WebSocket.Factory'が必要）に完全適合させます！
            opts.webSocketFactory = okHttpClient;
            opts.callFactory = okHttpClient;

            String serverUrl = "https://my-game-server-v7nc.onrender.com";
            System.out.println("サーバーに接続を試みています... URL: " + serverUrl);

            socket = IO.socket(serverUrl, opts);

            // 接続イベント
            socket.on(Socket.EVENT_CONNECT, args -> {
                javafx.application.Platform.runLater(() -> {
                    System.out.println("👉 【大成功】Renderサーバーに正常に接続されました！");
                });
            });

            socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                System.out.println("❌ 接続エラーが発生しました");
            });

            socket.connect();

        } catch (Exception e) {
            System.out.println("接続中に予期せぬエラーが発生しました。");
            e.printStackTrace();
        }
    }
    // 他のコントローラー（HelloControllerなど）からソケットを使い回すためのメソッド
    public static Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) {
        launch();
    }
}