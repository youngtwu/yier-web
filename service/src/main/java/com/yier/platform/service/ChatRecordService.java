package com.yier.platform.service;

import com.google.common.collect.Lists;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.ChatRecordRequest;
import com.yier.platform.common.requestParam.ChatStateRequest;
import com.yier.platform.common.requestParam.TaskScheduleRequest;
import com.yier.platform.dao.ChatRecordMapper;
import com.yier.platform.dao.ChatStateMapper;
import com.yier.platform.dao.TaskScheduleMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 聊天记录 service
 */
@ApiModel(value = "聊天记录 service")
@Service
public class ChatRecordService {
    private static final Logger logger = LoggerFactory.getLogger(ChatRecordService.class);
    @Autowired
    private ChatRecordMapper daoChatRecordMapper;
    @Autowired
    private ChatStateMapper daoChatStateMapper;
    @Autowired
    private DoctorService serviceDoctorService;
    @Autowired
    private PharmacistService servicePharmacistService;
    @Autowired
    private AppPushService serviceAppPushService;
    @Autowired
    private RedisService serviceRedisService;
    @Autowired
    private MessageService serviceMessageService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private TaskScheduleMapper daoTaskScheduleMapper;

    @ApiOperation(value = "根据条件查询原始聊天记录列表")
    public List<ChatRecordPo> listChatRecordPo(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPo(params);
    }

    @ApiOperation(value = "根据条件查询原始聊天记录列表 总数")
    public int listChatRecordPoCount(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPoCount(params);
    }

    @ApiOperation(value = "根据条件查询对应药师聊天列表")
    public List<PharmacistPo> listChatRecordPharmacistPo(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPharmacistPo(params);
    }

    @ApiOperation(value = "根据条件查询对应药师聊天列表 总数")
    public int listChatRecordPharmacistPoCount(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPharmacistPoCount(params);
    }

    @ApiOperation(value = "根据条件查询对应医生聊天列表")
    public List<DoctorPo> listChatRecordDoctorPo(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordDoctorPo(params);
    }

    @ApiOperation(value = "根据条件查询对应医生聊天列表 总数")
    public int listChatRecordDoctorPoCount(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordDoctorPoCount(params);
    }

    @ApiOperation(value = "根据条件查询对应患者聊天列表")
    public List<PatientPo> listChatRecordPatientPo(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPatientPo(params);
    }

    @ApiOperation(value = "根据条件查询对应患者聊天列表 总数")
    public int listChatRecordPatientPoCount(ChatRecordRequest params) {
        return daoChatRecordMapper.listChatRecordPatientPoCount(params);
    }

    @ApiOperation(value = "插入聊天记录")
    @Transactional
    public ChatRecord insertChatRecord(ChatRecord record, MultipartFile file) {
        if (StringUtils.equalsIgnoreCase("0", record.getChatType())) {
            Assert.isTrue(StringUtils.isNotBlank(record.getTextContent()), "请输入有效的聊天内容");
        } else if (StringUtils.equalsIgnoreCase("1", record.getChatType())) {
            Assert.isTrue(file != null, "请上传有效的图片");
            String url = uploader.upload(file);
            record.setImageUrl(url);
        } else {
            Assert.isTrue(false, "上传类型无效");
        }
        Assert.isTrue(record.getReceiveTypeId()!= null, "目前是业务之外的非法聊天");
        daoChatRecordMapper.insertSelective(record);
        ChatStateRequest params = new ChatStateRequest();
        params.setChatSpace(record.getChatSpace());
        List<ChatState> list = daoChatStateMapper.listChatState(params);
        if(list.size()==0){
            ChatState chatState = new ChatState();
            chatState.setChatSpace(record.getChatSpace());
            chatState.setLastId(record.getId());
            if(record.getSendTypeId()<record.getReceiveTypeId()){//从聊天空间的发送者向接收者 正向
                chatState.setStatus("W");
                chatState.setSenderCount(1L);
            }
            else{
                chatState.setStatus("R");
                chatState.setReceiverCount(1L);
            }
            chatState.setChat("0");
            daoChatStateMapper.insertSelective(chatState);
        }
        else{
            ChatState chatState = list.get(0);
            chatState.setChatSpace(record.getChatSpace());
            chatState.setLastId(record.getId());
            if(record.getSendTypeId()<record.getReceiveTypeId()){//从聊天空间的发送者向接收者 正向
                chatState.setStatus("W");//wait to reply
                chatState.setSenderCount(chatState.getSenderCount()+1);
            }
            else{
                chatState.setStatus("R");//replay
                chatState.setReceiverCount(chatState.getReceiverCount()+1);
            }
//            chatState.setChat("0");
            chatState.setGmtModified(new Date());
            daoChatStateMapper.updateByPrimaryKeySelective(chatState);
        }
        return record;
    }


