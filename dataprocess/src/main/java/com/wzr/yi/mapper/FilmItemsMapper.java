package com.wzr.yi.mapper;

import com.wzr.yi.entity.FilmItems;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/2/27 5:56 下午
 */
public interface FilmItemsMapper {
    /**
     *
     *@param name
     * @param num
     * @return
     */
    @Select("select * from stat_keyword where search_type = #{name} and status = 1 order by score desc limit #{num}")
    List<FilmItems> findCountSearchsByNameNum(@Param("name") String name, @Param("num") Integer num);

    void insertBulkFilmItems();
}
