package com;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesManDao {
    public static Scanner s = new Scanner(System.in);
    public static MySqlAction sq = new MySqlAction();

    public void mainPage() {
        System.out.println("执行售货员管理操作");

        int select = 0;
        while (true)
        {
            System.out.println("商超购物管理系统>>商品管理>>售货员管理");
            System.out.println("*********************************");
            System.out.println("             1.添加售货员");
            System.out.println("             2.更改售货员");
            System.out.println("             3.删除售货员");
            System.out.println("             4.显示所有售货员");
            System.out.println("             5.查询售货员");
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
                addSalesMan();
                System.out.println("添加完成，返回上一级菜单");
            } else if (select == 2){
                alterSalesMan();
            } else if (select == 3){
                deleteSalesMan();
            } else if (select == 4){
                showSalesMan();
            } else if (select == 5){
                querySalesMan();
            } else if (select == 0){
                break;
            } else {
                System.out.println("请输入正确的数字！");
            }

        }

    }

    public void addSalesMan() {
        System.out.println("执行添加售货员操作");
        System.out.println("商超购物管理系统>>商品管理>>售货员管理>>添加售货员");
        String name;
        String password;

        char flag = 'y';

        while (flag == 'y') {

            while (true) {
                System.out.println("添加售货员名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }else break;
            }

            while (true) {
                System.out.println("添加售货员密码:");
                try {
                    password = s.nextLine();
                    break;
                }catch (Exception e) {
                    System.out.println("输入错误，重新输入");
                }
            }
            try {
                sq.addItem(name, password);
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

    public void alterSalesMan() {
        System.out.println("执行更改售货员操作");
        System.out.println("商超购物管理系统>>商品管理>>售货员管理>>更改售货员");
        String name;
        int modTag = 0;
        String password;
        char flag = 'y';

        while (flag == 'y') {

            while (true) {
                System.out.println("更改售货员名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }else break;
            }
            try {

                if(!sq.queryData(name, 2)){
                    System.out.println("查找结果为空，确认一下名字");
                    break;
                } else {
                    while (true) {
                        System.out.println("输入要修改的内容");
                        System.out.println("*********************************");
                        System.out.println("1.更改售货员姓名");
                        System.out.println("2.更改售货员密码");
                        System.out.println("*********************************");
                        System.out.println("请选择，输入数字或者按0返回上一级菜单");
                        try {
                            modTag = Integer.valueOf(s.nextLine());
                        } catch (Exception e) {
                            System.out.println("输入格式错误，重新输入");
                        }

                        if (modTag == 1) {
                            String newName;
                            while (true) {
                                System.out.println("请输入要更改的姓名");
                                newName = s.nextLine();
                                if (name.equals("")) {
                                    System.out.println("名字不能为空, 重新输入");
                                } else break;
                            }
                            sq.alterItem(2, name, newName);
                        }
                        else if (modTag == 2) {

                            System.out.println("请输入要更改的密码");
                            while (true) {
                                password = s.nextLine();
                                if (password.equals("")) {
                                    System.out.println("密码不能为空, 重新输入");
                                } else break;
                            }
                            sq.alterItem(2, name, password);
                        } else if (modTag == 0) break;
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

    public void deleteSalesMan() {
        System.out.println("执行删除售货员操作");
        System.out.println("商超购物管理系统>>商品管理>>售货员管理>>删除售货员");
        String name;
        char delTag = 'y';
        char flag = 'y';
        ResultSet rs = null;

        while (flag == 'y') {

            while (true) {
                System.out.println("输入删除的售货员名称：");
                name = s.nextLine();
                if(name.equals("")) {
                    System.out.println("名字不能为空, 重新输入");
                }
                else
                    break;
            }

            try {

                if(!sq.queryData(name, 2)){
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
                            sq.delData(2, name);
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

            System.out.println("继续删除操作(y/n)?: ");

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

    public void showSalesMan() {
        System.out.println("商超购物管理系统>>商品管理>>售货员管理>>显示所有售货员");
        try {
            sq.showData(4);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("按回车键返回上一级");
        s.nextLine();
    }

    public void querySalesMan() {
        System.out.println("执行查询售货员操作");
        System.out.println("商超购物管理系统>>商品管理>>售货员管理>>查询售货员");
        String name;
        char flag = 'y';

        try {
            while (flag == 'y') {
                System.out.println("输入售货员名字关键字查询：");
                name = s.nextLine();
                sq.fuzzyQueryData(name, 2);
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
}
