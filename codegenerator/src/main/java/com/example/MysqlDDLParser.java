package com.example;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shengwen.zhang on 2017-06-19 18:05:57.
 */
public class MysqlDDLParser {

    private static final Logger logger = LoggerFactory.getLogger(MysqlDDLParser.class);
    private static final Pattern COLUMN_PATTERN; // 捕获列相关信息的pattern
    private static final Pattern TABLE_NAME_PATTERN; // 捕获英文表示的表明
    private static final Pattern LIST_NAME_PATTERN; // 捕获表的中文名称
    static {
        COLUMN_PATTERN=Pattern.compile("`(\\w+)`\\s+(?:(\\w+)\\(?(\\d+)?,?(?:\\d+)?\\)?)(?:\\s+\\w+)+(?:\\s+'(\\W+)')?");
        LIST_NAME_PATTERN = Pattern.compile("ENGINE=InnoDB\\s+(?:\\w+=\\d+\\s+)?DEFAULT\\s+CHARSET=utf8\\s+COMMENT='([\\W\\w]+)'");
        TABLE_NAME_PATTERN = Pattern.compile("CREATE\\s+TABLE\\s+`(\\w+)`");
    }
    /**
     * @function ddl 转成 bean
     * @param ddl mysql的ddl
     * @return map 为freemarker的 bean.ftlh提供动态数据源， 最终得到一个Bean.java文件
     */
    public static Map ddl2Bean(String ddl){
        List<DatasourceFromDDL> datasource = parseDDL(ddl);
        String tableName = getTableName(ddl);
        String beanName = parseBeanName(tableName);
        String listName = parseListName(ddl);
        return columns2BeanUtil(beanName, listName, tableName, datasource);
    }
    protected final static Map columns2BeanUtil(String beanName, String listName, String tableName, List<DatasourceFromDDL> datasource){
        if(datasource == null || datasource.isEmpty()) {
            logger.info("Illegal argument: argument is null or empty!");
            return null;
        }
        Map root = Maps.newHashMap();
        root.put("tableName", tableName);
        root.put("BeanName", beanName);
        root.put("beanName", humpWholeWord(beanName));
        root.put("listName", listName);
        /*for(int i=0;i<datasource.size();i++){
            DatasourceFromDDL datasourceFromDDL = datasource.get(i);
            logger.info("private {} {};", datasourceFromDDL.getJavaType(), datasourceFromDDL.getJavaField());
        }*/
        root.put("fields", datasource);
        root.put("company", "com.hexin");
        return root;
    }
    protected final static List<DatasourceFromDDL> parseDDL(String ddl){
        if(StringUtils.isBlank(ddl)) return null;
        Matcher matcher = COLUMN_PATTERN.matcher(ddl.trim());
        List<DatasourceFromDDL> datasource = Lists.newArrayList();
        while(matcher.find()){
            DatasourceFromDDL datasourceFromDDL = new DatasourceFromDDL(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
            datasource.add(datasourceFromDDL);
        }
        return datasource;
    }
    protected final static String parseListName(String ddl){
        if(StringUtils.isBlank(ddl)) return null;
        Matcher matcher = LIST_NAME_PATTERN.matcher(ddl.trim());
        String listName = null;
        while(matcher.find()){
            listName = matcher.group(1);
            break; // 如果解析出多个列表名称， 只取第一个名称
        }
        logger.info("list name: {}", listName);
        return listName;
    }

    /**
     * @function 得到表名
     * @param ddl
     * @return
     */
    protected final static String getTableName(String ddl){
        if(StringUtils.isBlank(ddl)) return null;
        Matcher matcher = TABLE_NAME_PATTERN.matcher(ddl.trim());
        String beanName = null;
        while(matcher.find()){
            beanName = matcher.group(1);
            break; // 如果解析出多个bean name， 只取第一个
        }
        logger.info("table name: {}", beanName);
        return beanName;
    }
    protected final static String parseBeanName(String tableName){
        String beanName = upperWholeWord(tableName);
        logger.info("bean name: {}", beanName);
        return beanName;
    }
    /*protected final static String parseBeanName(String ddl){
        String tableName = getTableName(ddl);
        String beanName = upperWholeWord(tableName);
        logger.info("bean name: {}", beanName);
        return beanName;
    }*/

    /**
     * 首写字母 可大写 可小写，由reverse参数控制
     * @param source 含有下划线 或者 不含下划线的  有规律的字符串
     * @param reverse upper capital if reverse is false, or lower capital
     * @return
     */
    public final static String lower2Upper(String source, boolean reverse){
        char[] chars = source.toCharArray();
        if(!reverse && chars[0] >= 'a' && chars[0] <= 'z'){
            chars[0] = (char)(chars[0]-32);
        }
        if(reverse && chars[0] >= 'A' && chars[0] <= 'Z'){
            chars[0] = (char)(chars[0]+32);
        }
        return new String(chars);
    }
    // 首写字母大写
    protected final static String upperWholeWord(String str){
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        if(strs.length > 0) {
            for(int i=0;i<strs.length;i++){
                sb.append(lower2Upper(strs[i], false));
            }
        }
        return sb.toString();
    }
    // 首写字母小写，即驼峰式写法。
    protected final static String humpWholeWord(String str){
        String[] strs = str.split("_");
        StringBuilder sb = new StringBuilder();
        if(strs.length > 0) {
            sb.append(lower2Upper(strs[0], true));
            for(int i=1;i<strs.length;i++){
                sb.append(lower2Upper(strs[i], false));
            }
        }
        return sb.toString();
    }
    public static class DatasourceFromDDL{
        public DatasourceFromDDL(String column, String jdbcType, String length, String comment) {
            this.column = column;
            this.jdbcType = jdbcType;
            this.length = length;
            this.comment = comment;
            this.javaField = humpWholeWord(column);
            this.javaType = TypeEnum.getTypeByJdbcType(jdbcType, this.length);
        }

        public String getColumn() {
            return column;
        }

        public String getJdbcType() {
            return jdbcType;
        }

        public String getLength() {
            return length;
        }

        public String getComment() {
            return comment;
        }

        public String getJavaType() {
            return javaType;
        }

        public String getJavaField() {
            return javaField;
        }

        @Override
        public String toString() {
            return "DatasourceFromDDL{" +
                    "javaType='" + javaType + '\'' +
                    ", javaField='" + javaField + '\'' +
                    '}';
        }

        private String javaType;
        private String javaField;
        private String column; // 列名
        private String jdbcType; // 列的类型
        private String length; // 列长度
        private String comment; // 列注释
    }
    private enum TypeEnum {
        D("BigDecimal", "*", "decimal"),
        B("int", "+", "int", "tinyint"),
        E("boolean", "1", "tinyint"),
        A("String", "*", "varchar"),
        F("long", "*", "bigint"),
        C("Date", "?", "datetime");
        private String[] jdbcType; // 数据库数据类型
        private String type; // Java类型
        private String length; // 数据库类型长度
        TypeEnum(String type, String length, String... jdbcType){
            this.jdbcType = jdbcType;
            this.type = type;
            this.length = length;
        }
        public String getLength(){return this.length;}
        public String[] getJdbcType(){
            return this.jdbcType;
        }
        public String getType(){
            return this.type;
        }
        public static String getTypeByJdbcType(String jdbcType, String jdbcLength){
            if (StringUtils.isBlank(jdbcType)) {
                logger.info("参数jdbcType为空");
                return null;
            }
            if("1".equals(jdbcLength) && "tinyint".equals(jdbcType)) return TypeEnum.E.getType();
            TypeEnum[] typeEnums = TypeEnum.values();
            for(int i=0;i<typeEnums.length;i++){
                String[] jdbctypes = typeEnums[i].getJdbcType();
                for(int j=0;j<jdbctypes.length;j++){
                    if(jdbctypes[j].equals(jdbcType)) return typeEnums[i].getType();
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        String ddl = "CREATE TABLE `car_insurance_gps` (\n" +
                "  `id` int(11) unsigned NOT NULL COMMENT '主键',\n" +
                "  `gps_productor` varchar(30) DEFAULT NULL COMMENT 'gps厂商',\n" +
                "  `gps_type` tinyint(3) DEFAULT NULL COMMENT 'gps类型',\n" +
                "  `device_no` varchar(50) DEFAULT NULL COMMENT '设备号',\n" +
                "  `status` tinyint(1) DEFAULT NULL COMMENT '状态',\n" +
                "  `create_time` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
                "  `update_time` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆按照GPS信息';\n" +
                "\n";
        List<DatasourceFromDDL> fields = parseDDL(ddl);
        logger.info(fields.toString());
    }
}
