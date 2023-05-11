package org.campus02.ecom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BasketServerSt {
    public static void main(String[] args) {
        try (ServerSocket servSo = new ServerSocket(4444);
        ){
            while (true){
                System.out.println("Server wartet auf CLient");
                try (Socket cliSo = servSo.accept())
                {
                    System.out.println("Client hat sich verbunden");

                    EcommerceLogicVonMirSelbst ecommerceLogic = new EcommerceLogicVonMirSelbst(cliSo);
                    ecommerceLogic.run();

                    //für mutlithreading:
                    // oberes auskommentieren - geht mit meiner Implementierung nicht -> EcommerceLogikVonMirSelbst
                    //Lehrer:  -- ging bei Lehere im Video - bei mir nicht
                    //EcommerceLogikLehrer ecommerceLogikLehrer = new EcommerceLogikLehrer(cliSo);
                    //new Thread(ecommerceLogikLehrer).start();
                }

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
