package com.example.module_demo_0518;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DeviceFragment extends Fragment {
    //ImageButton button = (ImageButton) getActivity().findViewById(R.id.buttonTempAndHumidity);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_devide, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        // 温湿度按键
        ImageButton buttonTempAndHumidity = (ImageButton) view.findViewById(R.id.buttonTempAndHumidity);
        buttonTempAndHumidity.setOnClickListener(new ButtonListenerTempAndHumidity());

        // 插座开关
        ImageButton buttonSocket = (ImageButton) view.findViewById(R.id.buttonSocket);
        buttonSocket.setOnClickListener(new ButtonListenerbuttonSocket());

        // 门禁开关
        ImageButton buttonDoor = (ImageButton) view.findViewById(R.id.buttonDoor);
        buttonDoor.setOnClickListener(new ButtonListenerDoor());

        // 门禁开关
        ImageButton buttonInfrared = (ImageButton) view.findViewById(R.id.buttonInfrared);
        buttonInfrared.setOnClickListener(new ButtonListenerInfrared());
    }

    private class ButtonListenerTempAndHumidity implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(),TempAndHumidityActivity.class);
            startActivity(intent);
        }
    }

    private class ButtonListenerbuttonSocket implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(), Socket_many_Activity.class);
            startActivity(intent);
        }
    }

    private class ButtonListenerDoor implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(), DoorActivity.class);
            startActivity(intent);
        }
    }

    private class ButtonListenerInfrared implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent= new Intent(getActivity(), Remote_Activity.class);
            startActivity(intent);
        }
    }

}
