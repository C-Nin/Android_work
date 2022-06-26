package com.example.navigationview.mqtt;

import android.os.Bundle;

import com.lichfaker.log.Logger;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;


/**
 * 使用EventBus分发事件
 *
 * @author LichFaker on 16/3/25.
 * @Email lichfaker@gmail.com
 */
public class MqttCallbackBus implements MqttCallback {

    @Override
    public void connectionLost(Throwable cause) {
        Logger.e(cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Logger.d(topic + "====" + message.toString());
        String msg=topic.trim()+","+message.toString().trim();
//        Bundle bundle=new Bundle();
////                //传递参数部分
//        bundle.putString("mesg",msg);
//        Logger.d("msg: " + msg);
        EventBus.getDefault().post(msg);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }


}
