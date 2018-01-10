<#import "global.ftlh" as global>
package com.hexin.<@global.obj/>.repository;

import com.hexin.<@global.obj/>.domain.<@global.class/>;
<@global.baseDao/>
import com.hexin.utils.mybatis.paginator.PageList;
import com.hexin.utils.mybatis.paginator.Pageable;
import java.util.Map;
<@global.fileComment/>
public interface <@global.class/>Dao extends MyBatisDao<<@global.class/>> {

    PageList<<@global.class/>> findBy(Map<String, Object> map, Pageable page);
    <@global.class/> findOneById(int id);
    <@global.class/> findByMap(Map<String, Object> params);
}