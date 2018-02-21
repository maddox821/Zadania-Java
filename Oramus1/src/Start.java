class Start{
    public static final double A0 = -3.5;
    public static final double B0 = -7.5;
    public static final double N = 8;
    public static final double R_A = -0.75;
    public static final double R_B = 1.24975;

    public double counter(double A0, double B0, double N, double R_A, double R_B){
        int licznik;
        for(int i =0;i<=N; i++){
            licznik = i;
            if(A0 > B0 && Math.abs(A0 - B0) > 0.001){System.out.println("A["+licznik+"] =" +A0+ " B["+licznik+"] =" +B0+" A["+licznik+"] >B["+licznik+"]");}
            else if(A0 < B0 && Math.abs(A0 - B0) > 0.001){System.out.println("A["+licznik+"] =" +A0+ " B["+licznik+"] = "+B0+" A["+licznik+"] < B["+licznik+"]");}
            else if(Math.abs(A0 - B0) <= 0.001){System.out.println("A["+licznik+"] =" +A0+ " B["+licznik+"] = "+B0+" A["+licznik+"] = B["+licznik+"]");}
            A0 += R_A;
            B0 += R_B;
        }
        return 0;
    }
    public static void main(String[] args){
        Start s = new Start();
        s.counter(A0,B0,N,R_A,R_B);
    }
}
