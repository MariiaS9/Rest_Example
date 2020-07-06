package org.my.rest.models;

import java.util.Objects;

public class News {
    Long id;
    String name;
    String topic;

    public News(Long id, String name, String topic) {
        this.id = id;
        this.name = name;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News newspaper = (News) o;
        return Objects.equals(id, newspaper.id) &&
                Objects.equals(name, newspaper.name) &&
                Objects.equals(topic, newspaper.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, topic);
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
