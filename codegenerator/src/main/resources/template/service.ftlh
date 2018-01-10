<#import "global.ftlh" as global>
package com.hexin.<@global.obj/>.service;

import com.hexin.<@global.obj/>.domain.<@global.class/>;
<@global.pageable/>
import java.util.List;
import java.util.Map;
<@global.fileComment/>
public interface <@global.class/>Service {

    List<<@global.class/>> <@global.obj/>List(Map<String, Object> params, Pageable pageable); // 分页查询

    List<<@global.class/>> <@global.obj/>List(Map<String, Object> params); // 不分页查询

    <@global.class/> queryOneById(int id); // 按照Id查询一个<@global.obj/>对象

    <@global.class/> queryOneByMap(Map<String, Object> params); // 按任意参数查询一个<@global.obj/>对象

    int countByParams(Map<String, Object> params); // 查询符合条件的记录的个数

    int add(<@global.class/> <@global.obj/>); // 新增

    int modify(<@global.class/> <@global.obj/>); // 修改

    int remove(int id); // 删除
    int remove(<@global.class/> <@global.obj/>); // 删除
}