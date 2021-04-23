package com;

import java.sql.*;
import java.util.Scanner;

public class GsalesDao {

    public static Scanner s = new Scanner(System.in);
    public static MySqlAction sq = new MySqlAction();

    public void showSale() {
        System.out.println("执行列出当日商品列表操作：");
        System.out.println("商超购物管理系统>>商品管理>>执行列出当日商品列表操作");
        System.out.println("今日售出商品：");

        sq.showData(new java.util.Date());

        System.out.println("回车后返回");
        s.nextLine();
    }


}
