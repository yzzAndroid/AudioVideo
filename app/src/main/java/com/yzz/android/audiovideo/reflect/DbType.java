package com.yzz.android.audiovideo.reflect;

/**
 * Created by yzz
 * 2017/4/25 0025.
 */
public enum DbType {
    INTEGER,
    REAL,
    TEXT,
    BLOB,

    /**
     *  16 位元的整数。
     */
    SMALLINT,

    /**
     *  32 位元的整数。
     */
    INTERGER,
    /**
     * (p,s) p 精确值和 s 大小的十进位整数，精确值p是指全部有几个数(digits)大小值，s是指小数点後有几位数。如果没有特别指定，则系统会设为 p=5; s=0 。
     */
    DECIMAL,

    /**
     *  32位元的实数。
     */
    FLOAT ,
    /**
     *   64位元的实数。
     */
    DOUBLE,
    /**
     * n 长度的字串，n不能超过 254
     */
    CHAR,
    /**
     *  长度不固定且其最大长度为 n 的字串，n不能超过 4000。
     */
    VARCHAR,
    /**
     * 和 char(n) 一样，不过其单位是两个字元 double-bytes， n不能超过127。这个形态是为了支援两个字元长度的字体，例如中文字。
     */
    GRAPHIC,

    /**
     *  可变长度且其最大长度为 n 的双字元字串，n不能超过 2000
     */
    VARGRAPHIC,
    /**
     * 包含了 年份、月份、日期。
     */
    DATE,
    /**
     * 包含了 小时、分钟、秒。
     */
    TIME,

    /**
     *  包含了 年、月、日、时、分、秒、千分之一秒。
     */
    TIMESTAMP;

}
