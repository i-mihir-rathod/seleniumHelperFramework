package dataObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductDetails {
    private String name;
    private String price;
    private String withoutTax;
    private int quantity;
}
