package com.example.yibanke.api.db.pojo;

import java.math.BigDecimal;

public class TbAmectType {
    private Integer id;

    private String type;

    private BigDecimal money;

    private Boolean systemic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getSystemic() {
        return systemic;
    }

    public void setSystemic(Boolean systemic) {
        this.systemic = systemic;
    }
}