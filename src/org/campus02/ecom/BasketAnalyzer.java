package org.campus02.ecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasketAnalyzer {
    private ArrayList<BasketData> baskets = new ArrayList<>();

    public BasketAnalyzer(ArrayList<BasketData> baskets) {
        this.baskets = baskets;
    }

    // hallo huhu haha hoho hihi hrhr
    public List<BasketData> getEveryNthBasket(int n){
        List<BasketData> neueListe = new ArrayList<>();
        for (int i = 0; i < baskets.size(); i = i+n) {
            neueListe.add(baskets.get(i));
        }
        return neueListe;
    }

    public List<BasketData> filterBaskets(String paymentType, Double from, Double to){
        ArrayList<BasketData> gefilterteListe = new ArrayList<>();
        for (BasketData bd : baskets) {
            if (bd.getPaymentType().equals(paymentType) &&
                bd.getOrderTotal() <= to &&  bd.getOrderTotal() >= from){
                gefilterteListe.add(bd);
            }
        }
        return gefilterteListe;
    }
    // PC + List (gummi 22, schuhe 12, schreiber 3)
    public HashMap<String,ArrayList<Double>>groupByProductCategory(){
        HashMap<String,ArrayList<Double>> hashMap = new HashMap<>();
        for (BasketData bd : baskets) {
            if (hashMap.containsKey(bd.getProductCategory())){
                /*ArrayList<Double> werteVorher = new ArrayList<>();
               werteVorher = hashMap.get(bd.getProductCategory());
               werteVorher.add(bd.getOrderTotal());
               hashMap.put(bd.getProductCategory(), werteVorher);
                // in einem Schritt: */
                hashMap.get(bd.getProductCategory()).add(bd.getOrderTotal());
            }
            else {
                ArrayList<Double> werte = new ArrayList<>();
                werte.add(bd.getOrderTotal());
                hashMap.put(bd.getProductCategory(),werte);
            }
        }
        return hashMap;
    }

}
