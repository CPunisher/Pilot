package com.cpunisher.pilot.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.cpunisher.pilot.R;
import com.cpunisher.pilot.util.RankHelper;
import com.cpunisher.pilot.util.SysApplication;

import java.util.*;

public class RankActivity extends AppCompatActivity {

    private TextView textViewLeft;
    private TextView textViewRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_rank);

        textViewLeft = findViewById(R.id.rankViewLeft);
        textViewRight = findViewById(R.id.rankViewRight);
        textViewLeft.setTextSize(30.0f);
        textViewRight.setTextSize(30.0f);
        initData();
    }

    public void back(View view) {
        this.finish();
    }

    public void clear(View view) {
        RankHelper.clearRankList(this);
        initData();
    }

    private void initData() {
        StringBuilder builderLeft = new StringBuilder();
        StringBuilder builderRight = new StringBuilder();

        List<RankHelper.Rank> list = RankHelper.getSortedRankList(this);
        for (RankHelper.Rank rank : list) {
            builderLeft.append(rank.date);
            builderLeft.append('\n');
            builderRight.append(rank.score);
            builderRight.append('\n');
        }

        textViewLeft.setText(builderLeft.toString());
        textViewRight.setText(builderRight.toString());
    }
}
