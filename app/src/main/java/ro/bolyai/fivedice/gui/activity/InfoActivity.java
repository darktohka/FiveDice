package ro.bolyai.fivedice.gui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import ro.bolyai.fivedice.R;

public class InfoActivity extends AppCompatActivity {
    //region 0. Constants

    //endregion

    //region 1. Decl and Init
    private EditText txtInfoOut;

    private ImageView imgFiveDiceInfo;
    //endregion

    //region 2. Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.info_activity_layout);

        this.imgFiveDiceInfo = findViewById(R.id.imgInfoFiveDice);
        this.txtInfoOut=findViewById(R.id.txtvInfoOut);

        this.txtInfoOut.setFocusable(false);
        //this.imgFiveDiceInfo.


    }

    //endregion
}
