package com;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;


public class MainPage {
    public static Scanner s = new Scanner(System.in);
    public static MySqlAction sq = new MySqlAction();

    private void GoodsManager() {
        Scanner s = new Scanner(System.in);
        GsalesDao GSD = new GsalesDao();
        SalesManDao SMD = new SalesManDao();
        int select = 0;

        while (true)
        {
            System.out.println("执行商品管理");
            System.out.println("商超购物管理系统>>商品管理");
            System.out.println("*********************************");
            System.out.println("             1.列出当日卖出商品列表");
            System.out.println("             2.售货员管理");
            System.out.println("**********************************");
            System.out.println("请选择，输入数字或者按0返回上一级菜单");


            while (true) {
                try {
                    select = Integer.valueOf(s.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("输入数字！");
                }
            }

            if (select == 1){
                GSD.showSale();
                System.out.println("返回上一级菜单");
            } else if (select == 2){
                SMD.mainPage();
            } else if (select == 0){
                break;
            } else {
                System.out.println("请输入正确的数字！");
            }

        }

    }

    private void GoodMaintenance() {
        Scanner s = new Scanner(System.in);
        GoodsDao op = new GoodsDao();
        int select = 0;

        while (true)
        {
            System.out.println("执行显示商品维护菜单");
            System.out.println("商超购物管理系统>>商品维护");
            System.out.println("*********************************");
            System.out.println("             1.添加商品");
            System.out.println("             2.更改商品");
            System.out.println("             3.删除商品");
            System.out.println("             4.显示所有商品");
            System.out.println("             5.查询商品");
            System.out.println("**********************************");
            System.out.println("请选择，输入数字或者按0返回上一级菜单");


            while (true) {
                try {
                    select = Integer.valueOf(s.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("输入数字！");
                }
            }

            if (select == 1){
                op.addGoods();
                System.out.println("添加完成，返回上一级菜单");
            } else if (select == 2){
                op.alterGoods();
            } else if (select == 3){
                op.deleteGoods();
            } else if (select == 4){
                op.showGoods();
            } else if (select == 5){
                op.quaryGoods();
            } else if (select == 0){
                break;
            } else {
                System.out.println("请输入正确的数字！");
            }

        }

    }

    private void FrontEndLogin() {
        int select;
        int count = 0;
        String usr, pwd;

        while (true)
        {
            System.out.println("执行显示商品维护菜单");
            System.out.println("商超购物管理系统>>商品维护");
            System.out.println("*********************************");
            System.out.println("             1.登陆系统");
            System.out.println("             2.退出");
            System.out.println("**********************************");
            System.out.println("请选择，输入数字或者按2返回上一级菜单");


            while (true) {
                try {
                    select = Integer.valueOf(s.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("输入数字！");
                }
            }
            if (select == 1){
                while (true) {
                    System.out.print("请输入用户名:");
                    usr = s.nextLine();
                    System.out.println();
                    System.out.print("请输入密码:");
                    pwd = s.nextLine();
                    System.out.println();
                    try {
                        int SID = sq.loginCheck(usr, pwd);
                        if(SID == -1) {
                            count++;
                            System.out.println("用户名和密码不匹配！");
                            if(count == 3) {
                                System.out.println("错误3次，bye");
                                break;
                            }else {
                                System.out.println("你还有" + (3 - count) +"登陆机会");
                            }
                        } else {
                            System.out.println("登陆成功！");
                            GoodsDao op = new GoodsDao();
                            op.purchase(SID);
                            break;
                        }
                    }catch (Exception e ) {
                        e.printStackTrace();
                    }
                }
                System.out.println("返回上一级菜单");
                break;
            } else if (select == 2){
                System.out.println("返回上一层");
                break;
            } else {
                System.out.println("请输入正确的数字！");
            }

        }

    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        MainPage MP = new MainPage();

        MySqlAction sql = new MySqlAction();

        boolean tag = true;

        while(tag) {
            System.out.println("*********************************");
            System.out.println("             1.商品维护");
            System.out.println("             2.前台收银");
            System.out.println("             3.商品管理");
            System.out.println("*********************************");
            System.out.println("请选择，输入数字或者按0退出");

            int choise = 0;
            while (true) {
                try{
                    choise = Integer.valueOf(s.nextLine());
                    break;
                }catch (Exception e) {
                    System.out.println("输入错误，重新数字后按Enter键:");
                }
            }
            switch (choise){
                case 1:
                    MP.GoodMaintenance();
                    break;
                case 2:
                    MP.FrontEndLogin();
                    break;
                case 3:
                    MP.GoodsManager();
                    break;
                case 0:
                    tag = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("输入错误，请重新输入正确的数字:");
            }
        }
    }

}

