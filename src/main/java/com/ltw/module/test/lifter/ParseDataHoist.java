package com.ltw.module.test.lifter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class ParseDataHoist {

    public static byte[] processingData(byte[] data, String address){
        //获得吊笼编号
        byte cage_id = data[3];

        //获得黑匣子编号
        Integer int_black_box_id = ByteArrayUtil.getInt((byte) 0, data[4], data[5], data[6]);
        String hoist_box_id = String.valueOf(int_black_box_id);
        byte[] byte_hoist_box_id = new byte[]{data[4], data[5], data[6]};
        
        //获得帧类型，根据该值做出相应操作
        int frame_type = data[2];

        String msgs = ByteArrayToStringUtil.getHex(data);
        Byte type = null;
        byte[] message_segment = null;
        if (frame_type == 0x01) {//注册信息
            message_segment = new byte[8];
            //从数据库查询该设备，如果查询不到则添加该设备。
            HoistInfoRegisterPO hoistInfoRegisterPO = new HoistInfoRegisterPO();
            hoistInfoRegisterPO.setCage_id((short) (cage_id & 0xFF));//吊笼编号
            hoistInfoRegisterPO.setHoist_box_id(hoist_box_id);//黑匣子编号
            try {//本try catch循环无用，update已经捕获异常，无法知道是否修改成功。代码待更改
                //deviceInfoService.updateHoistInformationByHoistBoxId(hoistInfoRegisterPO);
                message_segment[0] = 1;//注册结果
            } catch (Exception e) {
                message_segment[0] = 0;
            }
            //下发应答帧
            type = (byte) 02;
            //格式为yyyyMMddHHmmss
            String time = getStringFigureByDate(new Date());
            message_segment[1] = 30;
            //信息体 时间 所需格式为 yyMMddHHmmss
            message_segment[2] = (byte) Integer.parseInt(time.substring(2, 4));
            message_segment[3] = (byte) Integer.parseInt(time.substring(4, 6));
            message_segment[4] = (byte) Integer.parseInt(time.substring(6, 8));
            message_segment[5] = (byte) Integer.parseInt(time.substring(8, 10));
            message_segment[6] = (byte) Integer.parseInt(time.substring(10, 12));
            message_segment[7] = (byte) Integer.parseInt(time.substring(12, 14));
            //信息体 上传间隔 默认设为15
            return CommonUtil.getDataReleased(type, cage_id, byte_hoist_box_id, message_segment);
        } else if (frame_type == 0x03) {//设备上报升降机基本信息 命令字 03	预留
        	return null;
        } else if (frame_type == 0x04) {//属性信息 标定信息帧 命令字04
            HoistInfo hoistInfoCalibrationPO = new HoistInfo();
            hoistInfoCalibrationPO.setCage_id((short) (cage_id & 0xFF));//吊笼编号
            hoistInfoCalibrationPO.setHoist_box_id(hoist_box_id);//黑匣子编号
            hoistInfoCalibrationPO.setWeight_noload_AD(new BigDecimal(BytesToShortUtil.getShortHL(data[7], data[8]) & 0xFFFF));//重量空载AD值2byte
            hoistInfoCalibrationPO.setWeight_noload_actual(new BigDecimal(BytesToShortUtil.getShortHL(data[9], data[10]) & 0xFFFF));//重量空载实际值2byte
            hoistInfoCalibrationPO.setWeight_load_AD(new BigDecimal(BytesToShortUtil.getShortHL(data[11], data[12]) & 0xFFFF));//重量负载AD值2byte
            hoistInfoCalibrationPO.setWeight_load_actual(new BigDecimal(BytesToShortUtil.getShortHL(data[13], data[14]) & 0xFFFF));//重量负载实际值2byte
            hoistInfoCalibrationPO.setHeight_bottom_AD(new BigDecimal(BytesToShortUtil.getShortHL(data[15], data[16]) & 0xFFFF));//高度底端AD值2byte
            hoistInfoCalibrationPO.setHeight_bottom_actual(new BigDecimal(BytesToShortUtil.getShortHL(data[17], data[18]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//高度底端实际值2byte
            hoistInfoCalibrationPO.setHeight_top_AD(new BigDecimal(BytesToShortUtil.getShortHL(data[19], data[20]) & 0xFFFF));//高度顶端AD值2byte
            hoistInfoCalibrationPO.setHeight_top_actual(new BigDecimal(BytesToShortUtil.getShortHL(data[21], data[22]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//高度顶端实际值2byte
            //添加到数据库
        } else if (frame_type == 0x05) {//限位信息帧上报（限位，报警，预警等的设置） 命令字05
            HoistInfo hoistInfoLimitSetPO = new HoistInfo();
            hoistInfoLimitSetPO.setHoist_box_id(hoist_box_id);
            hoistInfoLimitSetPO.setCage_id((short) (cage_id & 0xFF));
            hoistInfoLimitSetPO.setMaximum_elevating_capacity_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[7], data[8]) & 0xFFFF));//最大起重量预警2byte
            hoistInfoLimitSetPO.setMaximum_elevating_capacity_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[9], data[10]) & 0xFFFF));//最大起重量报警2byte
            hoistInfoLimitSetPO.setMaximum_rise_height(new BigDecimal(BytesToShortUtil.getShortHL(data[11], data[12]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大起升高度2byte
            hoistInfoLimitSetPO.setMaximum_speed_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[13], data[14]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大速度预警2byte
            hoistInfoLimitSetPO.setMaximum_speed_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[15], data[16]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大速度报警2byte
            hoistInfoLimitSetPO.setMaximum_carrying_number(BytesToShortUtil.getShortHL(data[17], data[18]));//最大承载人数2byte
            hoistInfoLimitSetPO.setTilt_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[19], data[20]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//倾斜预警值2byte
            hoistInfoLimitSetPO.setTilt_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[21], data[22]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//倾斜报警值2byte
            //新增 2020-09-14 风速预警值2byte+风速报警值2byte
            if (data.length > 23) {
                hoistInfoLimitSetPO.setWindspeed_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[23], data[24]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));
                hoistInfoLimitSetPO.setWindspeed_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[25], data[26]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));
            }
            //添加到数据库
        } else if (frame_type == (byte) 0XB0) {//限位信息帧下行设置后，设备返回的命令
            HoistInfo hoistInfoLimitSetPO = new HoistInfo();
            hoistInfoLimitSetPO.setHoist_box_id(hoist_box_id);
            hoistInfoLimitSetPO.setCage_id((short) (cage_id & 0xFF));
            hoistInfoLimitSetPO.setMaximum_elevating_capacity_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[7], data[8]) & 0xFFFF));//最大起重量预警2byte
            hoistInfoLimitSetPO.setMaximum_elevating_capacity_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[9], data[10]) & 0xFFFF));//最大起重量报警2byte
            hoistInfoLimitSetPO.setMaximum_rise_height(new BigDecimal(BytesToShortUtil.getShortHL(data[11], data[12]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大起升高度2byte
            hoistInfoLimitSetPO.setMaximum_speed_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[13], data[14]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大速度预警2byte
            hoistInfoLimitSetPO.setMaximum_speed_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[15], data[16]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//最大速度报警2byte
            hoistInfoLimitSetPO.setMaximum_carrying_number(BytesToShortUtil.getShortHL(data[17], data[18]));//最大承载人数2byte
            hoistInfoLimitSetPO.setTilt_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[19], data[20]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//倾斜预警值2byte
            hoistInfoLimitSetPO.setTilt_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[21], data[22]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));//倾斜报警值2byte
            if (data.length > 23) {
                //新增 2020-09-14 风速预警值2byte+风速报警值2byte
                hoistInfoLimitSetPO.setWindspeed_warning(new BigDecimal(BytesToShortUtil.getShortHL(data[23], data[24]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));
                hoistInfoLimitSetPO.setWindspeed_alarm(new BigDecimal(BytesToShortUtil.getShortHL(data[25], data[26]) & 0xFFFF).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(BigDecimalSet.b10));
            }
            // 更改0下发状态；返回1未下发
            hoistInfoLimitSetPO.setInfo_status("1");
            //添加到数据库
        } else if (frame_type == 0X08) {//设备上行锁机/解锁成功命令
            HoistInfo hoistInfoLimitSetPO = new HoistInfo();
            hoistInfoLimitSetPO.setHoist_box_id(hoist_box_id);
            hoistInfoLimitSetPO.setCage_id((short) (cage_id & 0xFF));
            hoistInfoLimitSetPO.setLock(String.valueOf(data[7]));
           //添加到数据库
        } else if (frame_type == 0x10) {//实时工况数据（实时数据）  命令字0x10
            HoistData hoistData = new HoistData();
            hoistData.setCage_id(cage_id);
            hoistData.setHoist_box_id(hoist_box_id);
            //yyMMddHHmmss格式时间转换成yyyy-MM-dd HH:mm:ss形式
            hoistData.setHoist_time(DateFormatUtil.getTimeStampBy6ByteArrayTime(new byte[]{data[7], data[8], data[9], data[10], data[11], data[12]}));
            hoistData.setReal_time_lifting_weight(getShort(data[13], data[14]));//实时起重量2byte
            hoistData.setWeight_percentage(data[15]);//重量百分比1byte
            hoistData.setReal_time_number_of_people(data[16]);//实时人数1byte
            hoistData.setReal_time_height(getShort(data[17], data[18]));//实时高度2byte
            hoistData.setHeight_percentage(data[19]);//高度百分比（1byte）
            hoistData.setReal_time_speed(data[20]);//实时速度1byte 原高6bit
            hoistData.setReal_time_gradient1(getShort(data[21], data[22]));//实时倾斜度1(2byte)
            hoistData.setTilt_percentage1(data[23]);// 倾斜百分比1(1byte)
            hoistData.setReal_time_gradient2(getShort(data[24], data[25]));//实时倾斜度2(2byte)
            hoistData.setTilt_percentage2(data[26]);// 倾斜百分比2(1byte)
            hoistData.setDriver_identification_state(data[27]);//驾驶员身份认证结果1byte
            hoistData.setDoor_lock_state(data[28]);
            hoistData.setHoist_system_state(getShort(data[29], data[30]));// 本字段只存在于实时值 系统状态2byte（0-1bit重量，2-3bit高度限位，4-5bit超速，6-7bit人数，8-9bit倾斜，数值0代表正常，数值1代表预警，数值2代表报警。10bit前门锁状态11bit后门锁状态：数字0正常,数值1异常），
            try {
                String status = BitToShortUtil.getBitString(hoistData.getHoist_system_state());
                if (data.length > 31) {
                    hoistData.setReally_wind_speed(BytesToShortUtil.getShortHL(data[31], data[32]));
                    hoistData.setSystem_wind(status.substring(2, 4));
                }
                status = status.substring(6, 16);
                String system_weight = status.substring(8, 10);
                hoistData.setSystem_weight(system_weight);
                String system_height = status.substring(6, 8);
                hoistData.setSystem_height(system_height);
                String system_speeding = status.substring(4, 6);
                hoistData.setSystem_speeding(system_speeding);
                String system_people = status.substring(2, 4);
                hoistData.setSystem_people(system_people);
                String system_tilt = status.substring(0, 2);
                hoistData.setSystem_tilt(system_tilt);
            } catch (Exception e) {
            }
            hoistData.setReal_time_or_alarm((byte) 1);//是报警值还是实时值	1代表实时值，2代表报警值
            //添加到数据库
        } else if (frame_type == 0x11) {//报警信息	命令字0x11
            HoistData hoistData = new HoistData();
            hoistData.setCage_id(cage_id);
            hoistData.setHoist_box_id(hoist_box_id);
            //yyMMddHHmmss格式时间转换成yyyy-MM-dd HH:mm:ss形式
            hoistData.setHoist_time(getTimeStampBy6ByteArrayTime(new byte[]{data[7], data[8], data[9], data[10], data[11], data[12]}));
            hoistData.setReal_time_lifting_weight(getShort(data[13], data[14]));//实时起重量2byte
            hoistData.setWeight_percentage(data[15]);//重量百分比1byte
            hoistData.setReal_time_number_of_people(data[16]);//实时人数1byte
            hoistData.setReal_time_height(getShort(data[17], data[18]));//实时高度2byte
            hoistData.setHeight_percentage(data[19]);//高度百分比（1byte）
            hoistData.setReal_time_speed(data[20]);//实时速度1byte（高6bit速度，低2bit方向: 0停止，1上下2下）
            //倾斜数值减去1500取绝对值再除以100，倾斜度单位为0.01度，所以要减1500，取绝对值，除以100，因为此时的倾斜度单位为0.01度，所以还要除以100，将单位转为1度
            hoistData.setReal_time_gradient1(getShort(data[21], data[22]));//实时倾斜度1(2byte)
            hoistData.setTilt_percentage1(data[23]);// 倾斜百分比1(1byte)
            hoistData.setReal_time_gradient2(getShort(data[24], data[25]));//实时倾斜度2(2byte)
            hoistData.setTilt_percentage2(data[26]);// 倾斜百分比2(1byte)
            hoistData.setDriver_identification_state(data[27]);//驾驶员身份认证结果1byte
            // 门锁状态1byte（0bit前门1bit后门，数值1代表开启，0带便关闭。2bit门锁异常指示，0无异常1有异常）
            hoistData.setDoor_lock_state(data[28]);
            hoistData.setHoist_alarm_reason(data[29]);// 本字段只存在于报警值 报警原因1byte
            hoistData.setHoist_level(data[30]);// 本字段只存在于报警值 级别1byte
            if (data.length > 31) {
                hoistData.setReal_time_gradient1(BytesToShortUtil.getShortHL(data[31], data[32]));
            }
            hoistData.setReal_time_or_alarm((byte) 2);//是报警值还是实时值	1代表实时值，2代表报警值
            //添加到数据库
        } else if (frame_type == 0x12) {//人员身份认证信息  命令字0x12
            HoistDriverIdRecg hoistDriverIdRecg = new HoistDriverIdRecg();
            hoistDriverIdRecg.setHoist_box_id(hoist_box_id);//黑匣子(主机)编号 3byte(或4byte)
            hoistDriverIdRecg.setRecognition_results(data[7]);
            hoistDriverIdRecg.setDriver_name(getStringTrim(data, 8, 40, "GB2312"));
            hoistDriverIdRecg.setDriver_id(getStringTrim(data, 40, 48, "GB2312"));
            hoistDriverIdRecg.setRecg_rercentage(data[48]);
            hoistDriverIdRecg.setId_card_no(getStringTrim(data, 49, 67, "GB2312"));
            hoistDriverIdRecg.setSystem_time(getStringTimeByDate(new Date()));
            //添加到数据库
        } else if (frame_type == 0x13) {//工作循环统计 命令字0x13 工作循环是指记录一次完整的吊装过程
            HoistWorkCycle workCycleStatistics = new HoistWorkCycle();
            workCycleStatistics.setCage_id(cage_id);
            workCycleStatistics.setHoist_box_id(hoist_box_id);

            workCycleStatistics.setWork_cycle_current_time(getTimeStampBy6ByteArrayTime(new byte[]{data[7], data[8], data[9], data[10], data[11], data[12]}));//当前时间6byte
            workCycleStatistics.setWork_cycle_start_time(getTimeStampBy6ByteArrayTime(new byte[]{data[13], data[14], data[15], data[16], data[17], data[18]}));//工作循环开始时间6byte
            workCycleStatistics.setWork_cycle_end_time(getTimeStampBy6ByteArrayTime(new byte[]{data[19], data[20], data[21], data[22], data[23], data[24]}));//工作循环终止时间6byte

            String pilot_name = null;
            byte[] byte_hoist_pilot_name = new byte[32];
            for (int i = 0; i < byte_hoist_pilot_name.length; i++) {
                byte_hoist_pilot_name[i] = data[25 + i];
            }
            workCycleStatistics.setPilot_name(pilot_name);//驾驶员姓名32byte

            String pilot_identity_card_number = null;
            byte[] byte_pilot_identity_card_number = new byte[32];
            for (int i = 0; i < byte_pilot_identity_card_number.length; i++) {
                byte_pilot_identity_card_number[i] = data[57 + i];
            }
            workCycleStatistics.setPilot_identity_card_number(pilot_identity_card_number);//驾驶员身份证号18byte
            workCycleStatistics.setThe_load(getShort(data[75], data[76]));//本次载重2byte
            workCycleStatistics.setThe_load_percentage(data[77]);//本次载重百分比1byte
            workCycleStatistics.setThe_rise_start_height(getShort(data[78], data[79]));//本次起升起点高度2byte
            workCycleStatistics.setThe_rise_end_height(getShort(data[80], data[81]));//本次起升终点高度2byte
            workCycleStatistics.setThe_rise_stroke_height(getShort(data[82], data[83]));//本次起升的行程高度2byte
            workCycleStatistics.setThe_rise_direction(data[84]);//本次起升方向1byte
            workCycleStatistics.setThe_rise_average_speed(data[85]);//本次起升平均速度1byte
            workCycleStatistics.setThe_rise_maximum_x_direction_gradient(getShort(data[86], data[87]));//本次起升最大X向倾斜度2byte
            workCycleStatistics.setThe_rise_maximum_y_direction_gradient(getShort(data[88], data[89]));//本次起升最大Y向倾斜度2byte
            workCycleStatistics.setThe_load_number(data[90]);//本次装载人数1byte
            workCycleStatistics.setThe_rise_alert_state(getShort(data[91], data[92]));//本次起升警报状态2byte
            if (data.length > 93) {
                workCycleStatistics.setMaximum_wind_speed(BytesToShortUtil.getShortHL(data[93], data[94]));
            }
            //添加到数据库
        } else if (frame_type == (byte) 0xa0) {//设备掉电
            return powerDown(data, hoist_box_id, byte_hoist_box_id, cage_id);
        } else if (frame_type == (byte) 0xa2) {//GPS经纬度
            return getGPS(data, hoist_box_id, byte_hoist_box_id, cage_id);
        } else if (frame_type == (byte) 0xa4) {//人员身份认证信息 命令字0xA4（新设备使用）
            driverIdRecg0xa4(data, hoist_box_id, byte_hoist_box_id, cage_id);
        } else if (frame_type == (byte) 0xa5) {//人员脸部图像上传  命令字0xA5
            CombinationImageKaoQin(data, hoist_box_id);
        } else if (frame_type == (byte) 0xa6) {//吊笼抓拍图像上传 命令字0xA6
            return CombinationImageZhuaPai(data, hoist_box_id, byte_hoist_box_id, cage_id);
        }else if (frame_type == (byte) 0xa9) {//吊笼增减标准节高度设备返回命令 命令字0xA9
            increaseHeight(data, hoist_box_id);
        } else if (frame_type == 0x20) {
            CombinationImage(data, hoist_box_id);
        }
        return null;
    }

    /**
     * 吊笼增减标准节高度设备返回命令 命令字0xA9
     */
    private static void increaseHeight(byte[] data, String deviceId) {
        int i = 7;
        short increase_height = ByteArrayToShortUtil.getShortHL(data, i);
        i++;
        i++;
        short height_section = ByteArrayToShortUtil.getShortHL(data, i);
        i++;
        i++;
        DeviceHeight deviceHeight = Mem.deviceHeightMap.get(deviceId);
        if (deviceHeight != null) {
            Mem.deviceHeightMap.remove(deviceId);
        }
        deviceHeight = new DeviceHeight();
        deviceHeight.setHeight_section(height_section);
        deviceHeight.setIncrease_height(increase_height);
        deviceHeight.setFlag(true);
        Mem.deviceHeightMap.put(deviceId, deviceHeight);
        HoistInfo deviceInfo = new HoistInfo();
        deviceInfo.setHoist_box_id(deviceId);
        deviceInfo.setMaximum_rise_height(new BigDecimal(height_section).setScale(1, BigDecimal.ROUND_UNNECESSARY).divide(CommonUtil.b10));
        //数据库
    }

    /**
     * 掉线
     *
     * @param data
     * @param deviceId
     * @param byte_device_id
     * @param cage_id
     * @return
     */
    private static byte[] powerDown(byte[] data, String deviceId, byte[] byte_device_id, byte cage_id) {
        HoistInfo deviceInfo = new HoistInfo();
        //是否要增加一个掉电字段?
        deviceInfo.setHoist_box_id(deviceId);
        int i = 7;
        Date date = DateFormatUtil.getTimeStampBy6ByteArrayTime(new byte[]{data[i++], data[i++], data[i++], data[i++], data[i++], data[i++]});
        deviceInfo.setOffline_end_time(DateFormatUtil.getStringTimeByDate(date));
        //添加到数据库
        //下发应答帧
        byte type = (byte) 0xa1;
        return CommonUtil.getDataReleased(type, cage_id, byte_device_id, null);
    }

    private static byte[] getGPS(byte[] data, String deviceId, byte[] byte_device_id, byte cage_id) {
        HoistInfo deviceInfo = new HoistInfo();
        deviceInfo.setHoist_box_id(deviceId);
        int longitudeInt = ByteArrayToIntUtil.getIntLH(data, 13);
        int latitudeInt = ByteArrayToIntUtil.getIntLH(data, 17);
        //log.info("大地坐标系---longitudeInt---{}, latitudeInt---{}", longitudeInt, latitudeInt);
        float longitude = Float.intBitsToFloat(longitudeInt);
        float latitude = Float.intBitsToFloat(latitudeInt);
        //log.info("大地坐标系---longitude---{}, latitude---{}", longitude, latitude);
        double[] gpsDouble = GPS2Util.WGS84ToGCJ02(longitude, latitude);
        float[] gpsFloat = new float[2];
        for (int j = 0; j < gpsDouble.length; j++) {
            gpsFloat[j] = (float) gpsDouble[j];
        }
        //log.info("火星坐标系---longitude---{}, latitude---{}", gpsFloat[0], gpsFloat[1]);
        deviceInfo.setLongitude(String.valueOf(gpsFloat[0]));
        deviceInfo.setLatitude(String.valueOf(gpsFloat[1]));
        //下发应答帧
       //添加到数据库
        byte type = (byte) 0xa3;
        return CommonUtil.getDataReleased(type, cage_id, byte_device_id, null);
    }

    private static void driverIdRecg0xa4(byte[] data, String deviceId, byte[] byte_device_id, byte cage_id){
        int i = 7;
        HoistDriverIdRecg deviceDriverIdRecg = new HoistDriverIdRecg();
        deviceDriverIdRecg.setHoist_box_id(deviceId);//黑匣子(主机)编号 3byte(或4byte)
        Date date = DateFormatUtil.getTimeStampBy6ByteArrayTime(new byte[]{data[i++], data[i++], data[i++], data[i++], data[i++], data[i++]});
        deviceDriverIdRecg.setSystem_time(DateFormatUtil.getStringTimeByDate(date));
        deviceDriverIdRecg.setRecognition_results(data[i++]);
        deviceDriverIdRecg.setDriver_name(getStringTrim(data, i, i += 32, "GB2312"));
        deviceDriverIdRecg.setDriver_id(getStringTrim(data, i, i += 8, "GB2312"));
        deviceDriverIdRecg.setRecg_rercentage(data[i++]);
        deviceDriverIdRecg.setId_card_no(getStringTrim(data, i, i += 18, "GB2312"));
        //log.warn("driverIdRecg---{}", deviceDriverIdRecg);
        //添加到数据库
        //添加设备司机关联
    }

    private static void CombinationImageKaoQin(byte[] data, String deviceId) {
        CompletableFuture.supplyAsync(() ->{
        	int i = 7;
        	//Date date = DateFormatUtil.getTimeStampBy6ByteArrayTime(new byte[]{data[i++], data[i++], data[i++], data[i++], data[i++], data[i++]});
        	//log.warn("kaoqin---date---{}", date);
        	byte[] byteImage = CombinationImageUtil.combination(data, deviceId + data[2], i);
        	if (byteImage == null) {
        		return "3";
        	}
        	String imageName = CombinationImageUtil.getImageName(deviceId);
        	ByteToImage.ByteToFile(deviceId, byteImage, Mem.imagePathHoist + deviceId + "/kaoqin/", imageName, "jpg", 2);
        	byteImage = null;
        	imageName = null;
        	return "3";
        });
    }

    private static byte[] CombinationImageZhuaPai(byte[] data, String deviceId
            , byte[] byte_device_id, byte cage_id) {
        int i = 7;
        byte[] timeByteArray = new byte[]{data[i++], data[i++], data[i++], data[i++], data[i++], data[i++]};
        Date date = DateFormatUtil.getTimeStampBy6ByteArrayTime(timeByteArray);
    	byte[] byteImage = CombinationImageUtil.combination(data, deviceId + data[2], i);
        if (byteImage == null) {
            return null;
        }
        String imageName = CombinationImageUtil.getImageName(deviceId);
        Byte headCount = ByteToImage.ByteToFiles(deviceId, byteImage, Mem.imagePathHoist + deviceId + "/", imageName, "jpg", 2);
        if (headCount == null) {
            headCount = 0;
        }
        byteImage = null;
        imageName = null;
        //下发应答帧
        byte[] message_segment = new byte[7];
        int j = 0;
        for (; j < timeByteArray.length; j++) {
            message_segment[j] = timeByteArray[j];
        }//吊笼拍照图片时间
        message_segment[j++] = 1;//headCount;//识别人数(1byte) 暂时先设置个值1
        byte type = (byte) 0xa7;
        return CommonUtil.getDataReleased(type, cage_id, byte_device_id, message_segment);
    }


    /**
     * 以下是相关工具类
     */
    public static String getStringFigureByDate(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static Timestamp getTimestamp(Date date) {
        return getTimestampByStringTime(getStringTimeByDate(date));
    }

    public static String getStringTimeByDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Timestamp getTimestampByStringTime(String time) {
        return Timestamp.valueOf(time);
    }

    public static Timestamp getTimeStampBy6ByteArrayTime(byte[] bytesTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("20");
        if (bytesTime[0] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[0]);
        sb.append("-");
        if (bytesTime[1] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[1]);
        sb.append("-");
        if (bytesTime[2] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[2]);
        sb.append(" ");
        if (bytesTime[3] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[3]);
        sb.append(":");
        if (bytesTime[4] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[4]);
        sb.append(":");
        if (bytesTime[5] < 10) {
            sb.append("0");
        }
        sb.append(bytesTime[5]);
        return getTimestampByStringTime(sb.toString());
    }

    /**
     * @param data
     * @return 计算校验和累加和校验【每字节相加（16进制）取后末两位】
     */
    public static byte countChecksum(byte[] data, int start) {
        int result = 0;
        for (; start < data.length; start++) {
            result += data[start];
        }
        return (byte) (result % 256);
    }

    /**
     * @param high
     * @param low
     * @return 第一个参数是高字节，第二个参数是低字节
     */
    public static Short getShort(byte high, byte low) {
        return (short) ((high << 8) | (low & 0xFF));
    }

    /**
     * 根据传入的开始位置和结束位置，截取传入的byte数组
     * byte数组转字符串，同时修剪去除首尾空格
     *
     * @throws Exception
     */
    public static String getStringTrim(byte[] data, int start, int end, String encoder){
        byte[] intercept;
		try {
			intercept = getArrayToIntercept(data, start, end);
			return new String(intercept, encoder).trim();
		} catch (Exception e) {
			return "";
		}
        
    }

    /**
     * 根据传入的开始位置和结束位置，截取传入的byte数组
     */
    public static byte[] getArrayToIntercept(byte[] data, int start, int end) throws Exception {
        if (start > end || start < 0 || end > data.length) {
            throw new Exception();
        }
        byte[] o = new byte[end - start];
        for (int i = start; i < end; i++) {
            o[i - start] = data[i];
        }
        return o;
    }

    private static void CombinationImage(byte[] data, String deviceId) {
        byte[] byteImage = CombinationImageUtil.combination(data, deviceId);
        if (byteImage == null) {
            return;
        }
        String imageName = CombinationImageUtil.getImageName(deviceId);
        ByteToImage.ByteToFile(deviceId, byteImage, Mem.imagePathHoist + deviceId + "/", imageName, "jpg", 2);
        byteImage = null;
        imageName = null;
    }

    private static void updateOperationState(String deviceId, Date offlineTime) {
        //将上线时间放入塔机信息表中
        HoistInfo tci = new HoistInfo();
        tci.setHoist_box_id(deviceId);
        tci.setOffline_start_time(DateFormatUtil.getStringTimeByDate(Mem.getNowDate()));
        //保存数据库
        //将上线离线时间插入表中
        if (Mem.getLatestOnlineTime().containsKey(deviceId)) {
            HoistOperationTime deviceOperationTime = new HoistOperationTime();
            deviceOperationTime.setHoist_box_id(deviceId);
            deviceOperationTime.setStart_time(DateFormatUtil.getStringTimeByDate(Mem.getNowDate()));
            deviceOperationTime.setEnd_time(DateFormatUtil.getStringTimeByDate(offlineTime));
            //保存数据库
        }
    }
    
    public static void main(String[] args) {
		System.out.println(0x11);
	}
    
}