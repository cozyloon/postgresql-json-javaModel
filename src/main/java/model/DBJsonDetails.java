package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DBJsonDetails {
    private String customer;
    private Items items;

    @Getter
    @Setter
    public static class Items {
        private String product;
        private int qty;

        @Override
        public String toString() {
            return "Items{" +
                    "product='" + product + '\'' +
                    ", qty=" + qty +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DBJsonDetails{" +
                "customer='" + customer + '\'' +
                ", items=" + items +
                '}';
    }
}
