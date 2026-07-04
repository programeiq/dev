FROM eclipse-temurin:21-jdk

# パッケージインストールの自動応答とクリーンアップを徹底
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && \
    apt-get install -y --no-install-recommends openjfx xvfb && \
    rm -rf /var/lib/apt/lists/*

# 【ここを修正！】スクショ通り、targetの中にある「server.jar」をガシッと掴んでコピーする！
COPY target/server.jar ./server.jar

ENV PORT=10000

# お二人が用意してくれた完璧なXvfbの起動コマンド！
CMD ["sh", "-c", "xvfb-run -a --server-args='-screen 0 1024x768x24' java -Dserver.port=${PORT} -Djava.awt.headless=false -jar /server.jar"]
