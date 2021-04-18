package com.hyf.shiro.web.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Nameable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baB_hyf
 * @date 2021/04/18
 */
public class IniObject implements Nameable {

    private String              string          = "";
    private List<String>        stringList      = new ArrayList<>();
    private Map<String, String> stringMap       = new HashMap<>();
    private SecurityManager     securityManager = null;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Override
    public void setName(String s) {
        System.out.println(" --> IniObject --> name: " + s);
    }

    @Override
    public String toString() {
        return "IniObject{" +
                "string='" + string + '\'' +
                ", stringList=" + stringList +
                ", stringMap=" + stringMap +
                ", securityManager=" + securityManager +
                '}';
    }
}
