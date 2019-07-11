package ro.bolyai.fivedice.gui.model;

import android.app.Activity;
import android.os.Bundle;

import ro.bolyai.fivedice.R;

public class WinnerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity_layout);
    }
}
