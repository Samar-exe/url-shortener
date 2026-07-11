package com.samar.urlshortener.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "counters")
public class UrlCounter {
    @Id private String id;
    private long sequenceValue;
    public UrlCounter() {}
    public UrlCounter(String id, long sequenceValue) { this.id = id; this.sequenceValue = sequenceValue; }
    public String getId() { return id; } public void setId(String id) { this.id = id; }
    public long getSequenceValue() { return sequenceValue; } public void setSequenceValue(long v) { this.sequenceValue = v; }
}
