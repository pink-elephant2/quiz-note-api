package com.api.note.quiz.enums;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 画面表示に使用するEnumは当Interfaceを実装する.
 */
public interface BaseEnum {

    /**
     * 日本語表示名称
     */
    public String getNameJa();

    /**
     * 英語表示名称
     */
    public String getNameEn();

    /**
     * 表示名称取得
     */
    default public String getName() {
        if (LocaleContextHolder.getLocale() == Locale.JAPANESE) {
            return this.getNameJa();
        }
        return this.getNameEn();
    }
}
