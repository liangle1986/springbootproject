package com.springboot.springbootproject.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:梁乐乐
 * @Description: JDBC数据库连接公共类型
 * @params:
 * @return:
 * @Date: 2018/4/29 8:27
 */
public class DBUtil {
    //    oracle连接驱动
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    // jdbc:oracle:thin:@127.0.0.1:1521:ORCL
    private static final String ORACLE_URL = "jdbc:oracle:thin:@%s:%s:%s";

    //mysql的链接驱动
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    // jdbc:mysql://127.0.0.1:3306/mywuwu?useUnicode=true&characterEncoding=utf-8&useSSL=false
    private static final String MYSQL_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    //sql server的链接驱动
    private static final String SQL_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

    // jdbc:mysql://127.0.0.1:3306/mywuwu?useUnicode=true&characterEncoding=utf-8&useSSL=false
    private static final String SQL_URL = "jdbc:microsoft:sqlserver://%s:%s;DatabaseName=%s";

    //连接地址
    private String sHost = "127.0.0.1";
    //端口好
    private String sPort = "3306";
    //数据库名称
    private String sDBName = "mywuwu";
    //    用户名称
    private String sUName = "mywuwu";
    //密码
    private String sPWD = "le5201314";
    //链接地址
    private String sURL = MYSQL_URL;

    //链接服务
    private String sDRIVER = MYSQL_DRIVER;


    // 用户缓存的变量
    private String sHostUser = "127.0.0.1";
    private String sPortUser = "1521";
    private String sDBNameUser = "ORCL";
    private String sUNameUser = "UserName";
    private String sPWDUser = "password";

    //链接地址
    private String sURLUser = MYSQL_URL;

    //链接服务
    private String sDRIVERUser = MYSQL_DRIVER;

    /**
     * 初始化用户配置信息
     */
    public void initUserConfig() {
        this.sHostUser = this.sHost;
        this.sPortUser = this.sPort;
        this.sDBNameUser = this.sDBName;
        this.sUNameUser = this.sUName;
        this.sPWDUser = this.sPWD;
        this.sURLUser = this.sURL;
        this.sDRIVERUser = this.sDRIVER;
    }


    /**
     * jdbc 命令处理
     */

    private PreparedStatement ps = null;

    /**
     * 使用用户配置切换数据源
     *
     * @return true:切换成功 false:切换失败
     */
    public boolean changeDBSourceByUser() {
        return this.changeDBSource(sHostUser, sPortUser, sDBNameUser,
                sUNameUser, sPWDUser);
    }

