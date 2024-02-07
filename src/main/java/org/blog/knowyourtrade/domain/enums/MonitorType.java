package org.blog.knowyourtrade.domain.enums;

import lombok.Getter;

@Getter
public enum MonitorType {
    API("API", "API"),
    DAO("DAO", "DAO"),
    DATABASE("DATABASE","DATABASE"),
    SERVICE("SERVICE", "SERVICE"),
    KSQL("KSQL", "KSQL"),
    ;

    private final String code;
    private final String desc;

    MonitorType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}