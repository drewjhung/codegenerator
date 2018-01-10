package com.example.test;

import com.example.MysqlDDLParser;
import com.example.TemplateProcessor;
import com.example.domain.Product;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by drew.jhung on 2017/6/17 16:23.
 */
public class TemplateWithFreemarkerTest {

    private static final Logger logger = LoggerFactory.getLogger(TemplateWithFreemarkerTest.class);

    private static Configuration configuration;
    static{
        // aplication-level singleton Object Config
        try {
            configuration = new Configuration();
           configuration.setClassForTemplateLoading(TemplateWithFreemarkerTest.class, "/template/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Test
    public void templateTest() throws Exception {
        // create a data-model
        Map root = Maps.newHashMap();
        root.put("user", "Big Joe");
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        root.put("latestProduct", latest);
        root.put("beanName", "carInventory");
        root.put("BeanName", "CarInventory");
        // get template
        Template template = configuration.getTemplate("test.ftlh");
        Writer writer = new OutputStreamWriter(System.out);
        template.process(root, writer);
//        template.process(latest, writer);
//        AccountChangeHistory
    }
    @Test public void ddl2BeanTest(){
        String ddl = "CREATE TABLE `car_inventory` (\n" +
                "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `package_number_id` int(11) DEFAULT NULL COMMENT '进件号id',\n" +
                "  `car_no` varchar(22) NOT NULL COMMENT '资产号',\n" +
                "  `car_status` tinyint(4) NOT NULL COMMENT '车辆状态',\n" +
                "  `car_license_no` varchar(25) DEFAULT NULL COMMENT '车牌号',\n" +
                "  `car_brand` varchar(30) DEFAULT NULL COMMENT '车品牌',\n" +
                "  `car_vin_no` varchar(255) DEFAULT NULL COMMENT '车架号',\n" +
                "  `car_color` varchar(10) DEFAULT NULL COMMENT '车颜色',\n" +
                "  `car_model` varchar(30) DEFAULT NULL COMMENT '车型',\n" +
                "  `car_qulity` varchar(100) DEFAULT NULL COMMENT '车配置',\n" +
                "  `car_engine_no` varchar(20) DEFAULT NULL COMMENT '发动机号',\n" +
                "  `car_certification_no` varchar(50) DEFAULT NULL COMMENT '合格证编号',\n" +
                "  `car_km_num` decimal(10,2) DEFAULT NULL COMMENT '公里数',\n" +
                "  `car_license_type` tinyint(1) DEFAULT NULL COMMENT '牌照类型',\n" +
                "  `car_new_or_old` varchar(30) DEFAULT NULL COMMENT '新旧属性',\n" +
                "  `warehouse` varchar(50) DEFAULT NULL COMMENT '仓库',\n" +
                "  `store_no` varchar(50) DEFAULT NULL COMMENT '门店号',\n" +
                "  `store_name` varchar(30) DEFAULT NULL COMMENT '门店名称',\n" +
                "  `store_province_id` varchar(20) DEFAULT NULL COMMENT '省份no',\n" +
                "  `store_province_name` varchar(10) DEFAULT NULL COMMENT '省份名称',\n" +
                "  `shopping_type` tinyint(4) DEFAULT NULL COMMENT '采购类型',\n" +
                "  `car_advice_price` decimal(18,2) DEFAULT NULL COMMENT '指导价',\n" +
                "  `car_estimate_price` decimal(18,2) DEFAULT NULL COMMENT '预估保费',\n" +
                "  `shopping_tax` decimal(18,2) DEFAULT NULL COMMENT '购置税',\n" +
                "  `shopping_price` decimal(18,2) DEFAULT NULL COMMENT '采购价',\n" +
                "  `buyer` varchar(30) DEFAULT NULL COMMENT '采购商',\n" +
                "  `customer_name` varchar(20) NOT NULL COMMENT '客户名称',\n" +
                "  `check_car_time` datetime DEFAULT NULL COMMENT '验车时间',\n" +
                "  `auditting_time` datetime DEFAULT NULL COMMENT '审核时间',\n" +
                "  `accomplish_time` datetime DEFAULT NULL COMMENT '交车时间',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `idx_pack_id` (`package_number_id`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='车辆信息';\n" +
                "\n";
        Map map = MysqlDDLParser.ddl2Bean(ddl);
        List<MysqlDDLParser.DatasourceFromDDL> datasource = (List<MysqlDDLParser.DatasourceFromDDL>)map.get("fields");
        for(MysqlDDLParser.DatasourceFromDDL data : datasource){
            logger.info(data.toString());
        }

    }
    @Test public void parseListNameTest(){
        String ddl = "CREATE TABLE `car_insurance` (\n" +
                "  `id` int(11) unsigned NOT NULL,\n" +
                "  `car_inventory_id` int(11) NOT NULL COMMENT '车辆库存id',\n" +
                "  `insurance_type` tinyint(3) DEFAULT NULL COMMENT '险种',\n" +
                "  `insurance_bill_no` varchar(30) DEFAULT NULL COMMENT '保单号',\n" +
                "  `insurance_amount` decimal(18,2) DEFAULT NULL COMMENT '保险实际金额',\n" +
                "  `effective_start_time` datetime DEFAULT NULL COMMENT '保险生效开始时间',\n" +
                "  `effective_due_time` datetime DEFAULT NULL COMMENT '保险生效结束时间',\n" +
                "  `guarantee_entity` varchar(30) DEFAULT NULL COMMENT '承保机构',\n" +
                "  `status` tinyint(1) DEFAULT NULL COMMENT '状态',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_car_inventory_id` (`car_inventory_id`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆保险信息';\n" +
                "\n";
        Pattern LIST_NAME_PATTERN = Pattern.compile("ENGINE=InnoDB\\s+(?:\\w+=\\d+\\s+)?DEFAULT\\s+CHARSET=utf8\\s+COMMENT='([\\W\\w]+)'");
        Matcher matcher = LIST_NAME_PATTERN.matcher(ddl.trim());
        String listName = null;
        while(matcher.find()){
            listName = matcher.group(1);
            break; // 如果解析出多个列表名称， 只取第一个名称
        }
        logger.info("list name: {}", listName);
    }
}
