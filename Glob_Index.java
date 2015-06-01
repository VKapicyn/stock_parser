package Main;

import java.util.ArrayList;

/**
 * Created by vlad on 05.04.15.
 */
public class Glob_Index {
    String date;
    float index_0;
    float index_1;



    public static void global_index_calc(ArrayList<Stock> global_base,ArrayList<Glob_Index> global_index){

        // считаем сколько строк в первой компании + выводим на экран для теста
        int dlina=0;
        for(int i=0; true; i++){
            if(global_base.get(i).id==1){
                dlina++;
            }
            else
            break;

        }
        System.out.println(dlina);

        // считаем индекс для топ100_0
        for(int i = 0; i<dlina/*(global_base.size()/2)*/; i++){
            Glob_Index action=new Glob_Index();
            action.index_0=0;
           for(int j=i; j<(global_base.size()/2); j=j+dlina){
               action.index_0=action.index_0+global_base.get(j).index;
           }
            action.index_0=action.index_0*(float)0.01;
            action.index_0++;
            action.date=""+global_base.get(i).DATE;
            global_index.add(action);
        }

        // считаем индекс для топ100_1
        for(int i=global_base.size()/2; i<((global_base.size()/2)+dlina); i++){
            Glob_Index action=new Glob_Index();
            action.index_1=0;
            for(int j=i; j<global_base.size(); j+=dlina){
                action.index_1=action.index_1+global_base.get(j).index;
            }
            action.index_1=action.index_1*(float)0.01;
            action.index_1++;
            action.date=""+global_base.get(i).DATE;
            global_index.add(action);
        }
    }
}
