package activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hqyj.dev.ji.fs_sxt.R;

/**
 * the first view shown when the app start
 */
public class WelcomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
    }
}
