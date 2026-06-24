package com.example.demo_new;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class GameServer {
    public static void main(String[] args) {
        // 1. Renderの環境変数からポート番号を自動で読み込む（お二人の完璧なロジック！）
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 9092;

        Configuration config = new Configuration();
        config.setHostname("0.0.0.0"); // すべての外からの接続を受け付ける設定
        config.setPort(port);

        // 🔥【超超超重要：ここが受け止める準備！】
        // Renderの逆プロキシ（HTTPS/WSS）から届く通信を、Socket.IOが正常に解読できるようにする魔法の設定
        config.setAllowHeaders("*");
        config.setOrigin("*");

        // 2. Socket.IOサーバーの起動
        final SocketIOServer server = new SocketIOServer(config);

        // 🟢 クライアント（プレイヤー）が接続してきたときのイベント
        server.addConnectListener(client -> {
            System.out.println("🟢 [サーバーログ] プレイヤーが接続してきました！ ID: " + client.getSessionId());
            // ここに「プレイヤーが入室したよ」という処理があれば書く
        });

        // 🔴 クライアントが切断したときのイベント
        server.addDisconnectListener(client -> {
            System.out.println("❌ [サーバーログ] プレイヤーが切断しました。 ID: " + client.getSessionId());
        });

        // 3. サーバーをスタート！
        System.out.println("🚀 🚀 🚀 GameServerが起動しました！！！ ポート番号: " + port);
        server.start();

        // サーバーが終了しないようにスリープで維持する
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}