package com.example.module_demo_0518;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import Snack.MainSnackActivity;

public class ModeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.mode_fragment, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // 游戏模式
        Button onClickbuttonGame = (Button) view.findViewById(R.id.button_mode1);
        onClickbuttonGame.setOnClickListener(new ModeFragment.ButtonListenerGame());

        // 上班模式
        Button onClickbuttonTowork = (Button) view.findViewById(R.id.button_mode4);
        onClickbuttonTowork.setOnClickListener(new ModeFragment.ButtonListenerTowork());

        // 下班模式
        Button onClickbuttonGohome = (Button) view.findViewById(R.id.button_mode2);
        onClickbuttonGohome.setOnClickListener(new ModeFragment.ButtonListenerGohome());

        // 家长模式
        Button onClickbuttonParents = (Button) view.findViewById(R.id.button_mode3);
        onClickbuttonParents.setOnClickListener(new ModeFragment.ButtonListenerParents());

    }

    private class ButtonListenerGame implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainSnackActivity.class);
            startActivity(intent);
        }
    }

    private class ButtonListenerTowork implements View.OnClickListener {
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity()).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }

    private class ButtonListenerGohome implements View.OnClickListener {
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity()).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }

    private class ButtonListenerParents implements View.OnClickListener {
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity()).setTitle("开发人员正在Coding！尽情期待")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
    }

}
