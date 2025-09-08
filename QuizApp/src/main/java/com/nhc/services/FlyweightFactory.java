/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
public class FlyweightFactory {
    private static final Map<String, List> cacheData = new HashMap<>();
    
    public static <E> List<E> getData(BaseServices s, String key) throws SQLException{
        if(cacheData.containsKey(key)){
            return cacheData.get(key);
        }
        else{
            System.out.println(Math.random());
            List<E> re = s.list();
            
            cacheData.put(key, re);
            return re;
        }
    }
}
