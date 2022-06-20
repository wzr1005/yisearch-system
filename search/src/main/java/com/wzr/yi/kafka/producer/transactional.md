Kafka事务五个API

1. 初始化事务

2。 开启事务

3。 在事务内提交已经消费的偏移量（主要用于消费者）

4。 提交事务

5。 放弃事务（类似于回滚操作）

数据有序    多分区

broker最多缓存5个请求。

Kafka在1.x版本保证单分区数据有序，条件是最大缓存数量为1，不需要考虑是否开启幂等性

之后的版本，保证单分区有序，条件如下

1。 未开启幂等性，则broker最大缓存消息数量为1

2。 开启幂等性，最大缓存消息数量小于等于5， seqNumber单调递增

![](https://tva1.sinaimg.cn/large/e6c9d24egy1h3cxvnlst9j21l40imads.jpg)

zookeeper存储了客户端哪些消息？

![](https://tva1.sinaimg.cn/large/e6c9d24egy1h3cy3eeqkej20ti02y0sx.jpg)


