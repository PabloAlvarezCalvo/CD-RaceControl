package com.campusdual.racecontrol.util;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils(){}


    public static <T> void showFromList(List<T> list, boolean wait, boolean id) {
        Utils.showFromList(list, wait, id, null);
    }

    public static <T> void showFromList(List<T> list, boolean wait, boolean id, List<T> excludeElements) {
        StringBuilder builder = new StringBuilder();
        List<Object> auxList = new ArrayList<>();
        auxList.addAll(list);
        if (excludeElements != null) {
            auxList.removeAll(excludeElements);
        }
        for (int i = 0; i < auxList.size(); i++) {
            builder.append("\t");
            if (id) {
                builder.append(i + 1);
                builder.append(". ");
            }
            builder.append(auxList.get(i).toString());
            builder.append("\n");
        }
        System.out.print(builder);
        if (wait) {
            Input.string("\nPress \"Enter\" to continue...");
        }
    }


    public static <T> String returnShowFromList(List<T> list, boolean wait, boolean id) {
        return Utils.returnShowFromList(list, id, null);
    }

    public static <T> String returnShowFromList(List<T> list, boolean id, List<T> excludeElements) {
        StringBuilder builder = new StringBuilder();
        List<Object> auxList = new ArrayList<>();
        auxList.addAll(list);
        if (excludeElements != null) {
            auxList.removeAll(excludeElements);
        }
        for (int i = 0; i < auxList.size(); i++) {
            builder.append("\t");
            if (id) {
                builder.append(i + 1);
                builder.append(". ");
            }
            builder.append(auxList.get(i).toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    public static <T> int[] showAndSelectFromList(List<T> list, boolean cancel) {
        return Utils.showAndSelectFromList(list, cancel, false);
    }

    public static <T> int[] showAndSelectFromList(List<T> list, boolean cancel, boolean multipleReturn) {
        return Utils.showAndSelectFromList(list, cancel, multipleReturn, null);
    }


    public static <T> int[] showAndSelectFromList(List<T> list, boolean cancel, boolean multipleReturn, List<T> excludeElements) {
        Utils.showFromList(list, false, true, excludeElements);
        StringBuilder builder = new StringBuilder();
        if (!multipleReturn) {
            builder.append("\nSelect the desired option");
            if (cancel) {
                builder.append(", 0 to exit");
            }
            builder.append(": ");
            int selected = Input.integer(builder.toString());
            while (!Utils.checkSelection(selected, list.size()) && (selected != 0)) {
                selected = Input.integer("Invalid option, please choose a different one: ");
            }
            int[] selection = new int[1];
            selection[0] = selected - 1;
            return selection;
        } else {
            String auxSelected = Input.string("\nChose the desired elements, separating them by , : ");
            auxSelected = auxSelected.replace(" ", "");
            String[] split = auxSelected.split(",");
            return Utils.parseIntArray(split);
        }
    }

    static int[] parseIntArray(String[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : arr) {
            list.add(Integer.valueOf(s) - 1);
        }
        return list.stream().mapToInt(i -> i).toArray();
    }


    public static boolean checkSelection(int i, int size) {
        if ((i >= 1) && (i <= size)) {
            return true;
        }
        return false;
    }
}
