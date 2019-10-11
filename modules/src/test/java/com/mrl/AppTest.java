package com.mrl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static int twoD_oneD(int row, int col){
        return row*(row+1)/2 + col;
    }

    static final int max = 48;
    public static String[][] strss = new String[max][max];

    public static void format(){
        File file = new File("/Users/liuyinggang/Desktop/nums.txt");//我的txt文本存放目录，根据自己的路径修改即可
        String[] strs = txt2String(file).replaceAll("\r|\n", "").split(" ");
        int len = strs.length;
        for(int i=0;i<max;i++){
            for(int j=0;j<=i;j++){
                int index = twoD_oneD(i,j);
                if(index>=len) return;
                strss[i][j] = strs[index];
                strss[j][i] = strs[index];
            }
        }
    }

    /**
     * Rigorous Test :-)
     */
//    @Test
    public static void main(String[] args) {
        format();
        System.out.println("hello");
        for(int i=0;i<max;i++){
            for(int j=0;j<max;j++){
                System.out.print(strss[i][j]+" ");
            }
            System.out.println();
        }
    }


}
