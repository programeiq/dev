package com.example.demo_new;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class GameServer {
    public static void main(String[] args) {
        Configuration config = new Configuration();

        // 🚀 【超重要】Renderの上で動かすための設定
        // localhost ではなく "0.0.0.0" にすることで、ネットからの接続をすべて受け付けます！
        config.setHostname("0.0.0.0");

        // 🚀 【超重要】Renderから指定されるポート番号を自動で読み込む設定
        // Renderは環境変数 "PORT" でポートを指定してくるため、それを最優先で読み込みます。
        // ローカル（自分のPC）で動かす時は、自動的に後ろの 9092 番が使われます。
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 10000;
        config.setPort(port);

        final SocketIOServer server = new SocketIOServer(config);

        // クライアントが接続してきたときの処理
        server.addConnectListener(client -> {
            System.out.println("クライアントが接続しました: " + client.getSessionId());
        });

        // クライアントが切断したときの処理
        server.addDisconnectListener(client -> {
            System.out.println("クライアントが切断しました: " + client.getSessionId());
        });

        System.out.println("ゲームサーバーを起動しました！ポート: " + port);
        server.start();
    }
}