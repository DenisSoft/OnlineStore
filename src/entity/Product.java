package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzianis on 28.03.2017.
 */
public class Product {
    private long id;
    private long categoryId;
    private String productName;
    private String description;
    private double price;
    private int residue;
    private Map errors;

    public Product() {
        this.categoryId = 0;
        this.productName = "";
        this.description = "";
        this.price = 0;
        this.residue = 0;
        this.errors = new HashMap();
    }

    public Product(long id) {
        this.id = id;
    }

    public Product(long id, String productName) {
        this.id = id;
        this.productName = productName;
    }

    public Product(long categoryId, String productName, String description, double price, int residue) {
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.residue = residue;
    }

    public Product(long id, long categoryId, String productName, String description, double price, int residue) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.residue = residue;
    }

    public boolean validate() {
        boolean allOk = true;
        if (categoryId == 0) {
            errors.put("categoryId", "Пожалуйста, выберите категорию!");
            categoryId = 0;
            allOk = false;
        }
        if (productName.equals("")) {
            errors.put("productName", "Пожалуйста, введите название!");
            productName = "";
            allOk = false;
        }
        if (description.equals("")) {
            errors.put("description", "Пожалуйста, добавьте описание!");
            description = "";
            allOk = false;
        }
        if (price == 0) {
            errors.put("price", "Пожалуйста, введите цену!");
            price = 0;
            allOk = false;
        }
        if (residue == 0) {
            errors.put("residue", "Пожалуйста, введите количество!");
            residue = 0;
            allOk = false;
        }
        return allOk;
    }

    public String getErrorMsg(String s) {
        String errorMsg = (String) errors.get(s.trim());
        return (errorMsg == null) ? "" : errorMsg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getResidue() {
        return residue;
    }

    public void setResidue(int residue) {
        this.residue = residue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id == product.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", residue=" + residue +
                '}';
    }
}