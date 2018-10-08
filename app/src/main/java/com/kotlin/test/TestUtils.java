package com.kotlin.test;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class TestUtils {


    /**
     * @param context
     * @param fileName 文件名 不包括路径
     * @param content  写入的内容
     */
    public static void save(Context context, String fileName, String content) {
        if (fileName == null) {
            return;
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {//生成路径在data data 包名下 files
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @param context
     * @param fileName 文件名 不包括路径
     */
    public static String read(Context context, String fileName) {
        if (fileName == null) {
            return null;
        }
        FileInputStream input = null;
        BufferedReader reader = null;
        try {
            input = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(input));
            String content;
            StringBuffer sb = new StringBuffer();
            if (!TextUtils.isEmpty(content = reader.readLine())) {
                sb.append(content);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static Map<String, String> readContact(Context context) {
        Cursor cursor = null;
        Map<String, String> map = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                map = new HashMap<>();
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    map.put(name, number);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            return map;
        }
    }

    public static int[] countBits(int num) {
        int[] ret = new int[num + 1];
        ret[0] = 0;
        for (int i = 1; i <= num; ++i) {
            ret[i] = ret[i >> 1] + (i & 1);
        }
        return ret;
    }

    //冒泡排序
    public static void bubbleSort(int[] numbers) {
        int temp = 0;
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) { //交换两数位置
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }

        for (int i : numbers) {
            System.out.println(i);
        }
    }

    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers 带查找数组
     * @param low     开始位置
     * @param high    结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; //数组的第一个作为中轴
        while (low < high) {
            while (low < high && numbers[high] > temp) {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while (low < high && numbers[low] < temp) {
                low++;
            }
            numbers[high] = numbers[low]; //比中轴大的记录移到高端
        }
        numbers[low] = temp; //中轴记录到尾
        return low; // 返回中轴的位置
    }

    public static void main(String[] args) {
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);//true 值在-128~127之间
        System.out.println(f3 == f4);//false  当integer值不在-128~127之间，会返回一个新的integer对象
        int i = 2 << 3;
        System.out.println(i);

        String a = "Programming";
        String b = new String("Programming");
        String c = "Program" + "ming";

        System.out.println(a == b);//false
        System.out.println(a == c);//true
        System.out.println(a.equals(b));//true
        System.out.println(a.equals(c));//true
        System.out.println(a.intern() == b.intern());//true
        int[] arr = {3, 1, 32, 45, 2, 53};
        bubbleSort(arr);
    }
}
