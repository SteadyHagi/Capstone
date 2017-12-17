package com.thkang.svdr;

/**
 * Created by thkan on 2017-12-04.
 */

public class Contact  {
    String name, userid, password, phone, protectname;

    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getUserid(){
        return this.userid;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setProtectname(String protectname){
        this.protectname = protectname;
    }

    public String getProtectname(){
        return this.protectname;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
