package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * MySql操作
 * @author ccj
 */
public class MySqlAction {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Shoppingms?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
    static final String USER = "root";
    static final String PWD = "";
    static PreparedStatement stmt = null;
    static Connection conn = null;
    static ResultSet rs = null;


    private void dbConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dbClose() {
        try {
            if(conn != null) conn.close();
            if(stmt != null) stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add goods to table Goods
     * @param name
     * @param price
     * @param number
     */
    public void addItem(String name, double price, int number) {
        try {
            dbConnect();
            stmt = conn.prepareStatement("insert into GOODS(gname, gprice, gnum) VALUES (?, ?, ?);");
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, number);
            stmt.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * add salesman to table Salesman
     * @param name
     * @param password
     */
    public void addItem(String name, String password) {
        try {
            dbConnect();
            stmt = conn.prepareStatement("insert into SALESMAN(SNAME, SPASSWORD) VALUES (?, ?);");
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * change goods' number after purchases
     * @param updateMap including all the purchase info.
     */
    public void addItem(Map<String,Integer> updateMap) {
        try {
            dbConnect();
            stmt = conn.prepareStatement("UPDATE GOODS SET GNUM = ? WHERE GNAME = ?");

            for (Map.Entry<String, Integer> entry : updateMap.entrySet()){
                stmt.setInt(1, entry.getValue());
                stmt.setString(2, entry.getKey());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * add info. to table Gsale
     * @param gsalesList including all sql sentences needed
     */
    public void addItem(List<String> gsalesList) {
        try {
            dbConnect();
            for (String sql : gsalesList){
                stmt = conn.prepareStatement(sql);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * add item to table GSALES
     * @param SID
     * @param GID
     * @param date
     * @param number
     */
    public void addItem(int SID, int GID, java.util.Date date, int number) {
        try {
            dbConnect();
            stmt = conn.prepareStatement("INSERT INTO GSALES (SID, GID, SDATE, SNUM) values (?, ?, ?, ?)");
            stmt.setInt(1, SID);
            stmt.setInt(2, GID);
            stmt.setDate(3, new java.sql.Date(date.getTime()));
            stmt.setInt(4, number);
            stmt.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * alter val for goods or salesman
     * @param method
     *          method = 1 : goods name change
     *          method = 2 : salesman name change
     *          method = 3 : salesman password change
     * @param oldStr
     * @param newStr
     */
    public void alterItem(int method, String oldStr, String newStr) {
        try {
            dbConnect();
            if(method == 1) {
                stmt = conn.prepareStatement("UPDATE GOODS SET GNAME = ? where Gname = ?");
            } else if (method == 2){
                stmt = conn.prepareStatement("UPDATE SALESMAN SET SNAME = ? where SNAME = ?");
            } else {
                stmt = conn.prepareStatement("UPDATE SALESMAN SET SPASSWORD = ? where SNAME = ?");
            }

            stmt.setString(1, newStr);
            stmt.setString(2, oldStr);
            stmt.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * alter number for good
     * use for one good
     * @param goodName
     * @param newNum
     */
    public void alterItem(String goodName, int newNum) {

        try {
            dbConnect();
            stmt = conn.prepareStatement("UPDATE GOODS SET GNUM = ? where Gname = ?");

            stmt.setInt(1, newNum);
            stmt.setString(2, goodName);
            stmt.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * alter price for goods
     * @param goodName
     * @param newPrice
     */
    public void alterItem(String goodName, double newPrice) {

        try {
            dbConnect();
            stmt = conn.prepareStatement("UPDATE GOODS SET GPRICE = ? where Gname = ?");

            stmt.setDouble(1, newPrice);
            stmt.setString(2, goodName);
            stmt.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * select items with name and show data if result is not null
     * @param name
     * @param method
     *        method = 1: select from table GOODS
     *        method = 2: select from table SALESMAN
     * @return
     *      if (resultSet is not null) true
     *      else false
     */
    public boolean queryData(String name, int method) {

        String sql1 = "select * from GOODS where GNAME = ?";
        String sql2 = "select * from SALESMAN where SNAME = ?";
        try {
            dbConnect();
            if(method == 1) {
                stmt = conn.prepareStatement(sql1);
            } else stmt = conn.prepareStatement(sql2);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if(!rs.next()) return false;
            else if(method == 1) showResult(rs);
            else showSaleManResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose();
        }
        return true;
    }

    /**
     * shows table's data by some way
     * @param method
     *          method= 1 : show GOODS's data
     *          method= 2 : show GOODS's data ordered by price
     *          method= 3 : show GOODS's data ordered by number
     *          method= 4 : show SALESMAN's data
     */
    public void showData(int method) {

        String sql = null;
        switch (method) {
            case 1: sql = "select * from GOODS";
                    break;
            case 2: sql = "SELECT * FROM GOODS ORDER BY GPRICE";
                    break;
            case 3: sql = "SELECT * FROM GOODS ORDER BY GNUM";
                    break;
            case 4: sql = "SELECT * FROM SALESMAN";
                    break;
        }
        try {
            dbConnect();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(method ==4) showSaleManResult(rs);
            else    showResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose();
        }
    }

    /**
     * select and show data with fuzzy selection(Using % )
     *
     * @param name
     * @param method
     *          method = 1 : select data from table GOODS fuzzily with name
     *          method = 2 : select data from table SALESMAN fuzzily with name
     */
    public void fuzzyQueryData(String name, int method) {

        String sql1 = "SELECT * FROM GOODS WHERE GNAME LIKE \"%\"?\"%\"";
        String sql2 = "SELECT * FROM SALESMAN WHERE SNAME LIKE \"%\"?\"%\"";

        try {
            dbConnect();
            if(method == 1) stmt = conn.prepareStatement(sql1);
            else stmt = conn.prepareStatement(sql2);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if(method == 1) showResult(rs);
            else showSaleManResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose();
        }

    }

    /**
     * select and show data from table SALESMAN fuzzily with name
     * @param name
     * @return map<name, [id, price, number]>
     */
    public HashMap<String, List<Object>> fuzzyQueryData(String name) {
        String sql1 = "SELECT * FROM GOODS WHERE GNAME LIKE \"%\"?\"%\"";
        HashMap<String, List<Object>> map = new HashMap<>();
        try {
            dbConnect();
            stmt = conn.prepareStatement(sql1);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            showResult(rs);
            rs.beforeFirst();
            while (rs.next()) {
                map.put(rs.getString("GNAME"), List.of(rs.getInt("GID"), rs.getDouble("GPRICE"), rs.getInt("GNUM")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose();
        }
        return map;
    }

    /**
     * show sales' data in today
     * @param date
     */
    public void showData(java.util.Date date){
        dbConnect();
        try {
            stmt = conn.prepareStatement("SELECT a.GNAME, a.GPRICE, a.GNUM, b.SNUM, b.SDATE FROM GOODS a RIGHT JOIN GSALES b on a.GID = b.GID WHERE b.SDATE = ?");
            stmt.setDate(1, new java.sql.Date(date.getTime()));
            rs = stmt.executeQuery();
            showSellResult(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            dbClose();
        }

    }

    /**
     * del data with name
     * @param method
     *          method = 1: Delete from GOODS
     *          method = 2: delete from SALESMAN
     * @param name
     */
    public void delData(int method, String name) {
        try {
            dbConnect();
            if(method == 1) {
                stmt = conn.prepareStatement("DELETE FROM GOODS WHERE GNAME = ?;");
            } else if(method == 2) {
                stmt = conn.prepareStatement("DELETE FROM SALESMAN WHERE SNAME = ?;");
            }
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.setString(1, name);
            stmt.execute();
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
    }

    /**
     * login check
     * @param usr
     * @param pwd
     * @return
     *          -1 for fail
     *          SID for success
     */
    public int loginCheck(String usr, String pwd) {
        int SID = -1;
        try {
            dbConnect();
            stmt = conn.prepareStatement("SELECT * FROM SALESMAN WHERE SNAME = ? AND SPASSWORD = ?");
            stmt.setString(1, usr);
            stmt.setString(2, pwd);
            rs = stmt.executeQuery();
            if(!rs.next()) return SID;
            else SID = rs.getInt("SID");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbClose();
        }
        return SID;
    }

    /**
     * show Goods' data in format
     * @param rs
     * @throws SQLException
     */
    private void showResult(ResultSet rs) throws SQLException {
        //show result
        try {
            rs.beforeFirst();
            System.out.println(String.format("%-15s%-10s%-10s%-10s", "Name","Price","Number","Note"));
            System.out.println("-----------------------------------------------------");
            String note = "";
            while(rs.next()) {
                note = rs.getInt("GNUM") < 10? "*该商品已不足10件":" ";
                System.out.println(String.format("%-15s%-10.2f%-10d%-10s", rs.getString("GNAME"), rs.getDouble("GPRICE"), rs.getInt("GNUM"), note));
            }
            System.out.println("-----------------------------------------------------");
        } catch (Exception e )  {
            e.printStackTrace();
        }

    }

    /**
     * show SALEMAN info in format
     * @param rs
     * @throws SQLException
     */
    private void showSaleManResult(ResultSet rs) throws SQLException {
        //show result
        try {
            rs.beforeFirst();
            System.out.println(String.format("%-15s%15s", "name", "password"));
            System.out.println("------------------------------------------------");
            while (rs.next()){
                System.out.println(String.format("%-15s%15s", rs.getString("SNAME"), rs.getString("SPASSWORD")));
            }
            System.out.println("------------------------------------------------");
        } catch (Exception e )  {
            e.printStackTrace();
        }

    }

    /**
     * show today's sales info.
     * @param rs
     */
    private void showSellResult(ResultSet rs) {
        //show sell result
        try {
            rs.beforeFirst();
            System.out.println(String.format("%-15s%-10s%-10s%-12s%-10s", "Name","Price","Number", "SalesNum", "Note"));
            System.out.println("-----------------------------------------------------");
            String note = "";
            while(rs.next()) {
                note = rs.getInt("GNUM") < 10? "*该商品已不足10件":" ";
                System.out.println(String.format("%-15s%-10.2f%-10d%-12s%-10s", rs.getString("GNAME"), rs.getDouble("GPRICE"), rs.getInt("GNUM"), rs.getInt("SNUM"), note));
            }
        } catch (Exception e )  {
            e.printStackTrace();
        }
    }
}
