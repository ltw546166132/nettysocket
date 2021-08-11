package com.ltw.device;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ltw.device.common.CommonConfig;
import com.ltw.device.common.enums.YCEnum;
import com.ltw.device.utils.T212Parser;
import com.ltw.device.vo.ExceptionData;
import com.ltw.device.vo.RealData;
import com.ltw.device.vo.WareInfo;
import com.ltw.device.vo.YCDateVo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class YCHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Value("${ycdevice.checkCrc}")
    private boolean checkcrc;
    public static BidiMap bidiMap = new DualHashBidiMap();
    public static Map<String, Map<String, Object>> devMap = new HashMap();
    public static Map<String, ChannelHandlerContext> ctxMap = new HashMap();
    @Resource
    private StringRedisTemplate stringRedisTemplate;

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
        Class var3 = YCHandler.class;
        Class var4 = YCHandler.class;
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
        stringRedisTemplate.opsForValue().set("123", "123",10,TimeUnit.SECONDS);
        byte[] data = new byte[m.readableBytes()];
        m.readBytes(data);
        String msg = new String(data, CharsetUtil.UTF_8);
        SocketAddress address = ctx.channel().remoteAddress();
        System.out.println(address.toString().substring(1) + "---" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println(msg);
        List<Object> list = parse(msg);
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        for(int i=0; i<list.size(); i++){
            Object o = list.get(i);
//            boolean contains = ObjectUtil.contains(o, RealData.class);
            boolean contains = o instanceof RealData;
            if(contains){
                RealData realData = (RealData)o;
                Map<String, Map<String, Map<String, String>>> dataMap = realData.getData();
                if(!MapUtil.isEmpty(dataMap)){
                    Set<Map.Entry<String, Map<String, Map<String, String>>>> entries = dataMap.entrySet();
                    Iterator<Map.Entry<String, Map<String, Map<String, String>>>> iterator = entries.iterator();
                    Map.Entry<String, Map<String, Map<String, String>>> next = iterator.next();
                    Map<String, Map<String, String>> value = next.getValue();
                    Set<Map.Entry<String, Map<String, String>>> entries1 = value.entrySet();
                    Iterator<Map.Entry<String, Map<String, String>>> iterator1 = entries1.iterator();
                    YCDateVo ycDateVo = new YCDateVo();
                    ycDateVo.setDeviceNum(realData.getMn());
                    ycDateVo.setDateSendTime(DateUtil.toLocalDateTime(realData.getQn()));
                    for(;iterator1.hasNext();){
                        Map.Entry<String, Map<String, String>> next1 = iterator1.next();
                        String key = next1.getKey();
                        Map<String, String> value1 = next1.getValue();
                        String rtd = value1.get("Rtd");
                        if(StrUtil.isNotBlank(rtd)){
                            if(YCEnum.contains(key)){
                                YCEnum enumByCode = YCEnum.getEnumByCode(key);
                                switch (enumByCode){
                                    case temperature:
                                        ycDateVo.setTemperature(rtd);
                                        break;
                                    case humidity:
                                        ycDateVo.setHumidity(rtd);
                                        break;
                                    case pm:
                                        ycDateVo.setPm(rtd);
                                        break;
                                    case pm10:
                                        ycDateVo.setPm10(rtd);
                                        break;
                                    case tsp:
                                        ycDateVo.setTsp(rtd);
                                        break;
                                    case noise:
                                        ycDateVo.setNoise(rtd);
                                        break;
                                    case airspeed:
                                        ycDateVo.setAirspeed(rtd);
                                        break;
                                    case airpressure:
                                        ycDateVo.setAirpressure(rtd);
                                        break;
                                    case airdirection:
                                        ycDateVo.setAirdirection(rtd);
                                        break;
                                    default:
                                }
                            }
                        };
                    }
                    //将扬尘数据存入redis
//                    redisTemplate.opsForValue().set(CommonConfig.REDIS_YC_DEVICE+ycDateVo.getDeviceNum(), JSON.toJSONString(ycDateVo), 10, TimeUnit.SECONDS);

                };
            }
        }

    }

    private List<Object> parse(String msg) {
        List<Object> list = new ArrayList<>();
        RealData realData = new RealData();


        String dataTime = "";
        try {
            StringReader reader = new StringReader(msg);
            T212Parser t212Parser = new T212Parser(reader);
            char[] header = t212Parser.readHeader();
            char[] dataLen = t212Parser.readDataLen();
            char[] data = t212Parser.readData(Integer.parseInt(new String(dataLen)));
            char[] readCrc = t212Parser.readCrc();
            //根据数据获取crc
            int crc = T212Parser.crc16Checkout(data, data.length);
            //校验
            if (Integer.parseInt(new BigInteger(new String(readCrc), 16).toString()) == crc) {
                //System.out.println(msg);
                String str = new String(data);
                //将212协议  分段
                String[] strings = str.substring(0, str.length() - 2).split(";");
                Map<String, Map<String, Map<String, String>>> map2 = new HashMap<>();
                Map<String, Map<String, String>> map1 = new HashMap<>();
                Map<String, Map<String, String>> wareMap1 = new HashMap<>();
                Map<String, Map<String, String>> exceptionMap1 = new HashMap<>();
                boolean flag = false;

                WareInfo wareInfo = new WareInfo();
                ExceptionData exceptionData = new ExceptionData();

                for (int i = 0; i < strings.length; i++) {
                    // System.out.println(strings[i]);
                    //获取QN对应值
                    if (strings[i].contains("QN")) {
//                        Date date = DateUtils.stringToDate(strings[i].split("=")[1], "yyyyMMddHHmmssSSS");
                        Date date = DateUtil.parse(strings[i].split("=")[1], "yyyyMMddHHmmssSSS").toJdkDate();

                        realData.setQn(date);
                        wareInfo.setQn(date);
                        exceptionData.setQn(date);
                        //System.out.println("QN:" + strings[i].split("=")[1]);
                    }
                    //获取ST对应值
                    if (strings[i].contains("ST")) {
                        //System.out.println("ST:" + strings[i].split("=")[1]);
                    }
                    //获取CN对应值
                    if (strings[i].contains("CN")) {
                        //System.out.println("CN:" + strings[i].split("=")[1]);
                        wareInfo.setDataType(strings[i].split("=")[1]);
                        exceptionData.setDataType(strings[i].split("=")[1]);
                    }
                    //获取PW对应值
                    if (strings[i].contains("PW")) {
                        //System.out.println("PW:" + strings[i].split("=")[1]);
                    }
                    //获取MN对应值
                    if (strings[i].contains("MN")) {
                        realData.setMn(strings[i].split("=")[1]);
                        wareInfo.setMn(realData.getMn());
                        exceptionData.setMn(realData.getMn());

                        //System.out.println("MN:" + strings[i].split("=")[1]);
//                        Bson bson = Filters.eq("mn", realData.getMn());
//                        FindIterable<Document> documents = COLLECTION.find(bson);
//                        Document doc = documents.first();
//                        if (doc == null) {
//                            return null;
//                        }
//                        for (Document document : documents) {
//                            Integer pointId = (Integer) document.get("pointId");
//                            String pointName = (String) document.get("pointName");
//                            Integer enterpriseId = (Integer) document.get("enterpriseId");
//                            String enterpriseName = (String) document.get("enterpriseName");
//                            wareInfo.setEnterpriseId(enterpriseId);
//                            wareInfo.setEnterpriseName(enterpriseName);
//                            wareInfo.setPointId(pointId);
//                            wareInfo.setPointName(pointName);
//                            exceptionData.setEnterpriseId(enterpriseId);
//                            exceptionData.setEnterpriseName(enterpriseName);
//                            exceptionData.setPointId(pointId);
//                            exceptionData.setPointName(pointName);
//                        }
                    }
                    //获取DataTime对应值
                    if (strings[i].contains("DataTime")) {
                        dataTime = strings[i].split("=")[2];
                        //System.out.println("DataTime:" + strings[i].split("=")[2]);
                    }
                    //对数据进行分段  CP的后一段是数据段
                    //CH4-Min=1.0700,CH4-Avg=1.1000,CH4-Max=1.1200,CH4-Cou=0.0014,CH4-Flag=N
                    //25-Min=12.6700,25-Avg=13.0820,25-Max=13.7000,25-Cou=0.0172,25-Flag=N
                    if ((i > 0 && strings[i - 1].contains("CP")) || flag) {
                        //修改标志位
                        if (strings[i - 1].contains("CP")) {
                            flag = true;
                        }
                        Map<String, String> wareMap = new HashMap<>();
                        Map<String, String> exceptionMap = new HashMap<>();
                        //查询
                        String code = strings[i].split("-", 2)[0];
//                        Divisor divisor = divisorService.findByCode(code);
//                        if (divisor != null) {
//                            wareMap.put("dateTime", DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                            wareMap.put("divisorName", divisor.getName());
//                            exceptionMap.put("dateTime", DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                            exceptionMap.put("divisorName", divisor.getName());
//                        }
                        //根据站点和因子id查询站点因子信息
//                        PointAndDivisor pointAndDivisor = pointAndDivisorService.findByPointIdAndDivisorId(wareInfo.getPointId(), divisor.getId());
                        //对数据段进行分区 string2[i] CH4-Min=1.0700
                        String[] strings2 = strings[i].split(",");

                        Map<String, String> map = new HashMap<>();


                        boolean wareFlag = false;
                        boolean exceptionFlag = false;
                        for (int j = 0; j < strings2.length; j++) {
                            String sufVal = strings2[j].split("-", 2)[1]; //CH4-Min=1.0700
                            String sufPreVal = sufVal.split("=")[0]; //Min
                            String suf2Val = sufVal.split("=")[1];  //1.0700


//                            if (!msg.contains("CN=2011")) {
//                                //零值 和 恒值 异常
//                                if (msg.contains("CN=2051") && sufVal.contains("Avg")) {
//                                    String k = "pointId:" + wareInfo.getPointId() + "-" + "divisorId:" + divisor.getId();
//                                    String value = JEDIS.hget(k, "val");
//                                    //第一次 为空
//                                    if (StringUtil.isNullOrEmpty(value)) {
//                                        JEDIS.hset(k, "val", suf2Val);
//                                        JEDIS.hset(k, "beginTime", DateUtils.dateToString(new Date(), "yyyy-HH-dd HH:mm:ss"));
//                                        JEDIS.hset(k, "updateTime", DateUtils.dateToString(new Date(), "yyyy-HH-dd HH:mm:ss"));
//                                    } else if (value.equals(suf2Val)) {
//                                        JEDIS.hset(k, "updateTime", DateUtils.dateToString(new Date(), "yyyy-HH-dd HH:mm:ss"));
//                                    } else {
//                                        // 值发生改变
//                                        String endTime = JEDIS.hget(k, "updateTime");
//                                        String beginTime = JEDIS.hget(k, "beginTime");
//                                        Map<String, Map<String, String>> m1 = new HashMap<>();
//                                        Map<String, String> m = new HashMap<>();
//                                        m.put("divisorName", divisor.getName());
//                                        m.put("dateTime", beginTime + "~" + endTime);
//                                        m.put("rtd", value);
//                                        m1.put(sufPreVal, m);
//                                        if (dateDiff(DateUtils.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss"), DateUtils.stringToDate(beginTime, "yyyy-MM-dd HH:mm:ss"))) {
//                                            Document document = new Document("pointId", wareInfo.getPointId())
//                                                    .append("pointName", wareInfo.getPointName())
//                                                    .append("mn", wareInfo.getMn())
//                                                    .append("enterpriseId", wareInfo.getEnterpriseId())
//                                                    .append("enterpriseName", wareInfo.getEnterpriseName())
//                                                    .append("code", sufPreVal)
//                                                    .append("beginTime", DateUtils.stringToDate(beginTime, "yyyy-MM-dd HH:mm:ss"))
//                                                    .append("endTime", DateUtils.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss"))
//                                                    .append("metrics", m1);
//
//                                            if (Integer.parseInt(value) == 0) {
//                                                MongoDBConnection.getConnection(CollectionMapping.ZERO.getcollectName()).insertOne(document);
//                                            } else {
//                                                MongoDBConnection.getConnection(CollectionMapping.CONST.getcollectName()).insertOne(document);
//                                            }
//                                        }
//                                        //更新redis
//                                        JEDIS.hset(k, "val", sufVal);
//                                        JEDIS.hset(k, "beginTime", DateUtils.dateToString(new Date(), "yyyy-HH-dd HH:mm:ss"));
//                                        JEDIS.hset(k, "updateTime", DateUtils.dateToString(new Date(), "yyyy-HH-dd HH:mm:ss"));
//                                    }
//                                }
//
//
//                                if (sufVal.contains("Max")) {
//                                    exceptionMap.put("Rtd", suf2Val);
//                                }
//
//                                if (sufVal.contains("Flag")) {
//                                    wareMap.put("flag", suf2Val);
//                                }
//                                if (sufVal.contains("Flag") && !"N".equals(suf2Val)) {
//                                    exceptionFlag = true;
//                                }
//
//                                //超标异常
//                                if (sufVal.contains("Max") && pointAndDivisor != null && Integer.parseInt(pointAndDivisor.getCeilval()) != 0 && Double.parseDouble(suf2Val) > Double.parseDouble(pointAndDivisor.getCeilval())) {
//                                    wareMap.put("ceilval", pointAndDivisor.getCeilval());
//                                    wareMap.put("floorval", pointAndDivisor.getFloorval());
//                                    wareMap.put("Rtd", suf2Val);
//                                    wareFlag = true;
//                                }
//                            }


                            map.put(sufPreVal, suf2Val);

                            if (j == strings2.length - 1) {
                                String preVal = strings2[j].split("-", 2)[0];
                                if (exceptionFlag) {
                                    exceptionMap1.put(preVal, exceptionMap);
                                } else if (wareFlag) {
                                    wareMap1.put(preVal, wareMap);
                                } else {
                                    map1.put(preVal, map);
                                }

                            }
                        }
                        if (wareFlag && wareMap1.size() > 0) {
                            wareInfo.setData(wareMap1);
                            if (wareInfo.getQn() == null) {
                                try {
//                                    Date date = DateUtils.stringToDate(dataTime, "yyyyMMddHHmmss");
                                    Date date = DateUtil.parse(dataTime, "yyyyMMddHHmmss").toJdkDate();
                                    wareInfo.setQn(date);
                                } catch (Exception e) {
                                    System.out.println();
                                }
                            }
                            list.add(wareInfo);
                        }
                        if (i == strings.length - 1 && exceptionMap1.size() > 0) {
                            exceptionData.setData(exceptionMap1);
                            if (exceptionData.getQn() == null) {
                                try {
//                                    Date date = DateUtils.stringToDate(dataTime, "yyyyMMddHHmmss");
                                    Date date = DateUtil.parse(dataTime, "yyyyMMddHHmmss").toJdkDate();
                                    exceptionData.setQn(date);
                                } catch (Exception e) {
                                    System.out.println();
                                }
                            }
                            list.add(exceptionData);
                        }

                        if (i == strings.length - 1 && map1.size() > 0) {
                            map2.put(dataTime, map1);
                            realData.setData(map2);
                            if (realData.getQn() == null) {
                                try {
//                                    Date date = DateUtils.stringToDate(dataTime, "yyyyMMddHHmmss");
                                    Date date = DateUtil.parse(dataTime, "yyyyMMddHHmmss").toJdkDate();
                                    realData.setQn(date);
                                } catch (Exception e) {
                                    System.out.println();
                                }
                            }
                            list.add(realData);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println();
        }
        return list;
    }
}
