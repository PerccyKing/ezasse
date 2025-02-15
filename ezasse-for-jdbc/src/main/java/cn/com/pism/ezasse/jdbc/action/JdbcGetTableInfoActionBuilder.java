package cn.com.pism.ezasse.jdbc.action;

import cn.com.pism.ezasse.exception.EzasseException;
import cn.com.pism.ezasse.jdbc.action.param.GetTableInfoActionParam;
import cn.com.pism.ezasse.model.EzasseDataSource;
import cn.com.pism.ezasse.model.EzasseExecutorAction;
import cn.com.pism.ezasse.model.EzasseTableInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.com.pism.ezasse.constants.EzasseConstants.*;
import static cn.com.pism.ezasse.constants.EzasseExecutorActionConstants.GET_TABLE_INFO;
import static cn.com.pism.ezasse.util.EzasseUtil.toTableInfo;

/**
 * @author PerccyKing
 * @since 25-01-31 14:57
 */
public class JdbcGetTableInfoActionBuilder implements EzasseExecutorAction<GetTableInfoActionParam, List<EzasseTableInfo>> {

    private final Map<String, String> selectMap = new HashMap<>(16);

    private String from;

    private final Map<String, String> where = new HashMap<>(16);

    private final Map<String, Function<WhereCondition, String>> paramWhere = new HashMap<>(16);

    private JdbcGetTableInfoActionBuilder() {
    }

    public static JdbcGetTableInfoActionBuilder builder() {
        return new JdbcGetTableInfoActionBuilder();
    }

    @Override
    @SuppressWarnings("all")
    public List<EzasseTableInfo> doAction(GetTableInfoActionParam actionParam, EzasseDataSource dataSource) {
        SqlArgs sqlArgs = buildQuerySql(actionParam, dataSource);
        return toTableInfo(JdbcTemplateCache.get(dataSource.getId()).queryForList(sqlArgs.getSql(), sqlArgs.getArgs().toArray()));
    }

    private SqlArgs buildQuerySql(GetTableInfoActionParam actionParam, EzasseDataSource dataSource) {
        StringBuilder getTableInfoSql = new StringBuilder("select \n");

        appendSelect(getTableInfoSql);

        getTableInfoSql.append(" from ").append(from).append("\n");

        if (CollectionUtils.isEmpty(paramWhere) && CollectionUtils.isEmpty(where)) {
            return new SqlArgs(getTableInfoSql.toString(), null);
        }

        getTableInfoSql.append("where\n");

        List<String> wheres = new ArrayList<>();

        List<String> args = new ArrayList<>();

        WhereCondition whereCondition = new WhereCondition();
        whereCondition.setDataSource(dataSource);
        whereCondition.setActionParam(actionParam);
        appendParamWhere(whereCondition, args, wheres);

        appendWhere(args, wheres);

        getTableInfoSql.append(String.join("AND ", wheres));

        return new SqlArgs(getTableInfoSql.toString(), args);
    }

    private void appendParamWhere(WhereCondition whereCondition, List<String> args, List<String> wheres) {
        List<String> paramWhereList = paramWhere
                .entrySet()
                .stream()
                .map(entry -> {
                    if (entry.getValue() == null || StringUtils.isBlank(entry.getValue().apply(whereCondition))) {
                        return "";
                    }
                    args.add(entry.getValue().apply(whereCondition));
                    return entry.getKey() + " = ?\n";
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(paramWhereList)) {
            wheres.addAll(paramWhereList);
        }
    }

    private void appendWhere(List<String> args, List<String> wheres) {
        List<String> whereList = where
                .entrySet()
                .stream()
                .map(entry -> {
                    if (StringUtils.isBlank(entry.getValue())) {
                        return "";
                    }
                    args.add(entry.getValue());
                    return entry.getKey() + " = ?\n";
                })
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(wheres)) {
            wheres.addAll(whereList);
        }
    }

    private void appendSelect(StringBuilder getTableInfoSql) {
        getTableInfoSql.append(selectMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " " + entry.getValue() + "\n")
                .collect(Collectors.joining(",")));
    }

    @Override
    public String getId() {
        return GET_TABLE_INFO;
    }

    public JdbcGetTableInfoActionBuilder select(String column, String as) {
        this.selectMap.put(column, as);
        return this;
    }

    public JdbcGetTableInfoActionBuilder selectColumnName(String selectColumnName) {
        this.selectMap.put(selectColumnName, COLUMN_NAME);
        return this;
    }

    public JdbcGetTableInfoActionBuilder selectDataType(String selectDataType) {
        this.selectMap.put(selectDataType, DATA_TYPE);
        return this;
    }

    public JdbcGetTableInfoActionBuilder selectDataLength(String selectDataLength) {
        this.selectMap.put(selectDataLength, DATA_LENGTH);
        return this;
    }

    public JdbcGetTableInfoActionBuilder selectColumnComment(String selectColumnComment) {
        this.selectMap.put(selectColumnComment, COLUMN_COMMENT);
        return this;
    }

    public JdbcGetTableInfoActionBuilder from(String from) {
        this.from = from;
        return this;
    }

    public JdbcGetTableInfoActionBuilder where(String column, String condition) {
        this.where.put(column, condition);
        return this;
    }

    public JdbcGetTableInfoActionBuilder where(String column, Function<WhereCondition, String> condition) {
        this.paramWhere.put(column, condition);
        return this;
    }

    public JdbcGetTableInfoActionBuilder build() {
        if (CollectionUtils.isEmpty(selectMap)) {
            throw new EzasseException("selectMap empty error");
        }
        return this;
    }


    @Data
    @AllArgsConstructor
    private static class SqlArgs {
        private String sql;
        private List<String> args;
    }

    @Data
    public static class WhereCondition {
        private EzasseDataSource dataSource;
        private GetTableInfoActionParam actionParam;
    }
}
