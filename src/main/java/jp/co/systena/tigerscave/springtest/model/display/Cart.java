package jp.co.systena.tigerscave.springtest.model.display;

import java.util.ArrayList;
import java.util.List;

public class Cart {
  private List<Order> orderList = new ArrayList<Order>();;

  public void addOrder(Order o) {

    // 保持しているorderListを横断検索してすでにある注文なら注文数を増やすように対応
    boolean isAlreadyOrder = false;
    if (this.orderList != null) {
      for (Order alreadyOrder : this.orderList) {
        if (alreadyOrder.getItemId() == o.getItemId()) {
          alreadyOrder.setNum(o.getNum());
          isAlreadyOrder = true;
        }
      }
    }

    if (!isAlreadyOrder) {
      this.orderList.add(o);
    }
  }

  public List<Order> getOrderList() {
    return this.orderList;
  }
}
