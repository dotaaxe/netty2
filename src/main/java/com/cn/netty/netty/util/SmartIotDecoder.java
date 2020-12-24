package com.cn.netty.netty.util;



import java.util.List;

import com.cn.netty.netty.model.SmartIotProtocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 自定义协议解析解码器
 * @author xzy.ajiu
 * Created by on @date 2020/12/24 9:42
 */
public class SmartIotDecoder extends ByteToMessageDecoder {


    private static final Logger log = LoggerFactory.getLogger(SmartIotDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        log.debug("启动解码器...");
        log.debug("目前数据缓存大小: " + buffer.readableBytes());
        // 刻度长度必须大于基本最小长度
        if(buffer.readableBytes() >= SmartIotProtocol.MIN_LEN){
            log.debug("符合最小长度，进行解析");
            //防止socket字节流攻击、客户端传来的数据过大，这里需要对数据进行过滤掉
            if(buffer.readableBytes() >= 4096){
                buffer.skipBytes(buffer.readableBytes());
                return ;
            }

            //记录包头开始位置
            int beginReader = 0;
            while(true){
                beginReader = buffer.readerIndex(); //记录包头开始位置
                buffer.markReaderIndex(); //标记包头开始index
                //读取协议开始标志
                if(buffer.readShort() == SmartIotProtocol.START){
                    break; //如果是开始标记，那么就结束查找
                }

                //如果找不到包头，这里要一个一个字节跳过
                buffer.resetReaderIndex();
                buffer.readByte();

                //当跳过后，如果数据包又不符合长度的，结束本次协议解析
                if(buffer.readableBytes() < SmartIotProtocol.MIN_LEN){
                    return ;
                }
            }

            short flowid = buffer.readShort();
            byte version_major = buffer.readByte();
            byte version_minor = buffer.readByte();
            byte second = buffer.readByte();
            byte minute = buffer.readByte();
            byte hour = buffer.readByte();
            byte day = buffer.readByte();
            byte month = buffer.readByte();
            byte year = buffer.readByte();
            byte[] src = new byte[6];
            src[0] = buffer.readByte();
            src[1] = buffer.readByte();
            src[2] = buffer.readByte();
            src[3] = buffer.readByte();
            src[4] = buffer.readByte();
            src[5] = buffer.readByte();
            byte[] dest = new byte[6];
            dest[0] = buffer.readByte();
            dest[1] = buffer.readByte();
            dest[2] = buffer.readByte();
            dest[3] = buffer.readByte();
            dest[4] = buffer.readByte();
            dest[5] = buffer.readByte();
            short data_len = buffer.readShort();
            if(buffer.readableBytes() < data_len + 4){
                //还原读指针
                buffer.readerIndex(beginReader);
                return ;
            }
            byte cmd = buffer.readByte();
            byte[] data = null;
            if(data_len > 0){
                //读取应用数据单元
                data = new byte[data_len];
                buffer.readBytes(data);
            }

            byte checksum = buffer.readByte();
            short end = buffer.readShort();

            if(end == SmartIotProtocol.END){
                log.debug("完成解析，并输出.");
                SmartIotProtocol iot = new SmartIotProtocol();
                iot.setFlowid(flowid);
                iot.setVersion_major(version_major);
                iot.setVersion_minor(version_minor);
                iot.setSecond(second);
                iot.setMinute(minute);
                iot.setHour(hour);
                iot.setDay(day);
                iot.setMonth(month);
                iot.setYear(year);
                iot.setSrc(src);
                iot.setDest(dest);
                iot.setData_len(data_len);
                iot.setCmd(cmd);
                if(data_len > 0){
                    iot.setData(data);
                }else{
                    iot.setData(null);
                }
                iot.setChecksum(checksum);
                out.add(iot);
            }
        }
    }

}
