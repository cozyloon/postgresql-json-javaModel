package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DBDetails {
    private String name;
    private String address;
    private int age;

    @Override
    public String toString() {
        return "DBDetails{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}


