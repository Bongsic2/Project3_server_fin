package orderCock_Server_DTO;

import java.util.Date;

public class Stamp {
   Date date;

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   @Override
   public String toString() {
      return "Stamp [date=" + date + "]";
   }
   
}