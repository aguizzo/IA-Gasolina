/*    */ //package IA.Gasolina;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Gasolineras
/*    */   extends ArrayList<Gasolinera>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Random myRandom;
/* 18 */   private static final double[] DPet = new double[] { 0.05D, 0.65D, 0.25D, 0.05D };
/* 19 */   private static final double[] DDias = new double[] { 0.6D, 0.2D, 0.15D, 0.05D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Gasolineras(int ngas, int seed) {
/* 33 */     this.myRandom = new Random(seed);
/*    */     
/* 35 */     for (int j = 0; j < ngas; j++) {
/* 36 */       Gasolinera c = new Gasolinera(this.myRandom.nextInt(100), this.myRandom.nextInt(100), generaPeticiones());
/* 37 */       add(c);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ArrayList generaPeticiones() {
/*    */     int npet;
/* 49 */     ArrayList<Integer> pet = new ArrayList<>();
/* 50 */     double dice = this.myRandom.nextDouble();
/*    */ 
/*    */     
/* 53 */     if (dice < DPet[0]) {
/* 54 */       npet = 0;
/* 55 */     } else if (dice < DPet[0] + DPet[1]) {
/* 56 */       npet = 1;
/*    */     } else {
/* 58 */       npet = 2;
/*    */     } 
/*    */     
/* 61 */     for (int i = 0; i < npet; i++) {
/* 62 */       int ndias; dice = this.myRandom.nextDouble();
/* 63 */       if (dice < DDias[0]) {
/* 64 */         ndias = 0;
/* 65 */       } else if (dice < DDias[0] + DDias[1]) {
/* 66 */         ndias = 1;
/* 67 */       } else if (dice < DDias[0] + DDias[1] + DDias[2]) {
/* 68 */         ndias = 2;
/*    */       } else {
/* 70 */         ndias = 3;
/*    */       } 
/* 72 */       pet.add(new Integer(ndias));
/*    */     } 
/* 74 */     return pet;
/*    */   }
/*    */ }


/* Location:              C:\Users\lator\Documents\GitHub\IA-Gasolina\extracted gasolina jar\!\IA\Gasolina\Gasolineras.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */