package jp.co.systena.tigerscave.springtest.model.display;

public class Order {
  private int itemId;
  private int num;

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num += num;
  }

}
