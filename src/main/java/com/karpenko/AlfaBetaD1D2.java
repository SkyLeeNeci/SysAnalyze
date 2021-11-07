package com.karpenko;

import java.util.List;

public class AlfaBetaD1D2 {
    private List<double[]> AlfaBeta;
    private List<double[]> D1D2;

    public AlfaBetaD1D2(List<double[]> alfaBeta, List<double[]> d1D2) {
        AlfaBeta = alfaBeta;
        D1D2 = d1D2;
    }

    public List<double[]> getAlfaBeta() {
        return AlfaBeta;
    }

    public List<double[]> getD1D2() {
        return D1D2;
    }


}
