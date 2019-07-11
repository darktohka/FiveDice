package ro.bolyai.fivedice.testdata;

import java.util.ArrayList;
import java.util.List;

import ro.bolyai.fivedice.model.PlayerDatas;

public class TestData {

    //region 0. Constants
    private static final int MAX_PLAYERS=50;
    //endregion

    //1. Constructor
    public TestData(){

    }
    //endregion

    public static synchronized List<PlayerDatas> getTestPlayerDatas(){
        List<PlayerDatas> testPlayerDatas= new ArrayList<>();
        for(int i=0;i<MAX_PLAYERS;i++){
            PlayerDatas playerDatas= new PlayerDatas();
            playerDatas.setName("Player "+String.valueOf(i));
            playerDatas.setId(i);
            playerDatas.setWinsPvP(i);
            playerDatas.setWinsPvE(i);

            testPlayerDatas.add(playerDatas);
        }

        return testPlayerDatas;

    }
}
