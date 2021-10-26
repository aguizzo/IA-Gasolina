/*    */ //package IA.Gasolina;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class Gasolinera
/*    */ {
/*    */   private ArrayList<Integer> Peticiones;
/*    */   private int CoordX;
/*    */   private int CoordY;
/*    */   
/*    */   public Gasolinera(int cx, int cy, ArrayList<Integer> peticiones) {
/* 28 */     this.Peticiones = peticiones;
/* 29 */     this.CoordX = cx;
/* 30 */     this.CoordY = cy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCoordX() {
/* 39 */     return this.CoordX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCoordX(int CoordX) {
/* 48 */     this.CoordX = CoordX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCoordY() {
/* 57 */     return this.CoordY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCoordY(int CoordY) {
/* 66 */     this.CoordY = CoordY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayList<Integer> getPeticiones() {
/* 75 */     return this.Peticiones;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPeticiones(ArrayList<Integer> peticiones) {
/* 84 */     this.Peticiones = peticiones;
/*    */   }
/*    */ }


/* Location:              C:\Users\lator\Documents\GitHub\IA-Gasolina\extracted gasolina jar\!\IA\Gasolina\Gasolinera.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */