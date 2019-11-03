package jp.co.systena.tigerscave.springtest.model.display;

import java.util.ArrayList;
import java.util.List;

public class Cart {
  private List<Order> orderList = new ArrayList<Order>();;

  public void addOrder(Order o) {
    this.orderList.add(o);
  }

  public List<Order> getOrderList() {
    return this.orderList;
  }
}
