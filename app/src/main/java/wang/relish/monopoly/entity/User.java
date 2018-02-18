package wang.relish.monopoly.entity;

import java.io.Serializable;

/**
 * @author Relish Wang
 * @since 2018/02/19
 */
public class User implements Serializable {

    private long id;
    private String name;
    private double money;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
