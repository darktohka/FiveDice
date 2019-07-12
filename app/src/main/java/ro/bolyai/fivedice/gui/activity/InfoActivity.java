package ro.bolyai.fivedice.gui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ro.bolyai.fivedice.R;

public class InfoActivity extends AppCompatActivity {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
    private TextView txtvInfoOut;

    private ImageView imgFiveDice;
    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.info_activity_layout);

        this.imgFiveDice = findViewById(R.id.imgFiveDice);
        this.txtvInfoOut=findViewById(R.id.txtvInfoOut);


    }

    //endregion
}
