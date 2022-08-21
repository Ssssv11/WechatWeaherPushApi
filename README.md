# WechatWeaherPushApi
程序员的浪漫

# 申请百度 Web 服务 API

1. 进入百度地图开发平台，在开发文档中的 Web 服务 API 中选择[天气查询](https://lbsyun.baidu.com/index.php?title=webapi/weather)
2. 选择服务文档一栏在在下方第一个表格中找到 「ak」一行，点击后面的 「API 控制台」进行申请(地址：https://lbsyun.baidu.com/apiconsole)
3. 申请完成后点击创建应用，填写相关信息后(**国内天气查询必选**)在 「IP 白名单」中填入 `0.0.0.0/0`
4. 创建完成后在控制台复制 「**访问应用（AK）**」并保存

# 申请微信服务号测试号

1. 进入官网 https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Requesting_an_API_Test_Account.html
2. 点击右侧 「进入微信公众帐号测试号申请系统」 网址：https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
3. 进行登录后保存 「**appID**」与「**appsecret**」
4. 在下方找到 「测试号二维码」并扫描关注，复制右边显示的「**微信号**」并保存
5. 点击下方 「模板消息接口」中的新增模版测试
6. 标题任拟，内容按照提示，如：

```
今天是 {{date.DATA}} 天气{{weather.DATA}} 最高气温{{high.DATA}} °C，最低气温{{low.DATA}} °C 风力{{windClass.DATA}} ，风向 {{windDir.DATA}} 今天是在一起第 {{anniversary.DATA}} 天
```
每个变量用 `{{}}` 括起来并在后面加 `.DATA`

7. 创建好后复制 「模版 ID」并保存

# 运行代码

1. 首先点击上方 「Code」选择「DownLoad ZIP」或直接使用 `git clone` 命令
2. 用 IDE 打开代码后先更新 Maven 库
3. 首先修改 com.ninn.utils 包下的 `Pusher` 中的参数：

```java
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

    // ...
```

- 其中 “微信服务测试号 ID” 为之前保存的 「appID」，“微信服务测试号 密钥” 为「appSecret」（引号不能去掉）
- “目标用户 openID” 为关注公众号后生成的「微信号」
- “模版消息 ID” 为创建模版消息后生成的 「模版 ID」

4. 修改 com.ninn.utils 包下的 `WeatherUtils` 中的参数：

```java
public static Weather getWeather() {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> map = new HashMap<String, String>();
    map.put("district_id", "目的地行政代码");
    map.put("data_type", "all");
    map.put("ak", "百度 API 申请的 ak");
    String res = restTemplate.getForObject(
            "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
            String.class,
            map);
    
    // ...
```

- 其中，“目的地行政代码” 为想要查询天气的地方的行政代码，可以查出来
- ”百度 API 申请的 ak“ 为 API 控制台中保存的「访问应用（AK）」

5. 修改 com.ninn.utils 包下的 `AnniversaryUtil` 中的参数：

```java
  public static int getAnniversary() {
      return calculateDays("2020-11-15");
  }

  public static int getBirthday1() {
      try {
          return calculateBirthday("2000-11-15");
      } catch (ParseException e) {
          e.printStackTrace();
      }
      return 0;
  }

  public static int getBirthday2() {
      try {
          return calculateBirthday("2001-01-18");
      } catch (ParseException e) {
          e.printStackTrace();
      }
      return 0;
  }
```
只需要将这三个时间修改：第一个为纪念日，后两个为双方生日

6. 修改定时执行（可选）

默认定时执行为每 30 秒一次，可以修改 com.ninn 包下的 `WechatpushApplication` 中的参数：

```java
  @Scheduled(cron = "1/30 * * * * ?")
//    @Scheduled(cron = "0 30 7 * * ?")
  public void goodMorning(){
      Pusher.push();
  }
```

可以使用注释的代码，表示每天早上 7:30 执行。或根据工具自定义：https://www.matools.com/cron

7. 运行代码检查错误，若只需要运行一次只需运行 com.ninn.utils 包下的 `Pusher` 中的 `main` 方法
