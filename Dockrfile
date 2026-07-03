FROM eclipse-temurin:21-jdk

# パッケージインストールの自動応答とクリーンアップを徹底
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx xvfb && \
    rm -rf /var/lib/apt/lists/*

# サーバーJARファイルのコピー
COPY server.jar /server.jar

# デフォルトのポート設定（Renderは環境変数PORTを上書きするためEXPOSEは不要）
ENV PORT=10000

# Xvfb（仮想ディスプレイ）の裏でJavaを実行し、Renderの動的ポートに対応させる起動コマンド
# クラス名を指定するのをやめて、Jarの中に焼き込まれている「Manifest（起動ボタンの記憶）」にすべてを委ねる、世界一シンプルで確実な形に変えます！
CMD ["sh", "-c", "xvfb-run --server-args='-screen 0 1024x768x24' java -Dserver.port=${PORT} -jar /server.jar"]
