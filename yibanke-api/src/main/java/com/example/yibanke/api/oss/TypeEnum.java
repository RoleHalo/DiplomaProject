package com.example.yibanke.api.oss;


//枚举封装腾讯云功能
public enum TypeEnum {
    ARCHIVE("archive");


    private String key;

    private TypeEnum(String key) {
        this.key = key;
    }
    private String getKey(){
        return key;
    }
    public static TypeEnum findByKey(String key) {
        if (key != null) {
            for (TypeEnum type : TypeEnum.values()) {
                if (key.equals(type.getKey())) {
                    return type;
                }
            }
        }
        return null;
    }
}

