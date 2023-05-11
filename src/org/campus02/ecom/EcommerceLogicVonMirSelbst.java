package org.campus02.ecom;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EcommerceLogicVonMirSelbst implements Runnable {

    private Socket socket;

    public EcommerceLogicVonMirSelbst(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()));
        ){
            // BasketAnalyzer basketAnalyzer = null; falls ich hier schon Basketanalyzer
            // benutzen will erst einfach null setzten! (benötige ich mit meiner Methode nicht)
            ArrayList<BasketData> eingelDa = new ArrayList<>();
            String command;
            while ((command =br.readLine()) != null){
                System.out.println("command >> " + command);

                if (command.startsWith("OpenFile ")){
                    String[] spalte = command.split(" ");
                    try {
                        eingelDa = BasketDataLoader.load(spalte[1]); //=eingelesene Daten
                    } catch (DataFileException e) {
                        bw.write("Error: " + e.getMessage());
                        bw.newLine();
                    }
                    bw.write("basket data loaded with " + eingelDa.size() + " entries");
                    bw.newLine();
                    // bw.flush(); hier nicht nötig
                }
                else if (command.startsWith("GetEveryNth ")){
                    String[] spalte = command.split(" ");
                    int nthZahl = Integer.parseInt(spalte[1]);
                    BasketAnalyzer ba = new BasketAnalyzer(eingelDa);
                    if (ba.getEveryNthBasket(nthZahl).size() == 0){
                        bw.write("Bitte vorher OpenFile anwenden");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }
                    else { //bw.write(ba.getEveryNthBasket(nthZahl).toString()); schöner unten:
                        List<BasketData> list = ba.getEveryNthBasket(nthZahl);
                        for (BasketData basketData : list) {
                            bw.write(basketData.toString());
                            bw.newLine();
                        }

                   }
                }

                else if (command.equals("GetStats")){
                    BasketAnalyzer ba = new BasketAnalyzer(eingelDa);
                    if (eingelDa.size() == 0){
                        bw.write("Bitte vorher OpenFile anwenden");
                        bw.newLine();
                        bw.flush();
                        continue;
                    }
                    HashMap<String, ArrayList<Double>> hashMap = ba.groupByProductCategory();
                    for (String key : hashMap.keySet()) {
                        ArrayList<Double> doublWerte =  hashMap.get(key);
                        Double sum = 0.0;
                        for (Double aDouble : doublWerte) {
                            sum =+ aDouble;
                        }
                        sum = sum / doublWerte.size();
                        /* HashMap<String, Double> neueHashMapDurchschnittsW = new HashMap<>();
                        neueHashMapDurchschnittsW.clear();
                        neueHashMapDurchschnittsW.put(key,sum);
                        bw.write(neueHashMapDurchschnittsW.toString());
                        bw.newLine();
                        */
                        //oder einfach: Richtiger als obere Ausgabe - keine neue Hashmap gefordert lt. Angabe
                        bw.write(key + " - " + sum);
                        bw.newLine();
                    }
                    
                }

               else if (command.equalsIgnoreCase("Exit")){
                   bw.write("Goodbye");
                   bw.newLine();
                   bw.flush();
                   break;
               }

               else {
                   bw.write("unknown command: " + command );
                   bw.newLine();
                }

            bw.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
