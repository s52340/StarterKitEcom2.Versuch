package org.campus02.ecom;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class BasketDataLoader {

    public static ArrayList<BasketData>load(String path) throws DataFileException {
        File file = new File(path);
        ArrayList<BasketData> basketDataList = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))
            ){
                String zeile;
                while ((zeile = br.readLine()) != null){
                    if (zeile.isEmpty()){                   //!!!!
                        continue;
                    }

                    BasketData bd = new Gson().fromJson(zeile,BasketData.class);
                    basketDataList.add(bd);
                }
            }
            return basketDataList;
        } catch (FileNotFoundException e) {
            throw new DataFileException("File wurde nicht gefunden", e);
        } catch (IOException e) {
                throw new DataFileException("File konnte nicht geöffnet werden", e);
        }
    }


}
