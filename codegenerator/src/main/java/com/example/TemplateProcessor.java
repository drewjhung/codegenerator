package com.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by shengwen.zhang on 2017/6/18 15:50.
 */
public class TemplateProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TemplateProcessor.class);
    private static Configuration configuration;
    private static final List<Writer> writers = new ArrayList<>();
    private static final Properties properties = new Properties();
    static{
        // application-level singleton Object Config
        try {
            configuration = new Configuration();
            configuration.setClassForTemplateLoading(TemplateProcessor.class, "/template/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        loadProperties(); // 加载配置文件
    }
    private static void loadProperties(){
        try {
            Reader reader = new FileReader(new File("E:\\workspace_intellij_2016\\codegenerator\\src\\main\\resources", "generatorConfig.properties"));
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 读取配置文件中的开关
    private static boolean getSwitch(String key){
        if(StringUtils.isBlank(key)) return false;
        String value = properties.getProperty(key, "false");
        return Boolean.valueOf(value);
    }
    // 读取配置文件中的生成文件路径
    private static String getFilePath(String key){
        if(StringUtils.isBlank(key)) return "";
        return properties.getProperty(key, "");
    }
    public static void main(String[] args) throws IOException, TemplateException {
        String ddl = "CREATE TABLE `order_info` (\n" +
                "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `order_customer_info_id` int(11) DEFAULT '0' COMMENT '订单客户详细信息ID',\n" +
                "  `clue_id` int(11) DEFAULT '0' COMMENT '客户线索id',\n" +
                "  `order_number` varchar(32) DEFAULT NULL COMMENT '订单编号',\n" +
                "  `purchase_type` tinyint(2) DEFAULT '0' COMMENT '采购类型(1：全国，2：地方)',\n" +
                "  `car_brand_id` int(11) DEFAULT '0' COMMENT '购车品牌id',\n" +
                "  `car_manufacturer` varchar(64) DEFAULT NULL COMMENT '厂商',\n" +
                "  `belong_to_store` varchar(32) DEFAULT NULL COMMENT '所属门店code',\n" +
                "  `belong_channelbus_id` int(11) DEFAULT NULL COMMENT '所属渠道商id',\n" +
                "  `channel_name` varchar(64) DEFAULT NULL COMMENT '渠道商name',\n" +
                "  `car_model_id` int(11) DEFAULT '0' COMMENT '车型id',\n" +
                "  `car_category_id` int(11) DEFAULT '0' COMMENT '车款id',\n" +
                "  `finance_id` int(11) DEFAULT '0' COMMENT '金融方案id',\n" +
                "  `customer_manager_id` int(11) DEFAULT '0' COMMENT '客户经理ID',\n" +
                "  `customer_manager_name` varchar(32) DEFAULT NULL COMMENT '客户经理姓名',\n" +
                "  `order_contract_number` varchar(32) DEFAULT NULL COMMENT '订单合同编号',\n" +
                "  `state` tinyint(4) DEFAULT '1' COMMENT '状态（1：草稿，2：待总部初审审核，3：待总部终审审核，4：审批拒绝，5：待资料上传，6：待运营审核，7：待财务审核，8：待车务中心审核，9：待确认提车，10，提车完成，11：订单撤销，12：订单结清,13:反欺诈模型 ,14:待首期款确认，15:资金对接中',\n" +
                "  `over_due` tinyint(2) DEFAULT '2' COMMENT '是否逾期过(1：是，2：否)',\n" +
                "  `first_payment_time` datetime DEFAULT NULL COMMENT '首付款到账时间',\n" +
                "  `first_payment_affirm_time` datetime DEFAULT NULL COMMENT '首期款到账确认操作时间',\n" +
                "  `first_payment_money` decimal(18,2) DEFAULT '0.00' COMMENT '首付款金额',\n" +
                "  `deposit_money` decimal(18,2) DEFAULT '0.00' COMMENT '保证金金额',\n" +
                "  `plate_numbers` varchar(32) DEFAULT NULL COMMENT '车牌号',\n" +
                "  `vin` varchar(64) DEFAULT NULL COMMENT '车架号',\n" +
                "  `gps_numbers` varchar(256) DEFAULT NULL COMMENT 'gps编号',\n" +
                "  `belong_to_store_name` varchar(64) DEFAULT NULL COMMENT '所属门店name',\n" +
                "  `payment_confirmation_person_id` int(11) DEFAULT NULL COMMENT '首付确认操作人ID',\n" +
                "  `payment_confirmation_person` varchar(64) DEFAULT NULL COMMENT '首付确认操作人',\n" +
                "  `vehicle_auditing_person_id` int(11) DEFAULT NULL COMMENT '车务审核操作人ID',\n" +
                "  `vehicle_auditing_person` varchar(64) DEFAULT NULL COMMENT '车务审核操作人',\n" +
                "  `push_time` datetime DEFAULT NULL COMMENT '推送时间',\n" +
                "  `creditor_right_status` tinyint(3) DEFAULT NULL COMMENT '债权状态',\n" +
                "  `release_loan_time` datetime DEFAULT NULL COMMENT '放款时间',\n" +
                "  `contract_amount` decimal(18,2) DEFAULT NULL COMMENT '合同金额',\n" +
                "  `total_service_fee_amount` decimal(18,2) DEFAULT NULL COMMENT '服务费总额',\n" +
                "  `service_fee_amount` decimal(18,2) DEFAULT NULL COMMENT '服务费',\n" +
                "  `consultation_fee_amount` decimal(18,2) DEFAULT NULL COMMENT '咨询费',\n" +
                "  `month_rate_of_hexindai` decimal(18,2) DEFAULT NULL COMMENT '线上（和信贷）月利率',\n" +
                "  `month_rate_of_geton` decimal(18,2) DEFAULT NULL COMMENT '快上车月利率',\n" +
                "  `order_repayment_status` tinyint(3) DEFAULT NULL COMMENT '订单还款状态',\n" +
                "  `order_repayment_periods` int(5) DEFAULT NULL COMMENT '订单还款期数',\n" +
                "  `submit_time` datetime DEFAULT NULL COMMENT '订单提交时间',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_name` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
                "  `update_name` varchar(32) DEFAULT NULL COMMENT '修改人',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `version` bigint(20) DEFAULT '0' COMMENT '版本号',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='订单信息';\n" +
                "\n";
        // create a data-model
        Map root = MysqlDDLParser.ddl2Bean(ddl);
        final String COMMON_ROOT_DIR = getFilePath("common.root.dir");
        // 构建每个module有规则的目录
        String subDir = (String)root.get("beanName");
        File commonBaseDir = new File(COMMON_ROOT_DIR, subDir);
        /**
         * get template and write into .java or .xml file with the specified template.
         */
        try{
            if(getSwitch("bean"))
                bean(commonBaseDir, root);
            if(getSwitch("dao"))
                dao(commonBaseDir, root);
            if(getSwitch("service"))
                service(commonBaseDir, root);
            if(getSwitch("serviceImpl"))
                serviceImpl(commonBaseDir, root);
            if(getSwitch("controller")){
                final String WEB_ROOT_DIR = getFilePath("web.root.dir");
                File webBaseDir = new File(WEB_ROOT_DIR, subDir);
                controller(webBaseDir, root);
            }
            if(getSwitch("mapper")){
                final String MAPPER_ROOT_DIR = getFilePath("mapper.root.dir");
                File mapperBaseDir = new File(MAPPER_ROOT_DIR, subDir);
                mapper(mapperBaseDir, root);
            }
        } catch (Exception e){
            logger.error("生成文件失败", e);
        } finally{
            if(!writers.isEmpty()){
                for(Writer writer : writers){
                    if(writer != null){
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
        // jsp (只新增文件 不写任何内容)
        if(getSwitch("jsp")){
            final String JSP_ROOT_DIR = properties.getProperty("jsp.root.dir");
            File jspBaseDir = new File(JSP_ROOT_DIR, subDir);
            // 列表
            createFile(new File(jspBaseDir, subDir+"List.jsp"));
            // 编辑
            createFile(new File(jspBaseDir, "add"+root.get("BeanName")+".jsp"));
        }
        // 在mybatis alias 添加bean包名
        final String MYBATIS_ALIAS_DIR = properties.getProperty("mybatis.alias.root.dir");
        appendToMybatisAlias(root, MYBATIS_ALIAS_DIR);
//        appendToMybatisAlias(root, TEST_MYBATIS_ALIAS_DIR);
        System.out.println("\nfinished...");
    }
    private static void appendToMybatisAlias(Map root, String dir) {
        File aliasDir = new File(dir);
        if(!aliasDir.exists()) aliasDir.mkdir();
        FileWriter aliasWriter = null;
        try{
            File aliasFile = new File(aliasDir, "mybatisAlias.properties");
            if(!aliasFile.exists()) aliasFile.createNewFile();
            aliasWriter = new FileWriter(aliasFile, true);
            aliasWriter.write(root.get("company")+"."+root.get("beanName")+".domain;\\\n");
        } catch (IOException e){
            logger.error("写入mybatis-alias配置文件错误", e);
        } finally{
            if(aliasWriter != null){
                try {
                    aliasWriter.flush();
                } catch (IOException e) {
                    // 忽略writer刷新缓存到文件过程中出现的异常
                }
            }
            if(aliasWriter != null)
                try {
                    aliasWriter.close();
                } catch (IOException e) {
                    // 忽略关闭writer出现的异常
                }
        }
    }

    /**
     * @description 创建多层级文件
     * @param file
     * @return
     * @throws IOException
     */
    private static boolean createFile(File file) throws IOException {
        boolean successFlag;
        if(!file.exists()){
            File parent = file.getParentFile();
            if(!parent.exists())
                parent.mkdirs();
            successFlag = file.createNewFile();
        } else
            successFlag = true;
        return successFlag;
    }

    /**
     * @description 封装针对不同模板生成模板文件
     * @param targetFile
     * @param root
     * @param template
     * @throws IOException
     * @throws TemplateException
     */
    public static void wiredFile(File targetFile, Map<String, Object> root, Template template)throws IOException, TemplateException {
        if(createFile(targetFile)){
            Writer beanWriter = new FileWriter(targetFile);
            writers.add(beanWriter);
            template.process(root, beanWriter);
        } else
            throw new IOException("创建bean文件失败");
    }

    /**
     * @description 生成bean文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    private static void bean(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template beanTemplate = configuration.getTemplate("bean.ftlh");
        File beanFile = new File(new File(baseDir, "domain"),root.get("BeanName")+".java");
        wiredFile(beanFile, root, beanTemplate);
    }

    /**
     * @description 生成dao文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    private static void dao(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template daoTemplate = configuration.getTemplate("dao.ftlh");
        File daoFile = new File(new File(baseDir, "repository"),root.get("BeanName")+"Dao.java");
        wiredFile(daoFile, root, daoTemplate);
    }

    /**
     * @description 生成service文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    public static void service(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template serviceTemplate = configuration.getTemplate("service.ftlh");
        File serviceFile = new File(new File(baseDir, "service"), root.get("BeanName")+"Service.java");
        wiredFile(serviceFile, root, serviceTemplate);
    }

    /**
     * @description 生成serviceImpl文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    public static void serviceImpl(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template serviceImplTemplate = configuration.getTemplate("serviceImpl.ftlh");
        File serviceImplFile = new File(new File(baseDir, "service\\impl"), root.get("BeanName")+"ServiceImpl.java");
        wiredFile(serviceImplFile, root, serviceImplTemplate);
    }
    /**
     * @description 生成controller文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    public static void controller(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template controllerTemplate = configuration.getTemplate("controller.ftlh");
        File controllerFile = new File(new File(baseDir, "controller"), root.get("BeanName")+"Controller.java");
        wiredFile(controllerFile, root, controllerTemplate);
    }

    /**
     * @description 生成mapper文件
     * @param baseDir
     * @param root
     * @throws IOException
     * @throws TemplateException
     */
    public static void mapper(File baseDir, Map<String, Object> root) throws IOException, TemplateException {
        Template mapperTemplate = configuration.getTemplate("mapper.ftlh", "utf-8");
        File mapperFile = new File(baseDir, root.get("beanName")+"Mapper.xml");
        wiredFile(mapperFile, root, mapperTemplate);
    }
}
