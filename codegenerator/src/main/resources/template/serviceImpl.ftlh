<#import "global.ftlh" as global/>
package com.hexin.<@global.obj/>.service.impl;

import com.hexin.<@global.obj/>.domain.<@global.class/>;
import com.hexin.<@global.obj/>.repository.<@global.class/>Dao;
import com.hexin.<@global.obj/>.service.<@global.class/>Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
<@global.pageable/>
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
<@global.fileComment/>
@Service
public class <@global.class/>ServiceImpl implements <@global.class/>Service {

    private static final Logger logger = LoggerFactory.getLogger(<@global.class/>ServiceImpl.class);

    @Autowired private <@global.class/>Dao <@global.obj/>Dao;

    /**
    * @author code-generator
    * @function 按条件分页查询
    * @param params 查询参数
    * @param pageable 分页对象
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {SQLException.class, Exception.class})
    public List<<@global.class/>> <@global.obj/>List(Map<String, Object> params, Pageable pageable) {
        return <@global.obj/>Dao.findBy(params, pageable);
    }

    /**
    * @author code-generator
    * @function 按条件查询
    * @param params 查询参数
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {SQLException.class, Exception.class})
        public List<<@global.class/>> <@global.obj/>List(Map<String, Object> params) {
        return <@global.obj/>Dao.findBy(params);
    }

    /**
    * @author code-generator
    * @function 按主键id查询唯一对象
    * @param id 主键id
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {SQLException.class, Exception.class})
        public <@global.class/> queryOneById(int id) {
        return id > 0 ? <@global.obj/>Dao.findOneById(id) : null;
    }

    /**
    * @author code-generator
    * @function 按参数查询唯一对象
    * @param params 查询参数
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {SQLException.class, Exception.class})
        public <@global.class/> queryOneByMap(Map<String, Object> params) {
        return <@global.obj/>Dao.findByMap(params);
    }

    /**
    * @author code-generator
    * @function 按参数查询记录个数
    * @param params 查询参数
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = {SQLException.class, Exception.class})
    public int countByParams(Map<String, Object> params) {
        List<<@global.class/>> list = <@global.obj/>List(params);
        return list == null ? 0 : list.size();
    }

    /**
    * @author code-generator
    * @function 添加 <@global.obj/>
    * @param <@global.obj/>
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class, Exception.class})
    public int add(<@global.class/> <@global.obj/>) {
        return <@global.obj/> != null ? <@global.obj/>Dao.insert(<@global.obj/>) : 0;
    }

    /**
    * @author code-generator
    * @function 修改 <@global.obj/>
    * @param <@global.obj/>
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class, Exception.class})
    public int modify(<@global.class/> <@global.obj/>) {
        return <@global.obj/> != null ? <@global.obj/>Dao.update(<@global.obj/>) : 0;
    }

    /**
    * @author code-generator
    * @function 删除 <@global.obj/>
    * @param <@global.obj/>
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class, Exception.class})
        public int remove(<@global.class/> <@global.obj/>) {
        return <@global.obj/> != null ? <@global.obj/>Dao.delete(<@global.obj/>) : 0;
    }

    /**
    * @author code-generator
    * @function 删除 <@global.obj/>
    * @param id 主键id
    * @return
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class, Exception.class})
    public int remove(int id) {
        return id > 0 ? <@global.obj/>Dao.deleteById(id) : 0;
    }
}