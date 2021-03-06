package com.ltw.device;

import com.ltw.device.event.DeviceDateEvent;
import com.ltw.utils.SpringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class YCHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Value("${ycdevice.checkCrc}")
    private boolean checkcrc;
    public static BidiMap bidiMap = new DualHashBidiMap();
    public static Map<String, Map<String, Object>> devMap = new HashMap();
    public static Map<String, ChannelHandlerContext> ctxMap = new HashMap();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String id = ctx.channel().id().toString();
        synchronized(YCHandler.class) {
            ctxMap.put(id, ctx);
            System.out.println("新建链接,连接数: " + ctxMap.size());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String id = ctx.channel().id().toString();
        synchronized(YCHandler.class) {
            Map<String, Object> map = (Map)devMap.get(id);
            if (map != null) {
                map.put("status", "offline");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("offLineTime", sdf.format(new Date()));
            }
            ctxMap.remove(id);
            bidiMap.remove(id);
            System.out.println("链路异常,关闭链接,剩余连接数 : " + ctxMap.size());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String id = ctx.channel().id().toString();
        synchronized(YCHandler.class) {
            String mn = (String)bidiMap.get(id);
            Map<String, Object> map = (Map)devMap.get(mn);
            if (map != null) {
                map.put("status", "offline");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put("offLineTime", sdf.format(new Date()));
            }
            ctxMap.remove(id);
            bidiMap.remove(id);
            System.out.println("关闭一个链接,剩余连接数 : " + ctxMap.size());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf m) throws Exception {
        byte[] data = new byte[m.readableBytes()];
        m.readBytes(data);
        String msg = new String(data, CharsetUtil.UTF_8);
        SocketAddress address = ctx.channel().remoteAddress();
        System.out.println("client段发的数据时间是:"+address.toString().substring(1) + "---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("client段发的数据是:"+msg);
        SpringUtils.getApplicationContext().publishEvent(new DeviceDateEvent(this, msg));
    }

}
