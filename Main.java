package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by vlad on 04.04.15.
 */
public class Main{
    public static void main(String[] args) throws InterruptedException, IOException {
        long startTime = System.currentTimeMillis();//замеряем время работы программы
        ArrayList<String> spisok_tickerov=new ArrayList<String>();
        ArrayList<Stock> global_base=new ArrayList<Stock>();
        ArrayList<Glob_Index> global_index=new ArrayList<Glob_Index>();


        System.out.println("программа стартовала");
        //-----------------Открываем списки
        try {
            FileInputStream fis = new FileInputStream("/home/vlad/Документы/American/top/_top100_0.txt");
            InputStreamReader in = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(in);

            spisok_tickerov.add(reader.readLine());
            while (spisok_tickerov.get(spisok_tickerov.size() - 1) != null)
                spisok_tickerov.add(reader.readLine());

            FileInputStream fis2 = new FileInputStream("/home/vlad/Документы/American/top/_top100_1.txt");
            InputStreamReader in2 = new InputStreamReader(fis2);
            BufferedReader reader2 = new BufferedReader(in2);

            spisok_tickerov.add(reader2.readLine());
            while (spisok_tickerov.get(spisok_tickerov.size() - 1) != null)
                spisok_tickerov.add(reader2.readLine());

            fis.close();
            fis2.close();
            in.close();
            in2.close();
            reader.close();
            reader2.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Файл с топ100 не найден");
        }

        System.out.println("база считана");
            cleaner(spisok_tickerov);//очищаем список

        //----------------Записываем все нужные компании и подготавливаем к выводу
           for (int i=0; i<spisok_tickerov.size(); i++){
               get_all_base(i,spisok_tickerov, global_base);
           }

        //----------------Удаляем даты которых нет
            del_dates(global_base);
        System.out.println("лишние даты удалены");

        //----------------Считаем  индекс

            index_calc(global_base);//индекс для каждой компании
            Glob_Index.global_index_calc(global_base,global_index);//индекс для всего портфеля

        //--------------------сохраняем все в файлик
        System.out.println("индекс посчитан, идет сохранение в файл");

            save_to_file(global_index,spisok_tickerov,global_base);
        long timeSpent = System.currentTimeMillis() - startTime;

            System.out.println("Программа скомпилировалась. Время компилции ~"+timeSpent/1000+" секунд."+"(~"+timeSpent/60000+" минут)");
    }

    public static void cleaner(ArrayList<String> massiv) {
        int count = Collections.frequency(massiv, "");
        for (int i = 0; i < count; i++)
            massiv.remove("");

        for (int i=0; i<massiv.size(); i++){
            if (null==massiv.get(i)){
                massiv.remove(i);
            }
        }
    }

    public static void get_all_base(int i, ArrayList<String> spisok_tickerov, ArrayList<Stock> global_base) throws IOException, InterruptedException {
        ArrayList<String> base_in_str=new ArrayList<String>();
        ArrayList<Stock> base_in_class=new ArrayList<Stock>();
        String TICKER=spisok_tickerov.get(i);
        try{
            open(base_in_str,TICKER);//открываем файл по имени тикера
        }
        catch(FileNotFoundException e) {
            System.out.println("Файл с топ100 не найден");
        }
        cleaner(base_in_str);
        Stock.base_to_class(base_in_str,base_in_class,TICKER,i);//заполняем класс

        global_base.addAll(base_in_class);//добавляем компанию в общую базу
        base_in_str.clear();
        base_in_class.clear();
    }