    /**
     * 切换数据源
     *
     * @param sHost   新数据源主机
     * @param sPort   新数据源端口
     * @param sDBName 新数据源数据库名
     * @param sUName  新数据源用户名
     * @param sPWD    新数据源密码
     * @return true:切换成功 false:切换失败
     */
    public boolean changeDBSource(String sHost, String sPort, String sDBName,
                                  String sUName, String sPWD) {
        if (this.checkConnConfig(sHost, sPort, sDBName, sUName, sPWD)) {
            this.sHost = sHost;
            this.sPort = sPort;
            this.sDBName = sDBName;
            this.sUName = sUName;
            this.sPWD = sPWD;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用传入的字符串获取一个数据库连接
     *
     * @param sHost   主机
     * @param sPort   端口
     * @param sDBName 数据库名
     * @param sUName  用户名
     * @param sPWD    密码
     * @return 数据库连接对象，如果获取失败返回null
     */

    public Connection getConn(String sHost, String sPort, String sDBName,
                              String sUName, String sPWD) {
        try {
            // 使用变量拼接连接串
            String sUrl = String.format(sURLUser, sHost, sPort, sDBName);
            // 初始化驱动 这里只支持oracle，可以做兼容其他数据库，通过配置增加对应代码即可
            Class.forName(sDRIVERUser);
            // 获取连接
            return DriverManager.getConnection(sUrl, sUName, sPWD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据预定连接串获取一个数据库连接
     *
     * @return 数据库连接对象，如果获取失败返回null
     */
    public Connection getConn() {
        try {
            // 使用变量拼接连接串
            String sUrl = String.format(sURLUser, sHost, sPort, sDBName);
            // 初始化驱动 这里只支持oracle，可以做兼容其他数据库，通过配置增加对应代码即可
            Class.forName(sDRIVERUser);
            // 获取连接
            return DriverManager.getConnection(sUrl, sUName, sPWD);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据传入的参数，验证连接是否可用
     *
     * @param sHost   主机
     * @param sPort   端口
     * @param sDBName 数据库名称
     * @param sUName  用户名
     * @param sPWD    密码
     * @return true:数据库连接正常 false:数据库连接异常
     */
    public boolean checkConnConfig(String sHost, String sPort, String sDBName,
                                   String sUName, String sPWD) {
        Connection conn = null;
        try {
            conn = this.getConn(sHost, sPort, sDBName, sUName, sPWD);
            return conn != null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 验证当前数据库连接配置是否可用
     *
     * @return true:数据库连接正常 false:数据库连接异常
     */
    public boolean checkConnConfig() {
        Connection conn = null;
        try {
            conn = this.getConn();
            return conn != null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getsHostUser() {
        return sHostUser;
    }

    public void setsHostUser(String sHostUser) {
        this.sHostUser = sHostUser;
    }

    public String getsPortUser() {
        return sPortUser;
    }

    public void setsPortUser(String sPortUser) {
        this.sPortUser = sPortUser;
    }

    public String getsDBNameUser() {
        return sDBNameUser;
    }

    public void setsDBNameUser(String sDBNameUser) {
        this.sDBNameUser = sDBNameUser;
    }

    public String getsUNameUser() {
        return sUNameUser;
    }

    public void setsUNameUser(String sUNameUser) {
        this.sUNameUser = sUNameUser;
    }

    public String getsPWDUser() {
        return sPWDUser;
    }

    public void setsPWDUser(String sPWDUser) {
        this.sPWDUser = sPWDUser;
    }


    /**
     * @param sql 需要执行的sql
     * @Author:梁乐乐
     * @Description: 根据sql返回list值
     * @return:
     * @Date: 2018/4/29 21:14
     */
    public List<Map<String, Object>> getDBSqlByList(String sql) {

        //返回值
        List<Map<String, Object>> resultList = new ArrayList<>();

        //链接对象
        Connection conn = null;

        //结果集对象
        ResultSet colRet = null;

        try {
            //获取链接
            conn = this.getConn();

            //执行sql
            ps = conn.prepareStatement(sql);

            //获取结果
            colRet = ps.executeQuery();

            if (colRet != null) {

                // 获取列名
                ResultSetMetaData metaData = colRet.getMetaData();

                while (colRet.next()) {
                    Map<String, Object> resultMap = new HashMap<>();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        // resultSet数据下标从1开始
                        resultMap.put(metaData.getColumnName(i + 1), colRet.getString(i + 1));

                    }
                    resultList.add(resultMap);
                }
                return resultList;
            }
        } catch (Exception e) {
            try {
                conn.close();
                ps.close();
                colRet.close();
            } catch (SQLException el) {

            }
        } finally {
            try {
                conn.close();
                ps.close();
                colRet.close();
            } catch (SQLException el) {

            }
        }
        return null;
    }


    /**
     * @Author:梁乐乐
     * @Description: 根据sql 查询数量
     * @param sql 需要执行的sql
     * @return: 单个数字类型
     * @Date: 2018/4/29 21:58
     */
    public int getDBSqlByInt(String sql) {
        List<Map<String, Object>> resultList = this.getDBSqlByList(sql);
        int resultValue = 0;
        if (resultList != null && resultList.size() > 0) {
            Map<String, Object> map = resultList.get(0);
            if (map != null && !map.isEmpty()) {
                String value = "";
                for (String key : map.keySet()) {
                    value = map.get(key).toString();
                    break;
                }
                if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {
                    resultValue = Integer.valueOf(value);
                }
            }
        }
        return resultValue;
    }

}
