package ro.bolyai.fivedice.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.logic.MainActivityListener;

public class MainActivity extends AppCompatActivity {

    //region 0. Constants
    //endregion

    //region 1. Decl. and Init widgets and attributes
    ImageView imgFiveDice;

    Button btnPlay;
    Button btnLeaderboard;

    MainActivityListener mainActivityListener;
    //endregion

    //region 2. LifeCircle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. Setting the Layout
        setContentView(R.layout.main_activity_layout);

        //2. Generate the Widgets
        imgFiveDice = findViewById(R.id.imgFiveDice);

        btnPlay = findViewById(R.id.btnPlay);
        btnLeaderboard = findViewById(R.id.btnLeaderboard);

        //3. Generate the Listener
        mainActivityListener = new MainActivityListener(this);

        //4. Add Listener
        btnPlay.setOnClickListener(mainActivityListener);
        btnLeaderboard.setOnClickListener(mainActivityListener);

    }
    //endregion

    //region 3. Helper functions and methods
    //endregion
}
