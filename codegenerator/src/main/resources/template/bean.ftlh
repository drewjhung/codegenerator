<#import "global.ftlh" as global>
package com.hexin.<@global.obj/>.domain;

import com.hexin.listingModule.annotation.FieldEntry;
import com.hexin.listingModule.annotation.View;
import java.math.BigDecimal;
import java.util.Date;
<@global.fileComment/>
@View(value = "${listName}", whereIsJsp = "jsp/<@global.obj/>/<@global.obj/>List")
public class <@global.class/> {
<#if fields??>
    <#list fields as item>
    @FieldEntry(name="${(item.comment)!item.column}", index = ${item_index}) private ${(item.javaType)!'your_type'} ${(item.javaField)!'your_field'};
    </#list>

    // getter and setter...

    <#list fields as item>
    public ${(item.javaType)!'your_type'} get${item.javaField?cap_first}() {
        return ${(item.javaField)!'your_field'};
    }

    public void set${item.javaField?cap_first}(${(item.javaType)!'your_type'} ${(item.javaField)!'your_field'}) {
        this.${(item.javaField)!'your_field'} = ${(item.javaField)!'your_field'};
    }
    </#list>
</#if>
}