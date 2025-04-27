
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.TreeMap;

public class Test {
    public static void main(String[] args) {
//        TreeMap<Integer,Double> treeMap = new TreeMap<>();
//        treeMap.put(1990,0.0);
//        treeMap.put(1995,200.0);
//        treeMap.put(2000,500.0);
//        Object[] arrayList = treeMap.keySet().toArray();
//        int size =arrayList.length;
//        System.out.println(arrayList[0]);
//        System.out.println(size);

        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

//        TimeSeries dogPopulation = new TimeSeries();
//        dogPopulation.put(1994, 400.0);
//        dogPopulation.put(1995, 500.0);
//
//        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
//        System.out.println(totalPopulation);
//        catPopulation.years();

//        NGramMap  nGramMap = new NGramMap("data/ngrams/very_short.csv" ,"data/ngrams/total_counts.csv");

    }
}