    // 根据条件查询列表
    public List<ChatState> listChatState(ChatStateRequest params){
        return this.daoChatStateMapper.listChatState(params);
    }
    // 根据条件查询列表 总数
    public int listChatStateCount(ChatStateRequest params){
        return this.daoChatStateMapper.listChatStateCount(params);
    }
    //批量更新
    @Transactional
    public String updateChatState(ChatStateRequest params){
        logger.info("目前更新聊天设置，传递过来的参数params是：{}",params);
        Assert.isTrue(params.getTypeId()!=null,"请指定端口来源,typeId:1(patient)2(doctor)3(pharmacist)");
        Assert.isTrue(params.getUserId()!=null ,"请指定端口对应的用户Id");
        Assert.isTrue(params.getChat()!=null ,"请指定要关闭的chat值");
        Date currentDate = new Date();
        if(params.getTypeId().longValue()==2){
            Doctor doctor = this.serviceDoctorService.selectByPrimaryKey(params.getUserId());
            Assert.isTrue(doctor !=null,"没有查询到有效的医生");
            Assert.isTrue(!StringUtils.equalsIgnoreCase(params.getChat(),doctor.getChat()),"修改无效，目前医生聊天状态与您要设置的一致");
            doctor.setChat(params.getChat());//这里必须要清楚，真实的医生表格聊天及时进行设置，曾经发生关系的是与t_chat_state中的chat进行设定
            doctor.setGmtModified(currentDate);
//            this.serviceRedisService.deleteChatStateCloseForRedis(params.getTypeId(),params.getUserId());//仅仅是为了测试验证，随时可以更改???
            this.serviceRedisService.isValidChatStateClose(params.getTypeId(),params.getUserId(),params.getAmount(),params.getField());//为提高效率，仅仅是做个普通的redies验证
            this.serviceDoctorService.updateDoctorSelective(doctor);
            this.serviceRedisService.setChatStateCloseForRedis(params.getTypeId(),params.getUserId(),params.getAmount(),params.getField());//放到后面，是因为redies没有办法回归

            ChatStateRequest reqChatState = new ChatStateRequest();
            reqChatState.setSearchKey(params.getSearchKey());
            //reqChatState.setStatus("W");//W 等待 R 回复
            List<ChatState> list =  this.listChatState(reqChatState);
            if(list.size()>0){
                List<String> listPushUserId = Lists.newArrayList();
                for(ChatState item:list){
                    String pushUserId = item.getChatSpace().replace("doctor"+doctor.getId(),"");
                    listPushUserId.add(pushUserId);
                    logger.info("要推送的患者 pushUserId----->{}",pushUserId);
                }
                StringBuilder sb = new StringBuilder();
                if("0".equals(doctor.getChat())){
                    sb.append(doctor.getTrueName())
                            .append("医生再次开通了健康咨询服务功能，您和该医生的咨询通道可以继续使用。");
                }
                else if("1".equals(doctor.getChat())){
                    sb.append(doctor.getTrueName())
                            .append("医生关闭了健康咨询服务功能，您和该医生的咨询通道将在24小时后关闭。");
                }
                String title = "医生 "+doctor.getTrueName()+("0".equals(doctor.getChat())?" 开通":" 关闭")+"咨询服务功能";
                String message = sb.toString();
                this.serviceMessageService.insertMessagesPersonByList(title,message,listPushUserId);//批量插入影响的列
                this.serviceAppPushService.pushListUserIdApp(1L,title,message,listPushUserId);
            }
            else{
                logger.info("目前医生聊天设置已经修改，但没有对应的患者不需要考虑批量更新及延迟操作");
                return "目前医生聊天设置已经修改，但没有对应的患者不需要考虑批量更新及延迟操作";
            }
        }
        else if(params.getTypeId().longValue()==3){
            Pharmacist doctor = this.servicePharmacistService.selectByPrimaryKey(params.getUserId());
            Assert.isTrue(doctor !=null,"没有查询到有效的药师");
            Assert.isTrue(!StringUtils.equalsIgnoreCase(params.getChat(),doctor.getChat()),"修改无效，目前药师聊天状态与您要设置的一致");
            doctor.setChat(params.getChat());
            doctor.setGmtModified(currentDate);
            this.serviceRedisService.isValidChatStateClose(params.getTypeId(),params.getUserId(),params.getAmount(),params.getField());//为提高效率，仅仅是做个普通的redies验证
            this.servicePharmacistService.updatePharmacistSelective(doctor);
            this.serviceRedisService.setChatStateCloseForRedis(params.getTypeId(),params.getUserId(),params.getAmount(),params.getField());

            ChatStateRequest reqChatState = new ChatStateRequest();
            reqChatState.setSearchKey(params.getSearchKey());
            //reqChatState.setStatus("W");//W 等待 R 回复
            List<ChatState> list =  this.listChatState(reqChatState);
            if(list.size()>0){
                List<String> listPushUserId = Lists.newArrayList();
                for(ChatState item:list){
                    String pushUserId = item.getChatSpace().replace("pharmacist"+doctor.getId(),"");
                    listPushUserId.add(pushUserId);
                    logger.info("要推送的患者 pushUserId----->{}",pushUserId);
                }
                StringBuilder sb = new StringBuilder();
                if("0".equals(doctor.getChat())){
                    sb.append(doctor.getTrueName())
                            .append("药师再次开通了健康咨询服务功能，您和该药师的咨询通道可以继续使用。");
                }
                else if("1".equals(doctor.getChat())){
                    sb.append(doctor.getTrueName())
                            .append("药师关闭了健康咨询服务功能，您和该药师的咨询通道将在24小时后关闭。");
                }
                String title = "药师 "+doctor.getTrueName()+("0".equals(doctor.getChat())?" 开通":" 关闭")+"咨询服务功能";
                String message = sb.toString();
                this.serviceMessageService.insertMessagesPersonByList(title,message,listPushUserId);//批量插入影响的列
                this.serviceAppPushService.pushListUserIdApp(1L,title,message,listPushUserId);
            }
            else{
                logger.info("目前药师聊天设置已经修改，但没有对应的患者不需要考虑批量更新及延迟操作");
                return "目前药师聊天设置已经修改，但没有对应的患者不需要考虑批量更新及延迟操作";
            }
        }
        else{
            return "目前业务不支持";
        }

        params.setGmtModified(currentDate);
        StringBuilder sb = new StringBuilder();
        if(StringUtils.equalsIgnoreCase("0",params.getChat()) ){//开通瞬间执行
            logger.info("注意：【开通聊天瞬间影响，没有延迟】");
            sb.append("开通聊天瞬间影响条目为:").append(this.updateChatStateBatch(params));//批量开通对应的影子chat开关
        }
        else if(StringUtils.equalsIgnoreCase("1",params.getChat()) ){//关闭有一定延迟
            logger.info("注意：【关闭有一定延迟影响】");
            Assert.isTrue(params.getAmount()!=null,"时长[amount]要有值");
            Assert.isTrue(params.getField()!=null && params.getField().intValue()>9 && params.getField().intValue()<14,"时间单位[field]要有值 10(小时HOUR)，12(分MINUTE),13(秒SECOND)");
            sb.append("关闭聊天需要在：");
            sb.append(params.getAmount());
            sb.append("单位时间");
            sb.append(params.getField());
            sb.append("后影响:");
            this.scheduleTask(params);
        }
        else{
            sb.append("不知道瞬间影响条目为:").append(this.updateChatStateBatch(params));
        }
        return sb.toString();
    }
    @Transactional
    public int updateChatStateBatch(ChatStateRequest params){
        return this.daoChatStateMapper.updateChatStateBatch(params);
    }

