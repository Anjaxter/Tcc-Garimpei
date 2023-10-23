package br.com.TccAndroid;

import android.content.Context;
import android.os.StrictMode;

import net.sourceforge.jtds.jdbc.Driver;

import java.security.Policy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar(){
        Connection conn = null;

        try{
            StrictMode.ThreadPolicy policy;
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            //dados da coneção
            String[] dadosconnHome = {"192.168.15.75:1433","teste","sa","andre123"};
            String[] dadosConnLocal = {"172.17.16.169:1433","teste","sa","andre123"};
            String[] dadosConnSomee = {"Garimpei-db.mssql.somee.com","Garimpei-db","AndreD_SQLLogin_2","3bk5l8en8z"};


            //variaveis com a url do sql
            String connHome = "jdbc:jtds:sqlserver://"+dadosconnHome[0]+";"+
                    "databaseName="+dadosconnHome[1]+";user="+dadosconnHome[2]+";password="+dadosconnHome[3]+"";
            String connLocal = "jdbc:jtds:sqlserver://"+dadosConnLocal[0]+";"+
                    "databaseName="+dadosConnLocal[1]+";user="+dadosConnLocal[2]+";password="+dadosConnLocal[3]+"";
            String connSomee = "jdbc:jtds:sqlserver://"+dadosConnSomee[0]+";"+
                    "databaseName="+dadosConnSomee[1]+";user="+dadosConnSomee[2]+";password="+dadosConnSomee[3]+"";

            conn = DriverManager.getConnection(connSomee);

        }catch (SQLException e){
            e.getMessage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
