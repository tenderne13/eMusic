package com.net.Entity;

public class Bot {
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bot bot = (Bot) o;

        return name != null ? name.equals(bot.name) : bot.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


    public static boolean equals(Object[] a,Object[] b){

        if(a.length!=b.length)
            return false;

        for (int i=0;i<(a.length<b.length?a.length:b.length);i++){
            if(a[i]==null && b[i] !=null)
                return false;
            if(!a[i].equals(b[i]))
                return false;
        }
        return true;
    }

    public static void main(String[] args){
        Bot bot1=new Bot();
        bot1.setName("bot1");
        Object[] obj1=new Object[2];
        obj1[0]=bot1;


        Bot bot2=new Bot();
        bot2.setName("bot1");
        Object[] obj2=new Object[2];
        obj2[0]=bot2;

        //boolean isEqual=bot1.equals(bot2);
        boolean isEqual=equals(obj1,obj2);
        System.out.println(isEqual);
    }
}
