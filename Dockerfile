FROM eclipse-temurin:21-jdk

# お二人が書いてくれた完璧な、目の前のJARファイルを掴む魔法の1行！
COPY demo_new-1.0-SNAPSHOT-jar-with-dependencies.jar /server.jar

# Renderのポート設定
ENV PORT=10000

# 【最終修正】画面（Xvfb）をわざわざ立ち上げず、Javaに直接「画面なしで動け！」と命令してエラーを完全回避！
CMD ["java", "-Djava.awt.headless=true", "-Dserver.port=${PORT}", "-jar", "/server.jar"]
