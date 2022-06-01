package com.wzr.yi.Mapper;

import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/5/4 10:08 下午
 */
@Mapper
@Repository
public interface IndexPropertyMapper {

    @Select("select VERSION();")
    String findVersions();

    @Insert({"<script>" +
            "INSERT INTO index_property (name, alias, eid, serial_name, starring, director, gene_type, resource_wap, " +
            "resource_pc, resource_rank, year, hot_count, status, describe_info) VALUES " +
            "<foreach collection = 'list' item = 'list' index = 'index' separator = ','> " +
            "(#{list.name}, #{list.alias}, #{list.eid}, #{list.serialName}, #{list.starring}, #{list.director}," +
            "#{list.geneType}, #{list.resourceWap}, #{list.resourcePc}, #{list.resourceRank}, #{list.year}, #{list.hotCount}, #{list.status}, #{list.describeInfo}) " +
            "</foreach>" +
            "</script>"

    })
    void insertBatchRecord(@Param("list")List<IndexPropertyDto> list);
}
