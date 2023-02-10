package com.br.var.solutions;

public class InformacoesIR {
    public double baseDeCalculo;
    public String aliquota;
    private static double ISENTO = 0;
    private static double LIQUOTA_75 = 7.5;
    private static double LIQUOTA_15 = 15;
    private static double LIQUOTA_22 = 22.5;
    private static double LIQUOTA_27 = 27.5;

    public double getBaseDeCalculo() {
        return baseDeCalculo;
    }
    public void setBaseDeCalculo(double baseDeCalculo) {
        this.baseDeCalculo = baseDeCalculo;
    }
    public String getAliquota() {
        return aliquota;
    }
    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }
    public static double getISENTO() {
        return ISENTO;
    }
    public static void setISENTO(double ISENTO) {
        InformacoesIR.ISENTO = ISENTO;
    }
    public static double getLiquota75() {
        return LIQUOTA_75;
    }
    public static void setLiquota75(double liquota75) {
        LIQUOTA_75 = liquota75;
    }
    public static double getLiquota15() {
        return LIQUOTA_15;
    }
    public static void setLiquota15(double liquota15) {
        LIQUOTA_15 = liquota15;
    }
    public static double getLiquota22() {
        return LIQUOTA_22;
    }
    public static void setLiquota22(double liquota22) {
        LIQUOTA_22 = liquota22;
    }
    public static double getLiquota27() {
        return LIQUOTA_27;
    }
    public static void setLiquota27(double liquota27) {
        LIQUOTA_27 = liquota27;
    }
}