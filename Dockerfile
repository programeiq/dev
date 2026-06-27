FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y openjfx xvfb && apt-get clean

# ⭕ 一番前にある本物の server.jar をコンテナの中にコピーします
COPY server.jar ./server.jar

# ⭕ Renderの無料Web Service（ポート監視）で絶対にエラーを起こさないための魔法の環境変数
ENV PORT=10000
EXPOSE 10000

# ⭕ 確実にこの場所にある jar を1回だけ起動させます！
CMD ["java", "-jar", "./server.jar"]