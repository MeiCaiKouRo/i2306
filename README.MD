# Java实战高性能12306售票系统

## 一、SQL初始化

### 0、重点说明

按课程给出的代码，至少需要本地创建7个database：

（可参考 common/src/main/resources/application-common.yml）

1、基础数据源：test1

2、订单数据源：t_train_order

3、座位数据源5个：t_train_seat_[1-5]

同时，需要保证数据库字符集为utf8mb4 （utf8也可以）

课程里默认DB的登录用户为:root，密码为:466420182，此处需要适配本地DB用户名和密码进行调整

此处可以通过修改代码来调整分库分表策略

### 1、基础数据源

sql/基础数据源.sql

如果需要使用课程里的数据，使用：sql/基础数据源含数据.sql

### 2、订单数据源

sql/订单库.sql

### 3、座位数据源

sql/座位库-分库1.sql

sql/座位库-分库2.sql

sql/座位库-分库3.sql

sql/座位库-分库4.sql

sql/座位库-分库5.sql


## 二、项目启动说明

### 0、强烈建议按照课程视频流程一步一步来进行

### 1、涉及的组件需要先启动，且保证连接正常

DB、Redis、RabbitMQ、ElasticSearch、Canal、ZK

### 2、需要启动的项目

完整需要同时运行 backend、frontend、sync 三个应用，请注意配置不同的端口

如果单独使用管理员后台，启动 backend 和 sync 即可

如果单独使用用户前台，启动 frontend 和 sync 即可