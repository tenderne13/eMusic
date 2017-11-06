package com.net.Entity;
import java.util.Arrays;
import java.util.Scanner;

public class BotSort {

    //返回数组元素最大值下标
    public static <T extends Comparable<? super T>> int max(Integer value[]){
        Integer max=value[0];
        for(int i=0;i<value.length;i++){
            if(value[i].compareTo(max)>0){
                max=value[i];
            }
        }
        return max;
    }

    //返回数组元素最小值下标
    public static <T extends Comparable<? super T>> int min(Integer value[]){
        Integer min=value[0];
        for(int i=0;i<value.length;i++){
            if(value[i].compareTo(min)<0){
                min=value[i];
            }
        }
        return min;
    }

    //将元素x插入到value排序对象数组中前n个元素中，插入位置由x值大小来决定
    public static <T extends Comparable<? super T>> void insert(T value[],int n,T x){
        Arrays.sort(value);

        value=Arrays.copyOf(value,value.length+1);
        System.arraycopy(value,n-1,value,n,value.length-n);
        value[n-1]=x;
        for(int i=0;i<value.length;i++){
            if(i==value.length-1){
                System.out.println(value[i]);
            }else{
                System.out.print(value[i]+" ");
            }
        }
        //System.arraycopy(value,0,value,);
    }

    //第一个排序
    public static <T extends Comparable<? super T>> void sort(T value[]){
        Arrays.sort(value);
        for(int i=0;i<value.length;i++){
            if(i==value.length-1){
                System.out.println(value[i]);
            }else{
                System.out.print(value[i]+" ");
            }
        }
    }

    //第二个排序
    public static <T> void sort(T value[],Comparable<? super T> c){
        Arrays.sort(value);
        for(int i=0;i<value.length;i++){
            if(i==value.length-1){
                System.out.println(value[i]);
            }else{
                System.out.print(value[i]+" ");
            }
        }

    }

    //二分查找返回位置
    public static <T extends Comparable<? super T>> int binarySearch(T value[],T key){
        int mid = value.length/2;
        if(key.compareTo(value[mid])==0){
            return mid;
        }

        int start=0;
        int end = value.length-1;
        while (start<=end){
            mid=(end-start)/2+start;
            if(key.compareTo(value[mid])<0){
                end=mid-1;
            }else if(key.compareTo(value[mid])>0){
                start=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    //二分查找返回位置给定begin end
    public static <T extends Comparable<? super T>> int binarySearch(T value[],int begin,int end,T key){
        int mid=0;
        while (begin<=end){
            mid=(end-begin)/2+begin;
            if(key.compareTo(value[mid])<0){
                end=mid-1;
            }else if(key.compareTo(value[mid])>0){
                begin=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    //
    public static <T> int binarySearch(T value[],T key,Comparable<? super T> c){
        int mid = value.length/2;

        if(((Comparable<T>)key).compareTo(value[mid])==0){
            return mid;
        }
        /*if((Integer) key - (Integer) value[mid] ==0){
            return mid;
        }*/

        int start=0;
        int end = value.length-1;
        while (start<=end){
            mid=(end-start)/2+start;
            if(((Comparable<T>)key).compareTo(value[mid])<0){
                end=mid-1;
            }else if(((Comparable<T>)key).compareTo(value[mid])>0){
                start=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }

    public static <T> int binarySearch(T value[],int begin,int end,T key,Comparable<? super T> c){
        int mid=0;
        while (begin<=end){
            mid=(end-begin)/2+begin;
            if(((Comparable<T>)key).compareTo(value[mid])<0){
                end=mid-1;
            }else if(((Comparable<T>)key).compareTo(value[mid])>0){
                begin=mid+1;
            }else{
                return mid;
            }
        }
        return -1;
    }


    public static <T extends Comparable<? super T>> void searchPrint(T value[],T key){
        for(int i=0;i<value.length;i++){
            if(key.compareTo(value[i])==0){
                System.out.println(i);
            }
        }
    }

    public static <T> void searchPrint(T value[],T key,Comparable<? super T> c){
        for (int i=0;i<value.length;i++){
            if(((Comparable<T>)key).compareTo(value[i])==0){
                System.out.println(i);
            }
        }
    }




    public static void main(String[] arcgs){
        int arrSize=0;//对象数组大小
        Integer[] arr=null;//对象数组
        int n=0;//insert方法中的参数n
        int value=0;//insert方法中插入的值。
        int valueFind=0;//查找的元素
        int begin=0;//起始位置。
        int end=0;//结束位置
        int valueFind2=0;//打印输出中的key
        //1.
        Scanner scanner=new Scanner(System.in);
        arrSize = scanner.nextInt();

        //2.
        //scanner=new Scanner(System.in);
        String[] temp=scanner.nextLine().split(" ");
        //初始化数组
        arr = new Integer[arrSize];
        for(int i=0;i<temp.length;i++){
            arr[i]= Integer.valueOf(temp[i]);
        }

        Integer[] arrTemp=Arrays.copyOf(arr,arr.length);//拷贝一个备份数组，最后两步用。

        //3.
        scanner=new Scanner(System.in);
        n=scanner.nextInt();

        //4.
        scanner=new Scanner(System.in);
        value=scanner.nextInt();

        //5.
        scanner=new Scanner(System.in);
        valueFind=scanner.nextInt();

        //6.
        scanner=new Scanner(System.in);
        begin=scanner.nextInt();

        //7.
        scanner=new Scanner(System.in);
        end=scanner.nextInt();

        //8.
        scanner=new Scanner(System.in);
        valueFind2=scanner.nextInt();

        //9.输出最大值
        int max=max(arr);
        System.out.println(max);

        //10.输出最小值
        int min=min(arr);
        System.out.println(min);

        //11.插入20
        insert(arr,n,value);

        //12.第一个排序
        sort(arr);

        //13.第二个排序
        sort(arr,value);

        //14.二分查找返回位置
        int index = binarySearch(arr,valueFind);
        System.out.println(index);

        //15
        index=binarySearch(arr,begin,end,valueFind);
        System.out.println(index);

        /* 16 */
        index=binarySearch(arr,valueFind,valueFind);
        System.out.println(index);

        //17
        index=binarySearch(arr,begin,end,valueFind,valueFind);
        System.out.println(index);

        //18
        searchPrint(arrTemp,valueFind2);

        //19
        searchPrint(arrTemp,valueFind2,valueFind2);

    }
}