    public static void save_to_file(ArrayList<Glob_Index> global_index,ArrayList<String> spisok_tickerov,ArrayList<Stock> global_base) throws IOException {
        try {
            OutputStream f = new FileOutputStream("/home/vlad/Документы/American/100.txt", true);
            OutputStreamWriter writer = new OutputStreamWriter(f);
            BufferedWriter out = new BufferedWriter(writer);

            StringBuilder a = new StringBuilder("");
            a.append("DATE");
            a.append(";");
            a.append(";");
            a.append("Index #1");
            a.append(";");
            a.append(";");
            a.append("Index #2");
            /*for(int i=0; i<spisok_tickerov.size(); i++){
                a.append(";");
                a.append(";");
                a.append(spisok_tickerov.get(i));
            } */

            String b = a.toString();

            out.write(b);
            out.flush();

            out.write("\n");

            /*////
            for(int i=0; i<global_index.size()/2; i++){
                
            }
            *////

            StringBuilder w = new StringBuilder("");
            w.append(global_index.get(0).date);
            w.append(";");
            w.append(";");
            w.append(global_index.get(0).index_0);
            w.append(";");
            w.append(";");
            w.append(global_index.get(global_index.size() / 2).index_1);
            /*int o=0;
            for(int j=0; o<spisok_tickerov.size(); j=j+(global_index.size()/2)){
                w.append(";");
                w.append(";");
                w.append(global_base.get(j).CLOSE);
                o++;
            }*/

            String h = w.toString();

            out.write(h+"\n");
            out.flush();

            for(int i=1; i<global_index.size()/2; i++){
                StringBuilder c = new StringBuilder("");
                c.append(global_index.get(i).date);
                c.append(";");
                c.append(";");
                global_index.get(i).index_0=global_index.get(i).index_0*global_index.get(i-1).index_0;
                c.append(global_index.get(i).index_0);
                c.append(";");
                c.append(";");
                global_index.get(i+global_index.size()/2).index_1=global_index.get(i+global_index.size()/2).index_1*global_index.get((i+global_index.size()/2)-1).index_1;
                c.append(global_index.get(i+global_index.size()/2).index_1);
                /*o=0;
                for(int j=i; o<spisok_tickerov.size(); j=j+(global_index.size()/2)){
                    c.append(";");
                    c.append(";");
                    c.append(global_base.get(j).CLOSE);
                    o++;
                }*/

                String d = c.toString();

                out.write(d+"\n");
                out.flush();
            }
        }
        catch(IOException ex){System.out.println("Не удалось сохранить файл");}
    }

    public static void del_dates(ArrayList<Stock> global_base){
        HashSet<String> geek=new HashSet<String>();//все даты
        ArrayList<String> geek_2=new ArrayList<String>();//все уникальные даты
        ArrayList<String> time=new ArrayList<String>();
        HashSet<String> time_2=new HashSet<String>();


        for (int j = 0; j < global_base.size(); j++) {
            String datka = global_base.get(j).DATE + "";
            geek_2.add(datka);
        }

        geek.addAll(geek_2);
        geek_2.clear();
        geek_2.addAll(geek);
        // волшебный цикл. получился случайно. не пытаться понять логику.

        for(int g=1; g <= 200; g++) {

            int hh=0;
            for(int t=0; t<global_base.size(); t++){
                if(global_base.get(t).id==g){
                    hh=t;
                    break;}
            }

            for (int j = 0; j <geek_2.size(); j++) {

                int flag=0;

                for (int s = hh; (s<global_base.size())&&(global_base.get(s).id == g); s++) {
                    if (Long.parseLong(geek_2.get(j)) == global_base.get(s).DATE) {
                        flag++;
                        break;
                    }
                }

                if (flag==0){
                    time.add(geek_2.get(j));
                }
            }

            System.out.println("Удаление дат завершено на "+g+"%");
        }

        time_2.addAll(time);
        time.clear();
        time.addAll(time_2);

        for (int j=0; j<time.size(); j++){
            for (int k=0; k<global_base.size(); k++){
                if(Long.parseLong(time.get(j))==global_base.get(k).DATE)
                {global_base.remove(k); k--;}
            }
        }
    }

    public static void open(ArrayList<String> base_in_str,String TICKER) throws InterruptedException, IOException {
        try {
            FileInputStream fis = new FileInputStream("/home/vlad/Документы/American/base/"+TICKER+".csv");
            InputStreamReader in = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(in);

            base_in_str.add(reader.readLine());
            base_in_str.remove(0);
            base_in_str.add(reader.readLine());
            while (base_in_str.get(base_in_str.size() - 1) != null)
                base_in_str.add(reader.readLine());

            fis.close();
            in.close();
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Файл с котировками компании"+TICKER+"не найден");
        }
    }

    public static void index_calc(ArrayList<Stock> global_base){
        for(int g=1; g <=200; g++) {
            int hh=0;
            for(int t=0; t<global_base.size(); t++){
                if(global_base.get(t).id==g){
                    hh=t;
                    break;}
            }
            global_base.get(hh).index=0;
            for (int s = hh+1; (s<global_base.size())&&(global_base.get(s).id == g); s++) {
                global_base.get(s).index =(global_base.get(s).CLOSE / global_base.get(s - 1).CLOSE) - 1;
            }

        }

    }
}
