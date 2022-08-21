package com.ninn.utils;


import com.ninn.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Value;

public class Pusher {

    @Value("${diy.appId}")
    private static String APP_ID;
    @Value("${diy.appSecret}")
    private static String APP_SECRET;

    public static void main(String[] args) {
        push();
    }

    public static void push() {
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("微信服务测试号 ID");
        wxStorage.setSecret("微信服务测试号 密钥");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("目标用户 openID")
                .templateId("模版消息 ID")
                .build();
        Weather weather = WeatherUtils.getWeather();
        templateMessage.addData(new WxMpTemplateData("date", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("weather", weather.getText_now(), "#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
        templateMessage.addData(new WxMpTemplateData("temperature", weather.getTemp() + "", "#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("windClass", weather.getWind_class() + "", "#42B857"));
        templateMessage.addData(new WxMpTemplateData("windDir", weather.getWind_dir() + "", "#B95EA3"));
        templateMessage.addData(new WxMpTemplateData("anniversary", AnniversaryUtil.getAnniversary() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("birthday1", AnniversaryUtil.getBirthday1() + "", "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("birthday2", AnniversaryUtil.getBirthday2() + "", "#FFA500"));
        String info = "xx快乐✨\n别的女孩有的xx也要有！";
        if (AnniversaryUtil.getAnniversary() % 365 == 0) {
            info = "今天是恋爱" + (AnniversaryUtil.getAnniversary() / 365) + "周年纪念日！";
        }
        if (AnniversaryUtil.getBirthday1() == 0) {
            info = "今天是xx生日，生日快乐呀！";
        }
        if (AnniversaryUtil.getBirthday2() == 0) {
            info = "今天是xx生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("info", info, "#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
