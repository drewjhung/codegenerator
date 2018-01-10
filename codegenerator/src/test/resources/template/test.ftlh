<#import "com.ftlh" as global>
package com.hexin.<@global.obj/>.service;

import com.hexin.<@global.obj/>.domain.<@global.class/>;
import java.util.List;
import java.util.Map;

<@global.fileComment/>
public interface <@global.class/>Service {
  List<<@global.class/>> queryAllPageable(Map<String, Object> params); // 分页查询
  List<<@global.class/>> queryAll(Map<String, Object> params); // 不分页查询
  <@global.class/> queryOneById(int id); // 按照Id查询一个<@global.obj/>对象
  <@global.class/> queryOneByMap(Map<String, Object> params); // 按任意参数查询一个<@global.obj/>对象
  int add(<@global.class/> <@global.obj/>); // 新增
  int update(<@global.class/> <@global.obj/>); // 修改
  int delete(<@global.class/> <@global.obj/>); // 删除
}
