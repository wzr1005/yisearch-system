package com.wzr.yi.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.wzr.yi.common.utils.GetOrDefault.*;


/**
 * @autor zhenrenwu
 * @date 2022/5/30 12:58 上午
 */
@Data
public class IndexProperty implements Serializable {
    //没有implements Serializable，你就不能通过rmi(包括ejb)提供远程调用。
    //serialization 允许你将实现了Serializable接口的对象转换为字节序列，这些字节序列可以被完全存储以备以后重新生成原来的对象。
    //1。Java的RMI(remote method invocation).RMI允许象在本机上一样操作远程机器上的对象。当发送消息给远程对象时，就需要用到serializaiton机制来发送参数和接收返回直。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String eid;

    private String name;

    private String serialName;

    private List<String> starring;

    private List<String> director;

    private List<String> geneType;

    private List<String> alias;

    private List<String> resourceWap;

    private List<String> resourcePc;

    private int resourceRank;

    private int year;



    private int hotCount;

    private String feature;

    private String status;

    private String describeInfo;

    private String playLink;
    private String poster;

    @Override
    public String toString() {
        return "IndexProperty{" +
                "id='" + id + '\'' +
                ", eid='" + eid + '\'' +
                ", name='" + name + '\'' +
                ", serialName='" + serialName + '\'' +
                ", starring=" + starring +
                ", director=" + director +
                ", geneType=" + geneType +
                ", alias=" + alias +
                ", resourceWap=" + resourceWap +
                ", resourcePc=" + resourcePc +
                ", resourceRank=" + resourceRank +
                ", year=" + year +
                ", hotCount=" + hotCount +
                ", feature='" + feature + '\'' +
                ", status='" + status + '\'' +
                ", describeInfo='" + describeInfo + '\'' +
                '}';
    }

    public IndexProperty() {
    }
    public IndexProperty(JSONObject obj) {
        this.eid = (String) obj.get("id");
        this.id = this.eid;
        this.name = (String) obj.get("name");
        this.serialName = (String) obj.get("serialName");
        this.starring = (List<String>)obj.get("starring");
        this.director = (List<String>) obj.get("director");
        Object genreList = obj.get("genreList");
        List<JSONObject> genreListobj = (List<JSONObject>)genreList;
        List<String> genreStrList = new ArrayList<>();
        genreListobj.forEach(g->{
            genreStrList.add((String) g.get("type"));
        });
        this.geneType = genreStrList;
        this.alias = (List<String>) obj.get("alias");
        this.resourceWap = (List<String>) obj.get("resourceWapList");
        this.resourcePc = (List<String>) obj.get("resourcePcList");
        this.resourceRank = obj.getInteger("rank");
        this.year = obj.getInteger("yearOrigin");
        this.hotCount = obj.getInteger("hotCount");
        this.feature = "";
        this.status = (String) obj.get("status");
        this.describeInfo = getString(obj, "describe");
        this.playLink = getPlayLinkFromWapList(obj.getJSONArray("resourceWapList"));
        this.poster = getString(obj, "horizontalPoster");
    }

    public IndexProperty(String s) {
        JSONObject obj = null;
        try{
            obj = JSONObject.parseObject(s);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(s);
        }

        this.eid = (String) obj.get("id");
        this.id = this.eid;
        this.name = (String) obj.get("name");
        this.serialName = (String) obj.get("serialName");
        this.starring = (List<String>)obj.get("starring");
        this.director = (List<String>) obj.get("director");
        Object genreList = obj.get("genreList");
        List<JSONObject> genreListobj = (List<JSONObject>)genreList;
        List<String> genreStrList = new ArrayList<>();
        genreListobj.forEach(g->{
            genreStrList.add((String) g.get("type"));
        });
        this.geneType = genreStrList;
        this.alias = (List<String>) obj.get("alias");
        this.resourceWap = (List<String>) obj.get("resourceWapList");
        this.resourcePc = (List<String>) obj.get("resourcePcList");
        this.resourceRank = obj.getInteger("rank");
        this.year = obj.getInteger("yearOrigin");
        this.hotCount = obj.getInteger("hotCount");
        this.feature = "";
        this.status = (String) obj.get("status");
        this.describeInfo = getString(obj, "describe");
        this.playLink = getPlayLinkFromWapList(obj.getJSONArray("resourceWapList"));
        this.poster = getString(obj, "horizontalPoster");
        if(this.poster.equals("")){
            this.poster = getString(obj, "verticalPoster");
        }
    }

    public static void main(String[] args) {
        String s = "{\"id\": \"244874880\", \"name\": \"红死病\", \"alias\": [\"The Masque of the Red Death\", \"红死病的面具\"], \"url\": \"https://baike.baidu.com/api/lemmapv?id=300006d9a03d776e093d5354\", \"verticalPoster\": \"http://inews.gtimg.com/newsapp_ls/0/14861953959/0\", \"horizontalPoster\": \"\", \"stillPoster\": \"\", \"languageList\": [\"英语\", \"拉丁语\"], \"descriptionDict\": {\"origin\": \"故事发生在十二世纪中期，帕斯伯亲王（文森特·普莱斯 Vincent Price 饰）因为信奉邪恶的撒旦而对当地的农民实行重税制度，使得当地居民们苦不堪言。帕斯伯亲王十分喜爱一位名叫弗兰西斯卡（珍·爱舍 Jane Asher 饰）的女子，为了逼迫她就范，帕斯伯亲王不惜逮捕和拘禁了范的父亲，同时被拘禁的还有当地的农民领袖吉诺（David Weston 饰） 朱利安（哈泽尔·考特 Hazel Court 饰）苦苦的恋慕着亲王，却无法得到他的任何回应。于是，朱利安决定帮助范的父亲和吉诺越狱，不幸的是，计划失败了。范的父亲企图刺杀亲王，却反被杀死，幸存的吉诺遭到了流放。危险的红死病在乡间蔓延开来，吉诺遇见了一个神秘的红衣人，在红衣人的帮助下，他展开了他的复仇大计。\", \"short\": \"\", \"manual\": \"\"}, \"scoreList\": [{\"source\": \"douban\", \"value\": 7.1}, {\"source\": \"电影网\", \"value\": 7.1}], \"genreList\": [{\"type\": \"恐怖\", \"score\": 0.0}, {\"type\": \"惊悚\", \"score\": 0.0}], \"starring\": [\"文森特·普莱斯\", \"哈泽尔·考特\", \"珍·爱舍\", \"Norris Boyd\", \"Julian Burton\", \"Gladys Davison\", \"Nigel Green\", \"大卫·戴维斯\", \"Len Martin\", \"David Wishart\", \"Alan Dalton\", \"Joan Palethorpe\", \"Harvey Hall\", \"Norman McDowell\", \"Maureen Sims\", \"Hugh Morton\", \"Doreen Dawn\", \"Selina Wylie\", \"Edith Gey\", \"帕特里克·马基\", \"Gale Law\", \"David Allen\", \"Fred Peters\", \"Rosemarie Dunham\", \"Roy Staite\", \"Brian Hewlett\", \"Dorothy Anelay\", \"Julian Bolt\", \"Caroline Symonds\", \"Gerry Atkins\", \"罗伯特·布朗\", \"Jenny Till\", \"Delia Linden\", \"Paul Whitsun-Jones\", \"约翰·斯通\", \"Robert de Warren\", \"Jane Evans\", \"戴维·韦斯顿\", \"比尔·欧文\", \"Dorothy Fraser\", \"Angela Symonds\", \"Janet Kedge\", \"Sally Gilpin\", \"盖伊·布朗\", \"Brigitte Kelly-Esp..\", \"Tony Manning\", \"Sarah Brackett\", \"Terry Gilbert\", \"Ronald Curran\", \"Verina Greenlaw\", \"Seraphina Lansdown\", \"Jean Lodge\", \"John Westbrook\", \"Skip Martin\", \"Ricky Clarke\", \"Jill Bathurst\", \"Janet Hall\", \"Joanna Kubik\", \"Bertie Green\", \"Stanley Tiller\"], \"castList\": [{\"name\": \"文森特·普莱斯\", \"role\": \"Prince Prospero\", \"type\": \"饰演\", \"castIcon\": \"http://inews.gtimg.com/newsapp_ls/0/14862224501/0\", \"characterIcon\": \"\", \"score\": 29581.0, \"hotCount\": 0, \"eid\": \"243068774\"}, {\"name\": \"哈泽尔·考特\", \"role\": \"\", \"type\": \"饰演\", \"castIcon\": \"http://inews.gtimg.com/newsapp_ls/0/14861953940/0\", \"characterIcon\": \"\", \"score\": 906.0, \"hotCount\": 0, \"eid\": \"253511235\"}, {\"name\": \"珍·爱舍\", \"role\": \"\", \"type\": \"饰演\", \"castIcon\": \"http://inews.gtimg.com/newsapp_ls/0/14862180674/0\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"241945702\"}, {\"name\": \"帕特里克·马基\", \"role\": \"\", \"type\": \"饰演\", \"castIcon\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1560787516.53.jpg\", \"characterIcon\": \"\", \"score\": 8026.0, \"hotCount\": 0, \"eid\": \"206915035\"}, {\"name\": \"Skip Martin\", \"role\": \"Hop Toad\", \"type\": \"饰演\", \"castIcon\": \"http://img31.mtime.cn/ph/261/1076261/1076261_1280X720X2.jpg\", \"characterIcon\": \"\", \"score\": 2.9911, \"hotCount\": 0, \"eid\": \"328026774\"}, {\"name\": \"罗伯特·布朗\", \"role\": \"Guard\", \"type\": \"饰演\", \"castIcon\": \"http://inews.gtimg.com/newsapp_ls/0/14862219766/0\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"241825435\"}, {\"name\": \"盖伊·布朗\", \"role\": \"\", \"type\": \"饰演\", \"castIcon\": \"https://img2.doubanio.com/view/celebrity/raw/public/p1374552737.03.jpg\", \"characterIcon\": \"\", \"score\": 1990.0, \"hotCount\": 0, \"eid\": \"241811050\"}, {\"name\": \"哈泽尔·考特\", \"role\": \"茱莉安娜\", \"type\": \"饰演\", \"castIcon\": \"\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"\"}, {\"name\": \"杰尔·格林\", \"role\": \"帕斯伯亲王\", \"type\": \"饰演\", \"castIcon\": \"\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"\"}, {\"name\": \"珍·洛奇\", \"role\": \"Scarlatti's wife\", \"type\": \"饰演\", \"castIcon\": \"\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"\"}, {\"name\": \"盖伊·布朗\", \"role\": \"Señora Escobar\", \"type\": \"饰演\", \"castIcon\": \"\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"\"}, {\"name\": \"帕特里克·马吉\", \"role\": \"Alfredo\", \"type\": \"饰演\", \"castIcon\": \"\", \"characterIcon\": \"\", \"score\": 0.0, \"hotCount\": 0, \"eid\": \"\"}], \"directorList\": [{\"name\": \"罗杰·科曼\", \"type\": \"\", \"directorIcon\": \"http://inews.gtimg.com/newsapp_ls/0/14862208489/0\", \"score\": 99929.0, \"eid\": \"405766390\"}], \"season\": 0.0, \"seasonName\": \"\", \"seriesName\": \"\", \"seriesVersion\": [], \"resourceWapList\": [{\"source\": \"电影网\", \"score\": 7.1, \"rank\": 2, \"updateTimeTxt\": \"\", \"pay\": 2, \"videoList\": [{\"vid\": \"\", \"kgRid\": \"002701024af9b19f1f09b3a454777fffe0180c6\", \"languageList\": [], \"episode\": 0, \"imgUrl\": \"\", \"title\": \"红死病（译制字幕版）\", \"summary\": \"帕斯伯亲王因为信奉邪恶的撒旦而对当地的农民实行重税制度，使得当地居民们苦不堪言。帕斯伯亲王十分喜爱一位名叫弗兰西斯卡的女子，为了逼迫她就范，帕斯伯亲王不惜逮捕和拘禁了范的父亲，同时被拘禁的还有当地的农民领袖吉诺。\", \"isNotice\": 0, \"guestList\": [], \"isNew\": 0, \"stillImg\": \"\", \"isDeath\": 0, \"playright\": [], \"longVideoList\": [], \"pay\": 1, \"playLink\": \"http://vip.1905.com/play/1513461.shtml?__hz=8c7bbbba95c10259\", \"naUrl\": \"\", \"xcxUrl\": \"\", \"rank\": 0.0, \"duration\": 4952}], \"playright\": [], \"longVideoList\": []}], \"resourcePcList\": [{\"source\": \"电影网\", \"score\": 7.1, \"rank\": 2, \"updateTimeTxt\": \"\", \"pay\": 2, \"videoList\": [{\"vid\": \"\", \"kgRid\": \"002701024af9b19f1f09b3a454777fffe0180c6\", \"languageList\": [], \"episode\": 0, \"imgUrl\": \"\", \"num\": 0, \"title\": \"红死病（译制字幕版）\", \"summary\": \"帕斯伯亲王因为信奉邪恶的撒旦而对当地的农民实行重税制度，使得当地居民们苦不堪言。帕斯伯亲王十分喜爱一位名叫弗兰西斯卡的女子，为了逼迫她就范，帕斯伯亲王不惜逮捕和拘禁了范的父亲，同时被拘禁的还有当地的农民领袖吉诺。\", \"isNotice\": 0, \"guestList\": [], \"isNew\": 0, \"stillImg\": \"\", \"isDeath\": 0, \"playLink\": \"http://vip.1905.com/play/1513461.shtml?__hz=8c7bbbba95c10259\", \"pay\": 1, \"rank\": 0.0, \"duration\": 4952}]}], \"definition\": [\"高清\"], \"duration\": 5340, \"publishInfoList\": [{\"area\": \"欧美\", \"date\": \"1964\"}, {\"area\": \"美国\", \"date\": \"1964-06-24\"}], \"publishInfoOriList\": [{\"area\": \"欧美\", \"date\": \"1964\"}, {\"area\": \"美国\", \"date\": \"1964-06-24\"}], \"yearOrigin\": 1964, \"area\": \"美国\", \"productDate\": \"1964-06-24\", \"entityType\": \"电影\", \"hotCount\": 34814, \"playStatus\": 1, \"doubanComCount\": 711, \"hotFlag\": 0, \"hotnameList\": [], \"bgColor\": {\"light\": \"#FEE2D8\", \"dark\": \"#AD674E\"}, \"shortvideoList\": [], \"channelInfoList\": [], \"status\": \"已下映\", \"totalNum\": 0, \"playNum\": 0, \"pv\": 0, \"pay\": 2, \"awardInfoList\": [], \"authorList\": [], \"guestList\": [], \"hostList\": [], \"filterMsg\": {\"filterStatus\": 0, \"msgList\": [\"CP方有资源\"]}, \"mediaCategory\": \"正片\", \"doubanScore\": 7.1, \"director\": [\"罗杰·科曼\"], \"resourceWap\": [\"电影网\"], \"resourcePc\": [\"电影网\"], \"isDelete\": 0, \"delTime\": \"\", \"updateTime\": \"2022-05-10 18:14:17\", \"experimentGroup\": [\"test\"], \"operateGroup\": \"total\"}\n";
        JSONObject obj = JSON.parseObject(s);
        System.out.println();
    }
}
