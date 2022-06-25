# yisearch-system

本系统设计，基于elasticsearch的影视搜索系统，且集成论坛交流系统，搜集用户数据，

达到个性化推荐的影视搜索系统。支持高并发、高可用、

爬取的数据通过dataprocess融合、提纯离线写入es全量数据库，将部分字段打平提纯后写入索引数据库entity_index, 

主要考虑数据推送过来的实时性、生态的丰富性、性能的稳定性、推荐的高度相关性

search模块为搜索接入模块

![搜索结构](https://tva1.sinaimg.cn/large/e6c9d24egy1h388mmp42ij20uo0u0jt4.jpg)




