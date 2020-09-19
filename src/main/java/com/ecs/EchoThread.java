//package com.ecs;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//class EchoThread implements Runnable {
//    private Socket client;
//
//    public EchoThread(Socket client) {
//        this.client = client;
//
//    }
//
//    public void run() {
//        //run不需要自己去执行，好像是线程器去执行了来着，可以去看api
//        try {
//            BufferedReader in = null;
//            String br = null;
//            boolean flag = true;
//            while (flag == true) {
//                //Java流的操作没意见吧
//                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                br = in.readLine();
//                System.err.println("++:" + br);
////                recordMsg(br +);//写入到文件
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            System.out.println("error");
//        }
//
//    }
//}