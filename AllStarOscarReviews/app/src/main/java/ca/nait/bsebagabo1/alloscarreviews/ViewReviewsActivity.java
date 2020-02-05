package ca.nait.bsebagabo1.alloscarreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

public class ViewReviewsActivity extends AppCompatActivity
{

    private Spinner spinnerCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
    }
}
