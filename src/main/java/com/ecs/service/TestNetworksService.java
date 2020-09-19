package com.ecs.service;

import com.ecs.constant.Networks;
import com.ecs.model.NetworkConditions;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhaoone on 2019/4/15
 **/
@Service
public class TestNetworksService {
/*
    public TestNetworksService(String ip_adress) {
        this.ip_adress = ip_adress;
    }
*/

    public NetworkConditions getNetworkCondition() throws IOException {
        NetworkConditions networkConditions = new NetworkConditions();
        networkConditions.setDelay(getNetworkDelay());
        networkConditions.setPacket_loss(getNetworkPacketLoss());
        return networkConditions;
    }

//    public static String getNetworkDelay() throws IOException {
//        try {
//            Process p = Runtime.getRuntime().exec("ping " + Networks.IP_ADDRESS + " -c 1");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            StringBuilder sb = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            p.getInputStream().close();
//            System.out.println(sb.toString());
//            if(sb.toString().contains("time=")){
//                String[] message = sb.toString().split("time=");
//                String delay = message[1].split(" ms")[0];
//                if(Double.valueOf(delay) > 2000)
//                    return "2001";
//                else return delay;
//            }
//            else return "2001";
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return "2001";
//        }
//    }


/*
* windows
*/
    public String getNetworkDelay() throws IOException {
        int maxTime = 0;
        String maxtimeString = "";
        try {
            Process p = Runtime.getRuntime().exec("ping " + Networks.IP_ADDRESS + " -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            p.getInputStream().close();
            String[] message = sb.toString().split(" +");
            String timeString;

            for (int i = 0; i < message.length; i++) {
                if (message[i].startsWith("TTL=")) {
                    timeString = message[i - 1].replaceAll(message[i - 1].split("(?:(\\d+ms))")[0], "");
                    Pattern pat = Pattern.compile("\\D+");
                    Matcher m = pat.matcher(timeString);
                    int time = Integer.valueOf(m.replaceAll("").trim());
                    if (time > maxTime) {
                        maxTime = time;
                        maxtimeString = timeString;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (maxTime == 0) {
            return String.valueOf(Integer.valueOf(Networks.MAX_DELAY)+1);
        }else {
            return maxtimeString.split("ms")[0];
        }
    }



    public String getNetworkPacketLoss() throws IOException {
        InetAddress address = InetAddress.getByName(Networks.IP_ADDRESS);
        int packet_loss = 10;
        for(int i = 0;i < 10; i++)
            if(address.isReachable(Integer.valueOf(Networks.MAX_DELAY)))
                packet_loss--;
        packet_loss = packet_loss/10*100;
        Random r = new Random();
        if(packet_loss>10 && packet_loss<90)
            packet_loss+=r.nextInt(20)-10;
        return String.valueOf(packet_loss);          //丢包率有%.
    }
}
