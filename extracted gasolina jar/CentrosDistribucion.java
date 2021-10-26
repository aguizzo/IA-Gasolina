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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CentrosDistribucion
/*    */   extends ArrayList<Distribucion>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Random myRandom;
/*    */   
/*    */   public CentrosDistribucion(int ncen, int mult, int seed) {
/* 36 */     this.myRandom = new Random(seed);
/*    */     
/* 38 */     for (int j = 0; j < ncen; j++) {
/* 39 */       Distribucion c = new Distribucion(this.myRandom.nextInt(100), this.myRandom.nextInt(100));
/* 40 */       for (int i = 0; i < mult; i++)
/* 41 */         add(c); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\lator\Documents\GitHub\IA-Gasolina\extracted gasolina jar\!\IA\Gasolina\CentrosDistribucion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */