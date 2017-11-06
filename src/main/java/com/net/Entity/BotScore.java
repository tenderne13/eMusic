package com.net.Entity;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Scanner;
interface aveScoreInteface{
    void getAverageSocre(JTextField[] fields,JButton btn);
    void getDelHTAverageSvore(JTextField[] fields,JButton btn);
}
public class BotScore extends JFrame implements aveScoreInteface{
    private JTextField[] fields=null;
    public BotScore(int judgeNum,int scoreBegin,int scoreEnd) throws HeadlessException {
        super("BotScore");
        this.setBounds(300,300,600,400);
        this.setDefaultCloseOperation(3);
        //Container contentPane = this.getContentPane();
        JPanel contentPane=new JPanel(new BorderLayout());
        this.getContentPane().add(contentPane);

        JPanel panel=new JPanel(new GridLayout(0,5));
        //JScrollPane pane=new JScrollPane();
        //pane.add(panel);
        contentPane.add(panel,BorderLayout.NORTH);
        fields=new JTextField[judgeNum];
        //JTextField textField=null;
        double sum=0;
        for (int i = 0; i<judgeNum ; i++) {
            double random=getRandom(scoreBegin,scoreEnd);
            sum+=random;
            fields[i]=new JTextField(random+"",1);
            fields[i].setEditable(false);
            fields[i].setHorizontalAlignment(JTextField.CENTER);
            panel.add(fields[i]);
        }
        //按钮面板
        JPanel btnPanel=new JPanel(new GridLayout(0,3));
        JButton avgBtn=new JButton("平均分");
        avgBtn.setSize(200,10);

        JButton delHTBtn=new JButton("去头尾平均分");
        final JButton resultBtn=new JButton("");
        resultBtn.setEnabled(false);
        resultBtn.setBackground(new Color(0xE9FFBB));
        avgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAverageSocre(fields,resultBtn);
            }
        });
        delHTBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDelHTAverageSvore(fields,resultBtn);
            }
        });



        btnPanel.add(avgBtn);
        btnPanel.add(delHTBtn);
        btnPanel.add(resultBtn);



        contentPane.add(btnPanel,BorderLayout.CENTER);
        this.setVisible(true);
    }


    @Override
    public void getAverageSocre(JTextField[] fields, JButton btn) {
        if(fields.length<=0){
            JOptionPane.showMessageDialog(this,"裁判个数必须为大于零的整数");
            return;
        }
        double[] dataArr=new double[fields.length];
        double sum=0;
        for(int i=0;i<fields.length;i++){
            dataArr[i]=Double.parseDouble(fields[i].getText());
            sum+=dataArr[i];
        }
        double averageScore=sum/fields.length;
        btn.setText(String.valueOf(averageScore));
    }

    @Override
    public void getDelHTAverageSvore(JTextField[] fields, JButton btn) {
        if(fields.length<2){
            JOptionPane.showMessageDialog(this,"求此平均值裁判个数必须大于2~");
            return;
        }

        double[] dataArr=new double[fields.length];
        double sum=0;
        for(int i=0;i<fields.length;i++){
            dataArr[i]=Double.parseDouble(fields[i].getText());
            sum+=dataArr[i];
        }
        System.out.println("未去头尾之前总和："+sum+",平均值为:"+sum/fields.length);
        Arrays.sort(dataArr);
        System.out.println("头为："+dataArr[0]+",尾:"+dataArr[fields.length-1]+",总和："+(sum-dataArr[0]-dataArr[fields.length-1]));
        double delHTscore=(sum-dataArr[0]-dataArr[fields.length-1])/(fields.length-2);
        btn.setText(String.valueOf(delHTscore));
    }




    public static double getRandom(int start,int end){
        /*int随机数
            要获取一个[x,y)的int类型的随机数 | 左闭右开
            int d = x + (int)(Math.random() * (y - x));
            要获取一个(x,y]的int类型的随机数 | 左开右闭
            int d = y - (int)(Math.random() * (y - x));
            要获取一个[x,y]的int类型的随机数 | 左闭右闭
            int i = x + (int)(Math.random() * (y - x + 1));
            要获取一个(x,y)的int类型的随机数 | 左开右开
            int d = (int)((y - Math.random()) % y);*/
        double random = start + (int)(Math.random() * (end - start + 1));
        return  random;
    }

    public static void main(String[] args) {

        try {
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入裁判个数:");
        int judgeNum = scanner.nextInt();

        Scanner scanner1=new Scanner(System.in);
        System.out.println("请输入分数起始范围:");
        int begin = scanner.nextInt();

        Scanner scanner2=new Scanner(System.in);
        System.out.println("请输入分数结束范围:");
        int end = scanner.nextInt();


        new BotScore(judgeNum,begin,end);


    }


}