    public void scheduleTask(ChatStateRequest params) {
        // 时间类
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.SECOND,1);//主要目的是为了让TimerTask先处理，然后才有可能计划任务Task去补刀。
        Date realNow = cal.getTime();
        cal.add(params.getField().intValue(),params.getAmount().intValue());//.add(Calendar.SECOND,10);
        Date nextDay = cal.getTime();
        long secondAttach = 0;
        if(params.getField().intValue()==10){//小时
            secondAttach = params.getAmount().intValue()*3600;
        }
        else if(params.getField().intValue()==12){//分
            secondAttach = params.getAmount().intValue()*60;
        }
        else if(params.getField().intValue()==13){//秒
            secondAttach = params.getAmount().intValue();
        }
        TaskSchedule record = new TaskSchedule();
        record.setTypeId(params.getTypeId());
        record.setUserId(params.getUserId());
        record.setTaskDescription("咨询服务功能关闭 24小时后延迟 设定为1 单位域:"+params.getField()+" 长度:"+params.getAmount());
        record.setTaskParams(params.toJsonString());
        record.setTaskType(1L);//表示批量处理用户聊天类型
        record.setSchedule(nextDay);
        record.setAttach(secondAttach);
        //备用字段 record.setOther("");
        record.setGmtCreate(realNow);
        record.setRemarks("创建定时任务保存入库查看");
        this.daoTaskScheduleMapper.insertSelective(record);
        params.setTaskScheduleId(record.getId());
        //TimerTask 最大的弊端是没有DB记录，完全靠系统，无法补救，所以采用上述的计划任务，有定时任务来监控处理
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                logger.info("[TimerTask]核心执行中，对应的查询条件:{} 影响条目是：{}",params,updateChatStateInfo(params));
            }
            @Transactional
            private int updateChatStateInfo(ChatStateRequest params) {
                Assert.isTrue(daoChatStateMapper!=null,"daoChatStateMapper=null,出现异常情况，停止进程timer-0");
                TaskSchedule taskSchedule = daoTaskScheduleMapper.selectByPrimaryKey(params.getTaskScheduleId());
                int updateChatStateBatchCount = 0;
                if(StringUtils.equalsIgnoreCase("0", taskSchedule.getStatus())){
                    updateChatStateBatchCount = daoChatStateMapper.updateChatStateBatch(params);
                    taskSchedule.setGmtModified(new Date());
                    taskSchedule.setStatus("1");//1表示是TimerTask计划时间处理 2表示是 task 扫描处理
                    taskSchedule.setTaskResult("TimerTask");
                    taskSchedule.setRemarks("通过TimerTask来自动定向执行聊天批处理设置状态");
                    daoTaskScheduleMapper.updateByPrimaryKeySelective(taskSchedule);
                    logger.info("更新TaskSchedule记录表，如果此处有闪失，有定时任务去刷新处理");
                }
                else{
                    logger.info("状态已经被修改");
                }
                return updateChatStateBatchCount;
            }
        };
        Timer timer = new Timer();
        logger.info("<<[TimerTask]通过计划 设定要执行的信息内容：{}",record);
        timer.schedule(task,nextDay);
        // 1小时的毫秒设定
