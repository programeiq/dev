const express = require('express');
const app = express();

// Railwayが自動で割り当てるポートに対応（おまじない）
const PORT = process.env.PORT || 3000;

// アプリ（JavaFX）から通信が届いたら「動いてるよ！」と返事をするだけの機能
app.get('/', (req, res) => {
    console.log("🚀 お二人のJavaFXアプリから通信が届きました！！！");
    res.send("GAME_SERVER_OK");
});

// サーバーを起動して待ち受ける
app.listen(PORT, () => {
    console.log(`🎮 Node.jsゲームサーバーがポート ${PORT} で爆速起動しました！！！`);
});
