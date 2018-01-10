<#assign createdTime = .now>
<#macro obj>${beanName}</#macro>
<#macro class>${BeanName}</#macro>
<#macro pageable>
import com.hexin.utils.mybatis.paginator.Pageable;
</#macro>
<#macro baseDao>import com.hexin.common.repository.MyBatisDao;</#macro>
<#macro fileComment>

/**
* @author code-generator
* @createDate ${createdTime}
* @description
*/

</#macro>