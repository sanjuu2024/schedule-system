package com.atguigu.schedule.dao;

import com.atguigu.schedule.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    /**
     * 将下划线命名转换为驼峰命名
     * 例如：user_pwd -> userPwd
     */
    private String underlineToCamel(String underline) {
        if (underline == null || underline.isEmpty()) {
            return underline;
        }
        
        StringBuilder result = new StringBuilder();
        boolean toUpperCase = false;
        
        for (char c : underline.toCharArray()) {
            if (c == '_') {
                toUpperCase = true;
            } else {
                if (toUpperCase) {
                    result.append(Character.toUpperCase(c));
                    toUpperCase = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * 通用的增删改方法
     * @param sql 要执行的SQL语句
     * @param params 占位符要赋值的参数
     * @return 受影响的行数
     */
    public int executeUpdate(String sql, Object... params) throws Exception {
        // 1.通过JDBCUtil获取数据库连接
        Connection connection = JDBCUtil.getConnection();

        // 2.预编译SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 3.为占位符赋值、执行SQL、接受返回结果
        if (params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        int row = preparedStatement.executeUpdate();

        // 4.释放资源
        preparedStatement.close();
        if (connection.getAutoCommit()){   // 🍉必须当前事务是自动提交的才去释放！
            JDBCUtil.release();
        }

        // 5.返回结果
        return row;
    }

    /**
     * 🔺🔺🔺通用的查询方法：多行多列、单行多列、单行单列
     * @param sql 要执行的SQL语句
     * @param params 占位符要赋值的参数
     * @return 查询结果
     * 封装过程：
     *      1、返回的类型：泛型，调用时调用者将此次查询的结果类型告知BaseDAO就可以了。
     *      2、返回的结果：List
     *      3、结果的封装：🔺反射！（拿返回结果的列名作为类型）
     *                      要求调用者告知BaseDAO此次查询的结果类型。
     */
    public <T> List<T> executeQuery(Class<T> clazz,String sql, Object... params) throws Exception {
        // 1.获取连接
        Connection connection = JDBCUtil.getConnection();

        // 2.预编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 3.设置占位符的值
        if (params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

        // 4.执行sql，获取结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.🔺获取结果集中的元数据对象（包括了列的数量以及每列的名称）
        ResultSetMetaData metaData = resultSet.getMetaData();

        // 6.🔺处理结果集（封装结果）
        List<T> list = new ArrayList<>();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            // 循环一次，代表有一行数据，则通过反射创建一个对象
            T t = clazz.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= columnCount; i++) {
                // (1)通过下标获取该行该列的值(即该对象该属性的值)
                Object value = resultSet.getObject(i);

                // (2)通过下标获取该列的名字(即对象的属性字段的名字)
                String columnName = metaData.getColumnLabel(i);
                
                // (3)将数据库列名（下划线命名）转换为Java字段名（驼峰命名）
                String fieldName = underlineToCamel(columnName);

                // (4)然后通过反射，结合这个fieldName，获取该对象的该属性并且赋值
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    // 然后field很可能是private，所以还要取消属性的封装检查
                    field.setAccessible(true);
                    field.set(t, value);
                } catch (NoSuchFieldException e) {
                    // 如果找不到驼峰命名的字段，尝试使用原始列名
                    try {
                        Field field = clazz.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(t, value);
                    } catch (NoSuchFieldException ex) {
                        // 字段不存在，跳过该列
                        System.err.println("字段不存在: " + fieldName + " 或 " + columnName);
                    }
                }
            }
            list.add(t);
        }

        // 7.释放资源
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()){   // 🍉必须当前事务是自动提交的才去释放！
            JDBCUtil.release();
        }

        // 8.返回结果
        return list;
    }

    /**
     * 通用查询2，获取上面查询得到的集合结果中的第一个结果。简化单行单列 / 单行多列的获取
     * @param clazz 要查询的结果类型
     * @param sql 要执行的SQL语句
     * @param params 占位符要赋值的参数
     * @param <T> 泛型
     * @return  查询结果中的第一个结果
     */
    public <T> T executeQuerySingle(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> list = executeQuery(clazz, sql, params);
        if (list == null || list.isEmpty()){
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 通用查询3，获取单行单列的值
     * @param sql 要执行的SQL语句
     * @param params 占位符要赋值的参数
     * @return 查询到的单行单列的值
     */
    public <T> T executeQueryScalar(Class<T> clazz,String sql, Object... params) throws Exception {// 1.获取连接
        // 1.获取连接
        Connection connection = JDBCUtil.getConnection();

        // 2.预编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 3.设置占位符的值
        if (params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

        // 4.执行sql，获取结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        // 5.处理结果集
        resultSet.next();
        Object value = resultSet.getObject(1);

        // 6.释放资源
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()){   // 🍉必须当前事务是自动提交的才去释放！
            JDBCUtil.release();
        }

        // 7.返回结果
        return (T) value;
    }
}