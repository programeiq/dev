FROM eclipse-temurin:21-jdk

# スラッシュを無くして、同じ部屋（作業ディレクトリ）にコピーさせる！
COPY demo_new-1.0-SNAPSHOT-jar-with-dependencies.jar server.jar

ENV PORT=10000

# 起動コマンドもスラッシュを消して、同じ部屋のファイルを指定！
CMD ["java", "-Djava.awt.headless=true", "-Dserver.port=${PORT}", "-jar", "server.jar"]
