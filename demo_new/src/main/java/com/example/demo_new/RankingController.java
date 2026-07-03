package com.example.demo_new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RankingController {

    @FXML private TableView<RankingData> rankingTable;
    @FXML private TableColumn<RankingData, Integer> rankColumn;
    @FXML private TableColumn<RankingData, String> nameColumn;
    @FXML private TableColumn<RankingData, Integer> levelColumn;
    @FXML private TableColumn<RankingData, String> timeColumn;
    @FXML private TableColumn<RankingData, String> debateColumn;

    @FXML
    public void initialize() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("bestTime"));
        debateColumn.setCellValueFactory(new PropertyValueFactory<>("longestDebate"));

        ObservableList<RankingData> data = FXCollections.observableArrayList(
                new RankingData(1, "Player_A", 150, "1.2秒", "15分"),
                new RankingData(2, "Player_B", 120, "1.5秒", "12分"),
                new RankingData(3, "Player_C", 95, "2.0秒", "8分")
        );

        rankingTable.setItems(data);
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class RankingData {
        private final int rank;
        private final String name;
        private final int level;
        private final String bestTime;
        private final String longestDebate;

        public RankingData(int rank, String name, int level, String bestTime, String longestDebate) {
            this.rank = rank;
            this.name = name;
            this.level = level;
            this.bestTime = bestTime;
            this.longestDebate = longestDebate;
        }

        public int getRank() { return rank; }
        public String getName() { return name; }
        public int getLevel() { return level; }
        public String getBestTime() { return bestTime; }
        public String getLongestDebate() { return longestDebate; }
    }
}
