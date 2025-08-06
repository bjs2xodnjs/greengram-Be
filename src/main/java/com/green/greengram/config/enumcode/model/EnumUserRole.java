package com.green.greengram.config.enumcode.model;

import com.green.greengram.config.enumcode.AbstractEnumCodeConverter;
import com.green.greengram.config.enumcode.EnumMapperType;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumUserRole implements EnumMapperType {
    USER_1("01", "유저1"),
    USER_2("02", "유저2"),
    ADMIN("3","관리자" ),
    MENTO("4", "멘토"),
    MANAGER("5", "매니저")
    ;


    private final String code;
    private final String value;

    @Converter(autoApply = true)
    public static class CodeConverter extends AbstractEnumCodeConverter<EnumUserRole> {
        public CodeConverter() { super(EnumUserRole.class, false); }
        // 만약 어떤데는 null 허용이고 어떤데는 아니다 라고 하면 그냥 거기에서 연결
    }
}
