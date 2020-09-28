package ${pPackage}.vo.page;

import lombok.Data;

/**
 * @desc 分页VO
 * @author ${author}
 * @date ${dateTime}
 */
@Data
public class Paging {
    /**
     * 页码
     */
    private int pageNum = 1;
    /**
     * 每页大小
     */
    private int pageSize = 20;

    <#list table.columns as column>
    <#if column.columnKey??>
    /**
     * 排序字段
     */
    private String condition = "${column.columnName}";
    </#if>
    </#list>

    /**
     * 排序规则
     */
    private String sort = "desc";
}

