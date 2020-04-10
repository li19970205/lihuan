package com.example.demo_mysql.entity;

public  class Result {
    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result failure(String message){
        return new Result("fail",message,false);
    }

   public  Result(String status, String msg, boolean isLogin){
      this(status,msg,isLogin,null);
    }

    public Result(String status,String msg, boolean isLogin, Object data){
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getMsg(){ return msg; }

    public boolean isLogin(){ return isLogin;}

    public Object getData(){ return data;}
}
