package com.seek.friend.util.CommonUtil;

public class ConfigUtil {

    public static String addAddrAndWrap(String addr,String addAddr){
        return wrapAddr(addr+"."+addAddr);
    }

    public static String wrapAddr(String addr){
        return "${"+addr+"}";
    }
}
