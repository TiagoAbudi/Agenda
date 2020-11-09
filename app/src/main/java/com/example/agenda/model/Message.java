package com.example.agenda.model;

public class Message {

   private String text;
   private long timestap;
   private String fromId;
   private String toId;

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public long getTimestap() {
      return timestap;
   }

   public void setTimestap(long timestap) {
      this.timestap = timestap;
   }

   public String getFromId() {
      return fromId;
   }

   public void setFromId(String fromId) {
      this.fromId = fromId;
   }

   public String getToId() {
      return toId;
   }

   public void setToId(String toId) {
      this.toId = toId;
   }
}
