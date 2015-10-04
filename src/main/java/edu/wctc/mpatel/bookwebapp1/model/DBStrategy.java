package edu.wctc.mpatel.bookwebapp1.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DBStrategy {

    public void closeConnection() throws SQLException;

    public int deleteRecordById(String tableName, String recordField, Object recordValue) throws SQLException;

    public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    public int insertRecord(String tableName, List<String> recordFields, List<Object> recordValues) throws SQLException;

    public void openConnection(String driverClass, String url, String userName, String password) throws SQLException, ClassNotFoundException;

    public int updateRecord(String tableName, String recordWhereField, Object recordWhereValue, List<String> recordFields, List<Object> recordValues) throws SQLException;

    public Map<String, Object> findRecordByPrimaryKey(String tableName, String primaryKeyFieldName, String primaryKeyValue) throws SQLException;
}