//        long period = 60 * 60 * 1000;
//        timer.schedule(task,nextDay,period);//这个是周期频率反复执行
    }

    @Transactional
    public int scanningTaskSchedule(TaskScheduleRequest params){
        int updateChatStateBatchCount = 0;
        List<TaskSchedule> taskScheduleList = this.daoTaskScheduleMapper.listTaskSchedule(params);
        //where timestampdiff(SECOND,t.gmt_create,NOW()) > t.attach
        for(TaskSchedule taskSchedule:taskScheduleList){
            ChatStateRequest chatStateRequest = com.yier.platform.common.util.JsonUtils.fromJson(taskSchedule.getTaskParams(),ChatStateRequest.class);
            int currentBatchCount  = daoChatStateMapper.updateChatStateBatch(chatStateRequest);
            taskSchedule.setGmtModified(new Date());
            taskSchedule.setStatus("2");//1表示是TimerTask计划时间处理 2表示是 task 扫描处理
            taskSchedule.setTaskResult("通过Task 定时任务来补刀执行聊天批处理设置状态");
            daoTaskScheduleMapper.updateByPrimaryKeySelective(taskSchedule);
            logger.info("[scanningTaskSchedule]本次通过定时任务补刀操作条目为：{}",currentBatchCount);
            updateChatStateBatchCount += currentBatchCount;
        }
        return updateChatStateBatchCount;
    }

}
