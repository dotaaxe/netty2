package com.cn.netty.netty.model;

/**
 * * 自定义协议
 * @author xzy.ajiu
 * Created by on @date 2020/12/24 9:41
 */
public class SmartIotProtocol {

    /**
     * 协议最短长度 30 字节
     */
    public static int MIN_LEN = 30;

    /**
     * 数据包启动符号 @@
     */
    public static short START = 25700;

    /**
     * 业务流水号
     */
    private short flowid;
    /**
     * 主版本
     */
    private byte version_major;
    /**
     * 次版本
     */
    private byte version_minor;
    /**
     * 秒
     */
    private byte second;
    /**
     * 分钟
     */
    private byte minute;
    /**
     * 小时
     */
    private byte hour;
    /**
     * 日
     */
    private byte day;
    /**
     * 月
     */
    private byte month;
    /**
     * 年
     */
    private byte year;
    /**
     * 数据包的源地址
     */
    private byte[] src;
    /**
     * 数据包的目的地址
     */
    private byte[] dest;
    /**
     * 应用数据单元长度 长度不应大于1024；低字节传输在前
     */
    private short data_len;
    /**
     * 命令字节 为控制单元的命令字节
     */
    private byte cmd;
    /**
     * 应用数据单元  对于确认/否认等命令包，此单元可为空
     */
    private byte[] data;
    /**
     * 校验和 控制单元中各字节数据（第3～第27字节）及应用数据单元的算术校验和，舍去8位以上的进位位后所形成的1字节二进制数
     */
    private byte checksum;
    /**
     * 协议结束符号 ##
     */
    public static short END = 13621;

    /**
     * 打印调试信息
     */
    public void printDebugInfo(){
        System.out.println("---------完整数据包开始------------");
        System.out.println("|开始标志: " + printHexShort(START));
        System.out.println("|业务流水: " + printHexShort(flowid) + "\tFlowID:" + flowid);
        System.out.println("|协议版本: " + printHexByte(version_major) + printHexByte(version_minor));
        System.out.println("|时间标签: " + "20" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        System.out.println("|源地址  : " + printHexBytes(src));
        System.out.println("|目的地址: " + printHexBytes(dest));
        System.out.println("|数据长度: " + data_len);
        System.out.println("|命令字节: " + printHexByte(cmd));
        System.out.println("|应用数据: " + printHexBytes(data));
        System.out.println("|校验字节: " + printHexByte(checksum));
        System.out.println("|结束标志: " + printHexShort(END));
        System.out.println("---------------------------------");
    }
    private String printHexByte(byte b){
        return String.format("%02X", b);
    }
    private String printHexBytes(byte[] bytes){
        String str = "";
        for(int i=0; i<bytes.length; i++){
            str += String.format("%02X", bytes[i]);
        }
        return str;
    }
    private String printHexShort(int s){
        byte[] bytes = hexShort(s);
        return printHexBytes(bytes);
    }
    private byte[] hexShort(int s){
        byte[] bytes = new byte[2];
        bytes[0] = (byte)((s << 24) >> 24);
        bytes[1] = (byte)((s << 16) >> 24);
        return bytes;
    }
    private byte[] hexInt(int n){
        byte[] bytes = new byte[4];
        bytes[3] = (byte) ((n      ) >> 24);
        bytes[2] = (byte) ((n <<  8) >> 24);
        bytes[1] = (byte) ((n << 16) >> 24);
        bytes[0] = (byte) ((n << 24) >> 24);
        return bytes;
    }

    public short getFlowid() {
        return flowid;
    }
    public void setFlowid(short flowid) {
        this.flowid = flowid;
    }
    public byte getVersion_major() {
        return version_major;
    }
    public void setVersion_major(byte version_major) {
        this.version_major = version_major;
    }
    public byte getVersion_minor() {
        return version_minor;
    }
    public void setVersion_minor(byte version_minor) {
        this.version_minor = version_minor;
    }
    public byte getSecond() {
        return second;
    }
    public void setSecond(byte second) {
        this.second = second;
    }
    public byte getMinute() {
        return minute;
    }
    public void setMinute(byte minute) {
        this.minute = minute;
    }
    public byte getHour() {
        return hour;
    }
    public void setHour(byte hour) {
        this.hour = hour;
    }
    public byte getDay() {
        return day;
    }
    public void setDay(byte day) {
        this.day = day;
    }
    public byte getMonth() {
        return month;
    }
    public void setMonth(byte month) {
        this.month = month;
    }
    public byte getYear() {
        return year;
    }
    public void setYear(byte year) {
        this.year = year;
    }
    public byte[] getSrc() {
        return src;
    }
    public void setSrc(byte[] src) {
        this.src = src;
    }
    public byte[] getDest() {
        return dest;
    }
    public void setDest(byte[] dest) {
        this.dest = dest;
    }
    public short getData_len() {
        return data_len;
    }
    public void setData_len(short data_len) {
        this.data_len = data_len;
    }
    public byte getCmd() {
        return cmd;
    }
    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public byte getChecksum() {
        return checksum;
    }
    public void setChecksum(byte checksum) {
        this.checksum = checksum;
    }

}
