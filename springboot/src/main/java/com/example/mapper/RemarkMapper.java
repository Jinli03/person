package com.example.mapper;

import com.example.entity.Remark;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RemarkMapper {

    List<Remark> selectAll(Remark remark);

    @Select("select * from `remark` where id = #{id}")
    Remark selectById(Integer id);

    void insert(Remark remark);

    void updateById(Remark remark);

    @Delete("delete from `remark` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `remark` where username = #{username}")
    Remark selectByUsername(String username);

    @Select("select * from `remark` where article_id = #{id}")
    List<Remark> selectByArticleId(Integer id);
}
