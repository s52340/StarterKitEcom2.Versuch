package org.campus02.ecom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DemoBasket {
    public static void main(String[] args) throws DataFileException {
        ArrayList<BasketData> basketDataList = BasketDataLoader.load("data/buyings.json");
        System.out.println("basketDataList.size() = " + basketDataList.size());
    /*
        //hier bekommen wir nciht alle ( ev. zu groß?-weiß nicht)
        //System.out.println(basketDataList.toString());
        for (int i = 0; i < 100; i++) {
            System.out.println(basketDataList.get(i));
        }

        //nach sortierung:
        // 2. Möglichkeit: Collections.sort(basketDataList,new BasketComparator());
        basketDataList.sort(new BasketComparator());
        for (int i = 0; i < 100; i++) {
            System.out.println(basketDataList.get(i));
        }

        System.out.println();
        BasketAnalyzer basketA = new BasketAnalyzer(basketDataList);
        List<BasketData> everyN =  basketA.getEveryNthBasket(1000);
        for (BasketData basketData : everyN) {
            System.out.println(basketData);
        }

        System.out.println();
        BasketAnalyzer basketAn = new BasketAnalyzer(basketDataList);
        ArrayList<BasketData> filter = (ArrayList<BasketData>) basketAn.filterBaskets("Cash", 10.00, 13.00);
        for (BasketData basketData : filter) {
            System.out.println(basketData);
        }

        BasketAnalyzer baAn = new BasketAnalyzer(basketDataList);
        HashMap<String, ArrayList<Double>> hashMap = baAn.groupByProductCategory();
        System.out.println("hashMap.containsKey(\"Music\") = " + hashMap.containsKey("Music"));
        System.out.println("hashMap.get(\"Music\").toString() = " + hashMap.get("Music").toString());
    */

    }

}
