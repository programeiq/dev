FROM eclipse-temurin:21-jdk

# パッケージインストールの自動応答とクリーンアップを徹底
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx xvfb && \
    rm -rf /var/lib/apt/lists/*

# GitHubにある長い名前のJARファイルを、Dockerの中に「server.jar」という名前でコピー
COPY demo_new-1.0-SNAPSHOT-jar-with-dependencies.jar /server.jar

ENV PORT=10000

# 【修正ポイント】無料プランでも動くように -a を追加し、ヘッドレス（画面なしモード）のJava設定も念のため追加！
CMD ["sh", "-c", "xvfb-run -a --server-args='-screen 0 1024x768x24' java -Dserver.port=${PORT} -Djava.awt.headless=false -jar /server.jar"]
