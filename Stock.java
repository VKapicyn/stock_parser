package Main;

import java.util.ArrayList;

/**
 * Created by vlad on 04.04.15.
 */
public class Stock {
    String MONTH;
    String YEAR;
    String DAY;
    long DATE;
    float OPEN;
    float HIGH;
    float LOW;
    float CLOSE;
    String TICKER;

    float weight;
    float index;

    int id;

    public static void base_to_class(ArrayList<String> base_in_str, ArrayList<Stock> base_in_class, String name, int ii) {
        for (int j = 0; j < base_in_str.size() - 1; j++) {
            Stock action = new Stock();
            String d = base_in_str.get(j);
            char[] a = d.toCharArray();

            int i = 0;

            //add YEAR
            StringBuilder YEAR = new StringBuilder("");
            while (a[i] != '-') {
                YEAR.append(a[i]);
                i++;
            }
            i++;
            action.YEAR = YEAR.toString();

            //add MONTH
            StringBuilder MONTH = new StringBuilder("");
            while (a[i] != '-') {
                MONTH.append(a[i]);
                i++;
            }
            i++;
            action.MONTH = MONTH.toString();

            //add DAY
            StringBuilder DAY = new StringBuilder("");
            while (a[i] != ';') {
                DAY.append(a[i]);
                i++;
            }
            i++;
            action.DAY = DAY.toString();

            //add OPEN
            StringBuilder OPEN = new StringBuilder("");
            while (a[i] != ';') {
                OPEN.append(a[i]);
                i++;
            }
            i++;
            action.OPEN = Float.parseFloat(OPEN.toString());

            //add HIGH
            StringBuilder HIGH = new StringBuilder("");
            while (a[i] != ';') {
                HIGH.append(a[i]);
                i++;
            }
            i++;
            action.HIGH = Float.parseFloat(HIGH.toString());

            //add LOW
            StringBuilder LOW = new StringBuilder("");
            while (a[i] != ';') {
                LOW.append(a[i]);
                i++;
            }
            i++;
            action.LOW = Float.parseFloat(LOW.toString());

            //add CLOSE
            StringBuilder CLOSE = new StringBuilder("");
            while (a[i] != ';') {
                CLOSE.append(a[i]);
                i++;
            }
            i++;
            action.CLOSE = Float.parseFloat(CLOSE.toString());

            action.DATE=Integer.parseInt(action.YEAR + action.MONTH + action.DAY);
            action.TICKER=name;
            action.weight=(float)0.01;
            action.id=ii+1;

            base_in_class.add(action);
        }
    }
}
