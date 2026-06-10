package com.example.demo_new;

import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URISyntaxException;

public class HelloApplication extends Application {

    private static Socket socket;

    @Override
    public void start(Stage stage) throws IOException {
        // 🛠️ 1. 画面の読み込み（既存の設定）
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("オンライン対戦ゲーム");
        stage.setScene(scene);
        stage.show();

        // 🌐 2. サーバーへの接続処理を開始
        connectToServer();
    }

    private void connectToServer() {
        try {
            IO.Options opts = new IO.Options();

            // ⭕ あなたのアドレス「72.14.201.153」をセットしました！
            opts.host = "72.14.201.153";
            opts.port = 9092;

            System.out.println("サーバーに接続を試みています... URL: http://" + opts.host + ":" + opts.port);

            // Socket.IO クライアントの初期化と接続
            socket = IO.socket("http://" + opts.host + ":" + opts.port, opts);

            // 接続成功時のイベントリスナー
            socket.on(Socket.EVENT_CONNECT, args -> {
                System.out.println("サーバーへの接続に成功しました！");
            });

            // 接続切断時のイベントリスナー
            socket.on(Socket.EVENT_DISCONNECT, args -> {
                System.out.println("サーバーから切断されました。");
            });

            socket.connect();

        } catch (URISyntaxException e) {
            System.err.println("URLの形式が正しくありません。");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("接続中に予期せぬエラーが発生しました。");
            e.printStackTrace();
        }
    }

    // 他のクラスからソケットを使い回すためのメソッド
    public static Socket getSocket() {
        return socket;
    }

    @Override
    public void stop() throws Exception {
        // アプリ終了時に安全にソケットを閉じる
        if (socket != null) {
            socket.disconnect();
            System.out.println("アプリ終了に伴い、ソケットを安全に切断しました。");
        }
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}