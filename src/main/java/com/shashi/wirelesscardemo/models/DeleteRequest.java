package com.shashi.wirelesscardemo.models;

import java.util.List;
import java.util.Map;

public class DeleteRequest {

Map<String,List<String> > records;

    public Map<String, List<String>> getRecords() {
        return records;
    }

    public void setRecords(Map<String, List<String>> records) {
        this.records = records;
    }
}
