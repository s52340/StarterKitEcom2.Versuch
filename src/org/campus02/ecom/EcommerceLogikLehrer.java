package org.campus02.ecom;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EcommerceLogikLehrer implements Runnable {
    private Socket socket;

    public EcommerceLogikLehrer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String command;

            BasketAnalyzer basketAnalyzer = null;

            while ((command = br.readLine()) != null) {
                System.out.println("command >> " + command);

                // „OpenFile <path>"
                String[] parts = command.split(" ");
                if (parts[0].equals("OpenFile")) {
                    try {
                        ArrayList<BasketData> list = BasketDataLoader.load(parts[1]);
                        basketAnalyzer = new BasketAnalyzer(list);
                        bw.write("basket data loaded with " + list.size() +" entries");
                        bw.newLine();
                    } catch (DataFileException e) {
                        bw.write("Error: " + e.getMessage());
                        bw.newLine();
                    }
                }
                else if (parts[0].equals("GetEveryNth")) {

                    if (basketAnalyzer == null) {
                        bw.write("Calling GetEveryNth without OpenFile before");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }

                    int number = Integer.parseInt(parts[1]);
                    List<BasketData> list = basketAnalyzer.getEveryNthBasket(number);

                    for (BasketData bd : list) {
                        bw.write(bd.toString());
                        bw.newLine();
                    }
                }
                else if (parts[0].equals("GetStats")) {

                    if (basketAnalyzer == null) {
                        bw.write("Calling GetStats without OpenFile before");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }

                    HashMap<String, ArrayList<Double>> stats = basketAnalyzer.groupByProductCategory();

                    for(String key : stats.keySet()) {
                        ArrayList<Double> values = stats.get(key);
                        double sum = 0;
                        for (Double d : values) {
                            sum+=d;
                        }
                        bw.write(key + " - " + (sum / values.size()));
                        bw.newLine();
                    }
                }
                else if (parts[0].equals("EXIT")){
                    bw.write("good bye");
                    bw.newLine();
                    bw.flush();
                    //socket.close();
                    return;
                }
                else {
                    bw.write("unkown command: " + command);
                    bw.newLine();
                }

                bw.flush();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
