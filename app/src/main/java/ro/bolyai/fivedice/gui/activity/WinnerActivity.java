package ro.bolyai.fivedice.gui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ro.bolyai.fivedice.R;
import ro.bolyai.fivedice.gui.model.GUIDice;
import ro.bolyai.fivedice.logic.listener.WinnerActivityListener;

public class WinnerActivity extends Activity {

    //region 1. Declarations
    /**
     * The {@link TextView} holding the winner's name.
     */
    private TextView txtvWinnerName;

    /**
     * The first dice {@link ImageView}.
     */
    private ImageView imgvDiceOne;

    /**
     * The second dice {@link ImageView}.
     */
    private ImageView imgvDiceTwo;

    /**
     * The third dice {@link ImageView}.
     */
    private ImageView imgvDiceThree;

    /**
     * The fourth dice {@link ImageView}.
     */
    private ImageView imgvDiceFour;

    /**
     * The fifth dice {@link ImageView}.
     */
    private ImageView imgvDiceFive;

    /**
     * The main menu {@link Button}.
     */
    private Button btnMainMenu;

    /**
     * The play again {@link Button}.
     */
    private Button btnPlayAgain;

    /**
     * Our {@link WinnerActivityListener}.
     */
    private WinnerActivityListener winnerActivityListener;
    //endregion

    /**
     * Ran during the activity's creation.
     *
     * @param savedInstanceState : {@link Bundle} : The bundle containing the saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Set the content view
        setContentView(R.layout.winner_activity_layout);

        // 2. Initialize widgets
        txtvWinnerName = findViewById(R.id.txtvWinnerName);
        imgvDiceOne = findViewById(R.id.imgvDiceOne);
        imgvDiceTwo = findViewById(R.id.imgvDiceTwo);
        imgvDiceThree = findViewById(R.id.imgvDiceThree);
        imgvDiceFour = findViewById(R.id.imgvDiceFour);
        imgvDiceFive = findViewById(R.id.imgvDiceFive);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        // 3. Update the GUI based on the intent
        Intent intent = getIntent();

        if (intent.hasExtra("winnerName")) {
            txtvWinnerName.setText(intent.getStringExtra("winnerName"));
        }

        imgvDiceOne.setImageResource(GUIDice.getBitmapForValue(intent.getIntExtra("diceOne", 0)));
        imgvDiceTwo.setImageResource(GUIDice.getBitmapForValue(intent.getIntExtra("diceTwo", 0)));
        imgvDiceThree.setImageResource(GUIDice.getBitmapForValue(intent.getIntExtra("diceThree", 0)));
        imgvDiceFour.setImageResource(GUIDice.getBitmapForValue(intent.getIntExtra("diceFour", 0)));
        imgvDiceFive.setImageResource(GUIDice.getBitmapForValue(intent.getIntExtra("diceFive", 0)));

        // 4. Initialize listener
        winnerActivityListener = new WinnerActivityListener(this);

        // 5. Set listener
        btnPlayAgain.setOnClickListener(winnerActivityListener);
        btnMainMenu.setOnClickListener(winnerActivityListener);
    }
}