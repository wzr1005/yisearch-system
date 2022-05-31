package com.wzr.yi.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

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

    private String starring;

    private String director;

    private String geneType;

    private String alias;

    private String resourceWap;

    private String resourcePc;

    private int rank;

    private int year;

    private int hotCount;

    private String feature;

    private String status;

    private String describe;


    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", eid='" + eid + '\'' +
                ", name='" + name + '\'' +
                ", serialName='" + serialName + '\'' +
                ", starring='" + starring + '\'' +
                ", director='" + director + '\'' +
                ", geneType='" + geneType + '\'' +
                ", alias='" + alias + '\'' +
                ", resourceWap='" + resourceWap + '\'' +
                ", resourcePc='" + resourcePc + '\'' +
                ", rank=" + rank +
                ", year=" + year +
                ", hotCount=" + hotCount +
                ", feature='" + feature + '\'' +
                ", status='" + status + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

    public IndexProperty(JSONObject obj) {
        this.eid = (String) obj.get("eid");
        this.name = (String) obj.get("name");
        this.serialName = (String) obj.get("serialName");
        this.starring = (String) obj.get("starring");
        this.director = (String) obj.get("director");
        this.geneType = (String) obj.get("geneType");
        this.alias = (String) obj.get("alias");
        this.resourceWap = (String) obj.get("resourceWap");
        this.resourcePc = (String) obj.get("resourcePc");
        this.rank = (int) obj.get("rank");
        this.year = (int) obj.get("year");
        this.hotCount = (int) obj.get("hotCount");
        this.status = (String) obj.get("status");
        this.describe = (String) obj.get("describe");
    }
}
