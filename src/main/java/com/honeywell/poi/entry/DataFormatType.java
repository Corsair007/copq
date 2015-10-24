package com.honeywell.poi.entry;

public enum DataFormatType {
    /**
     * 精确到小数点后两位
     */
    Two_Decimal("#,##0.00"),

    /**
     * 精确到小数点后三位
     */
    Three_Decimal("#,##0.000"),

    /**
     * 百分比 精确到小数点后两位‰
     */
    Percentage_Two_Decimal("#,##0.00%"),


    /**
     * 千分比 精确到小数点后两位
     */
    Thousandth_Three_Decimal("#,##0.000‰"),

    /**
     *  数字每三位用,分隔
     */
    Three_separate("#,##0");

    private String dataFormat;

    DataFormatType(String dataFormat){
        this.dataFormat = dataFormat;
    }

    public String getDataFormat() {
        return dataFormat;
    }
}
