package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "gyhtest-mobilehub-903612549-FitnessLevels")

public class FitnessLevelsDO {
    private String _userId;
    private Double _mile;
    private Double _pushup;
    private Double _situps;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "mile")
    public Double getMile() {
        return _mile;
    }

    public void setMile(final Double _mile) {
        this._mile = _mile;
    }
    @DynamoDBAttribute(attributeName = "pushup")
    public Double getPushup() {
        return _pushup;
    }

    public void setPushup(final Double _pushup) {
        this._pushup = _pushup;
    }
    @DynamoDBAttribute(attributeName = "situps")
    public Double getSitups() {
        return _situps;
    }

    public void setSitups(final Double _situps) {
        this._situps = _situps;
    }

}
