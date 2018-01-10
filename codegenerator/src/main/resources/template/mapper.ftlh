<#import "global.ftlh" as global/>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
    @author shengwen.zhang
    @createDate ${.now}
 -->
<mapper namespace="com.hexin.<@global.obj/>.repository.<@global.class/>Dao">
    <sql id="base_columns">
    <#list fields as item>${item.column}<#if item_has_next>,</#if></#list>
    </sql>
    <sql id="if">
    <#list fields as item>
        <if test="${item.javaField} != null and ${item.javaField} != ''">
            AND ${item.column} = ${r'#{'}${item.javaField}${r'}'}
        </if>
    </#list>
    </sql>
    <sql id="set">
    <#list fields as item>
    <if test="${item.javaField} != null and ${item.javaField} != ''">
    ${item.column} = ${r'#{'}${item.javaField}${r'}'}<#if item_has_next>,</#if>
    </if>
    </#list>
    </sql>

    <resultMap id="<@global.class/>Map" type="<@global.class/>">
    <#list fields as item>
        <result property="${item.javaField}" column="${item.column}"/>
    </#list>
    </resultMap>
    <select id="findBy" parameterType="map" resultMap="<@global.class/>Map">
        SELECT <include refid="base_columns" /> FROM ${tableName}
        <where>
            <include refid="if"/>
        </where>
        ORDER BY id DESC
    </select>
    <select id="findOneById" parameterType="int" resultMap="<@global.class/>Map">
        SELECT <include refid="base_columns" /> FROM ${tableName} WHERE id=${r'#{'}id${r'}'}
    </select>
    <select id="findByMap" parameterType="map" resultMap="<@global.class/>Map">
        SELECT <include refid="base_columns" /> FROM ${tableName}
        <where>
            <include refid="if"/>
        </where>
    </select>
    <insert id="insert" parameterType="<@global.class/>">
        INSERT ${tableName} (<#list fields as item>${item.column}<#if item_has_next>,</#if></#list>)
        VALUES (<#list fields as item>${r'#{'}${item.javaField}${r'}'}<#if item_has_next>,</#if></#list>)
    </insert>
    <update id="update" parameterType="<@global.class/>">
        UPDATE ${tableName}
        <set>
            update_time=now(),
            <include refid="set"/>
        </set>
        <where>
            <include refid="if"/>
        </where>
    </update>
    <delete id="delete" parameterType="<@global.class/>">
        DELETE FROM ${tableName}
        <where>
            <include refid="if"/>
        </where>
    </delete>
    <delete id="deleteById" parameterType="int">
        DELETE FROM ${tableName} WHERE id=${r'#{'}id${r'}'}
    </delete>
</mapper>