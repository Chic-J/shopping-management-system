package com;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class GoodsDao {

    public static Scanner s = new Scanner(System.in);
    public static MySqlAction sq = new MySqlAction();

    public void addGoods() {
        System.out.println("执行添加商品操作");
        System.out.println("商超购物管理系统>>商品维护>>添加商品");
        String name;
        double price;
        int number;
        char flag = 'y';

        while (flag == 'y') {

            while (true) {
                System.out.println("添加商品名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }else break;
            }

            while (true) {
                System.out.println("添加商品价格:");
                try {
                    price = Double.valueOf(s.nextLine());
                    break;
                }catch (Exception e) {
                    System.out.println("输入价格错误，重新输入");
                }
            }
            while (true) {
                System.out.println("添加商品数量:");
                try {
                    number = Integer.valueOf(s.nextLine());
                    break;
                }catch (Exception e) {
                    System.out.println("输入错误，重新输入");
                }
            }
            try {
                sq.addItem(name, price, number);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("继续添加(y/n):");
            while (true){
                try{
                    flag = s.nextLine().charAt(0);
                }catch (Exception e){
                    System.out.println("输入不能为空！");
                }
                if(flag == 'n' || flag == 'y')
                    break;
                else
                    System.out.println("输入错误！请重新输入：");
            }

        }


    }

    public void alterGoods() {
        System.out.println("执行更改商品操作");
        System.out.println("商超购物管理系统>>商品维护>>更改商品");
        String name;
        int modTag = 0;
        double price;
        int number;
        char flag = 'y';
        ResultSet rs = null;

        while (flag == 'y') {

            while (true) {
                System.out.println("更改商品名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }else break;
            }
            try {

                if(!sq.queryData(name, 1)){
                    System.out.println("查找结果为空，确认一下名字");
                    break;
                } else {

                    while (true) {
                        System.out.println("输入要修改的内容");
                        System.out.println("1.更改商品名称：");
                        System.out.println("2.更改商品价格：");
                        System.out.println("3.更改商品数量：");
                        try {
                            modTag = Integer.valueOf(s.nextLine());
                        } catch (Exception e) {
                            System.out.println("输入格式错误，重新输入");
                        }

                        if (modTag == 1) {
                            String newName;
                            while (true) {
                                System.out.println("请输入要更改商品名称");
                                newName = s.nextLine();
                                if (name.equals("")) {
                                    System.out.println("名字不能为空, 重新输入");
                                } else break;
                            }
                            sq.alterItem(1, name, newName);
                        }
                        else if (modTag == 2) {
                            System.out.println("请输入要更改商品价格");
                            while (true) {
                                try {
                                    price = Double.valueOf(s.nextLine());
                                    break;
                                } catch (Exception e) {
                                    System.out.println("输入错误！");
                                }

                            }
                            sq.alterItem(name, price);
                        }
                        else if (modTag == 3) {

                            System.out.println("请输入要更改商品数量");
                            while (true) {
                                try {
                                    number = Integer.valueOf(s.nextLine());
                                    break;
                                } catch (Exception e) {
                                    System.out.println("输入错误！");
                                }
                            }
                            sq.alterItem(name, number);
                        }
                        else {
                            System.out.println("输入错误，请重新操作！");
                            continue;
                        }
                        System.out.println("修改完成！");
                        break;
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("继续修改(y/n):");
            while (true){
                try{
                    flag = s.nextLine().charAt(0);
                }catch (Exception e){
                    System.out.println("输入不能为空！");
                }
                if(flag == 'n' || flag == 'y')
                    break;
                else
                    System.out.println("输入错误！请重新输入：");
            }
        }
    }

    public void deleteGoods() {
        System.out.println("执行删除商品操作");
        System.out.println("商超购物管理系统>>商品维护>>删除商品");
        String name;
        char delTag = 'y';
        double price;
        int number;
        char flag = 'y';
        ResultSet rs = null;



        while (flag == 'y') {
            while (true) {
                System.out.println("输入删除的商品名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }
                else
                    break;
            }

            try {
                if(!sq.queryData(name, 1)){
                    System.out.println("查找结果为空，返回上一级目录");
                    break;
                } else {

                    while (true) {
                        System.out.println("是否确定要删除(y/n)?");
                        try {
                            delTag = s.nextLine().charAt(0);
                        }
                        catch (Exception e){
                            System.out.println("输入不能为空！");
                        }
                        if(delTag == 'y') {
                            sq.delData(1, name);
                            break;
                        }
                        else if(delTag == 'n'){
                            break;
                        }
                        else {
                            System.out.println("输入错误！请重新输入：");
                            continue;
                        }
                    }

                }

            }catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("继续删除操作(y/n):");
            while (true){
                try{
                    flag = s.nextLine().charAt(0);
                }catch (Exception e){
                    System.out.println("输入不能为空！");
                }
                if(flag == 'n' || flag == 'y')
                    break;
                else
                    System.out.println("输入错误！请重新输入：");
            }

        }
    }

    public void showGoods() {
        System.out.println("商超购物管理系统>>商品维护>>显示所有商品");
        try {
            String note = "";
            sq.showData(1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("按回车键返回上一级");
        s.nextLine();
    }

    public void quaryGoods() {

        System.out.println("执行查询商品操作");
        System.out.println("商超购物管理系统>>商品维护>>查询商品");
        String name;
        int queTag = 0;
        double price;
        int number;
        char flag = 'y';
        ResultSet rs = null;

        try {
            while (flag == 'y') {
                System.out.println("1.按商品数量升序查询");
                System.out.println("2.按商品价格升序查询");
                System.out.println("3.输入关键字查询商品");
                //select actions
                while (true) {
                    try {
                        queTag = Integer.valueOf(s.nextLine());
                    } catch (Exception e) {
                        System.out.println("请输入数字");
                    }
                    if (queTag == 1) {
                        sq.showData(3);
                        break;
                    } else if (queTag == 2) {
                        sq.showData(2);
                        break;
                    } else if (queTag == 3) {
                        System.out.println("输入关键字：");
                        name = s.nextLine();
                        sq.fuzzyQueryData(name, 1);
                        break;
                    } else System.out.println("输入错误，请重新输入数字！");
                }


                System.out.println("继续查询(y/n)?:");
                while (true) {
                    try {
                        flag = s.nextLine().charAt(0);
                    } catch (Exception e) {
                        System.out.println("输入不能为空！");
                    }
                    if (flag == 'n' || flag == 'y')
                        break;
                    else
                        System.out.println("输入错误！请重新输入：");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchase(int SID) {
        Map<String,Integer> purchMap = new HashMap<>();
        Map<String,Integer> rsMap = new HashMap<>();
        Map<String,Integer> priceMap = new HashMap<>();
        Map<String,Integer> GIDMap = new HashMap<>();
        HashMap<String, List<Object>> map = new HashMap<>();
        String goodName = "";
        String name = "";
        int goodNum;
        int totalCost = 0;
        int money = 0;
        char flag = 'y';
        System.out.println("商品结算");

        System.out.println("请输入商品关键字：");
        name = s.nextLine();
        //process resultList
        //list seq: gid, price, num


        while (flag == 'y') {
            map = sq.fuzzyQueryData(name);
            System.out.println("请选择商品：");
            goodName = s.nextLine();
            if(goodName == "" || !map.containsKey(goodName)) {
                System.out.println("输入为空或者商品不存在，请重新输入");
                continue;
            }
            while (true) {
                System.out.println("请输入购买数量：");
                try {
                    goodNum = Integer.valueOf(s.nextLine());
                    if(goodNum > (int)(map.get(goodName).get(2))) {
                        System.out.println("数量过大，重新输入");
                        continue;
                    } else if(purchMap.containsKey(goodName)) {
                        purchMap.put(goodName, purchMap.get(goodName) - goodNum);
                        break;
                    } else {
                        purchMap.put(goodName, goodNum);
                        break;
                    }
                }catch (Exception e) {
                    System.out.println("数量输入错误，重新输入");
                }
            }

            System.out.println("继续添加(y/n):");
            while (true){
                try{
                    flag = s.nextLine().charAt(0);
                }catch (Exception e){
                    System.out.println("输入不能为空！");
                }
                if(flag == 'n' || flag == 'y')
                    break;
                else
                    System.out.println("输入错误！请重新输入：");
            }

        }
        try {

            HashMap<String, Integer> updateMap = new HashMap<>();
            List<String> gsalesList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : purchMap.entrySet()) {
                double cost = entry.getValue() * (double)map.get(entry.getKey()).get(1);
                System.out.println("商品 = " + entry.getKey() + ", 数量 = " + entry.getValue() + ", 价格 = " + cost);
                totalCost += cost;

                //save info into updateMap
                updateMap.put(entry.getKey(), (int)(map.get(entry.getKey()).get(2)) - entry.getValue());

                //save sql sentence into gsalesList
                gsalesList.add(String.format("INSERT INTO GSALES (SID, GID, SDATE, SNUM) values (%s, %s, %s, %s)",
                                SID,map.get(entry.getKey()).get(0),"now()",entry.getValue()));
            }

            System.out.println("总计：" + totalCost);

            while (true) {
                System.out.println("请输入实际交费金额：");
                money = Integer.valueOf(s.nextLine());
                if(money>=totalCost){
                    sq.addItem(updateMap);
                    sq.addItem(gsalesList);
                    System.out.println("找钱：" + (money-totalCost) );
                    System.out.println("谢谢光临！");
                    break;
                } else {
                    System.out.println("钱不够，重新输入");
                }

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
