package com.example.demo.mina;

/**
 * 概述：
 * 功能：自定义协议的消息体
 * 作者：郑肖亚
 * 创建时间：2019/3/5 8:57
 */
public class MyMsg {

    /**
     * 0x00表示请求
     */
    public static final byte REQUEST = 0x00;
    /**
     * 0x01表示回复
     */
    public static final byte RESPONSE = 0x01;

    /**
     * 消息长度
     */
    private int length;

    /**
     * 发送人
     */
   // private Long sender;

    /**
     * 接收人
     */
    //private Long receiver;

    /**
     * 消息内容
     */
    private String content;
    /**
     * 版本号
     */
    private byte flag;




    // 构造方法设置协议
    public MyMsg(byte flag,String content){
        this.flag=flag;
        this.content=content;
        this.length=1+4+(content == null ?0:content.getBytes().length);
    }
    // 无参构造
    public MyMsg() {

    }


    public byte getFlag() {
        return flag;
    }
    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MyMsg{" +
                "length=" + length +
                ", content='" + content + '\'' +
                ", flag=" + flag +
                '}';
    }
}
