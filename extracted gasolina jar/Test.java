/*    */ //package IA.Gasolina;
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
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args) {
/* 21 */     Gasolineras s = new Gasolineras(100, 1234);
/* 22 */     CentrosDistribucion c = new CentrosDistribucion(10, 1, 1234);
/* 23 */     double[] histo = { 0.0D, 0.0D, 0.0D, 0.0D };
/*    */     int i;
/* 25 */     for (i = 0; i < s.size(); i++) {
/* 26 */       System.out.println("Gasolinera " + i + ": " + s.get(i).getCoordX() + " " + s.get(i).getCoordY());
/* 27 */       int j = 0;
/* 28 */       if (s.get(i).getPeticiones().isEmpty()) {
/* 29 */         System.out.println("-> Sin Peticiones <-");
/*    */       }
/* 31 */       for (Integer peticion : s.get(i).getPeticiones()) {
/* 32 */         System.out.println("Peticion " + j + ": Dias " + peticion);
/* 33 */         j++;
/* 34 */         histo[peticion.intValue()] = histo[peticion.intValue()] + 1.0D;
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     System.out.println();
/* 39 */     for (i = 0; i < 4; i++) {
/* 40 */       System.out.println(histo[i] + " de " + i + " dias");
/*    */     }
/* 42 */     System.out.println();
/*    */     
/* 44 */     for (i = 0; i < c.size(); i++)
/* 45 */       System.out.println("Centro " + i + ": " + c.get(i).getCoordX() + " " + c.get(i).getCoordY()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\lator\Documents\GitHub\IA-Gasolina\extracted gasolina jar\!\IA\Gasolina\Test.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */