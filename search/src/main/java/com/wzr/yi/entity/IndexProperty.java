package com.wzr.yi.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.wzr.yi.util.GetOrDefault;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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


    public IndexProperty(JSONObject obj) {
        this.eid = (String) obj.get("id");
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
        this.resourceWap = (List<String>) obj.get("resourceWap");
        this.resourcePc = (List<String>) obj.get("resourcePc");
        this.resourceRank = GetOrDefault.getInteger(obj, "rank");
        this.year = GetOrDefault.getInteger(obj, "yearOrigin");
        this.hotCount = GetOrDefault.getInteger(obj, "hotCount");
        this.feature = "";
        this.status = (String) obj.get("status");
        this.describeInfo = GetOrDefault.getString(obj, "describe");
    }
}
