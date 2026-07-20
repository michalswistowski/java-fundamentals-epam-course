package com.epam.training.unclean;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        
        Application app = new Application();

        System.out.println(app.filter(List.of(1, 2, 1, 5, 3, 2, 2, 2, 2, 4, 2)));
        System.out.println(app.filter(List.of(1, 2, 1, 2, 1, 2, 1, 2, 3)));
        System.out.println(app.filter(List.of(1, 2, 1, 2, 1, 2, 1, 2)));
        System.out.println(app.filter(List.of(0, 0, 0, 0)));
        System.out.println(app.filter(List.of(1)));
        System.out.println(app.filter(List.of()));
        System.out.println(app.filter(null));


    }

//    public static List<Integer> filter(List<? extends Integer> inputList) {
//        if (inputList == null) {
//            throw new IllegalArgumentException("List cannot be null");
//        }
//        List<Integer> array4 = new ArrayList<>();
//
//        int Idx1;
//        for (Idx1 = 0; Idx1 < inputList.size(); Idx1++) {
//            Integer Indexed = inputList.get(Idx1);
//            boolean l = false;
//            for (int index2 = 1; index2 < inputList.size() - 1; index2++) {
//                if (Idx1 == index2 - 1) {
//                    continue;
//                }
//                if (Indexed.equals(inputList.get(index2 - 1))) {
//                    l = true;
//                    break;
//                }
//            }
//            if (!l) {
//                array4.add(Indexed);
//            }
//        }
//        return array4;
//    }

    public static <T> List<T> filter(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        Map<T, Long> occurenceMap = list.stream()
                 .collect(Collectors.groupingBy(
                            Function.identity(),
                            LinkedHashMap::new,
                            Collectors.counting()));

        return occurenceMap.entrySet().stream()
                .filter((entry) -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .toList();
    }
}
