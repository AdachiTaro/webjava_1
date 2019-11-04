package jp.co.systena.tigerscave.springtest.model.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart {
  private List<Order> orderList = new ArrayList<Order>();
  private int totalAmount;

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

  public int getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Map itemList) {
    if (orderList != null) {
      totalAmount = 0;
      for (Order allOrder : orderList) {
        // allOrderから商品番号を取得
        // itemListでその商品番号の値段を取得
        // allOrderの数量とその値段をかけた結果を代入
        int itemId = allOrder.getItemId();
        Item item = (Item) itemList.get(Integer.toString(itemId));
        int price = item.getPrice();

        totalAmount += allOrder.getNum() * price;
      }
    }
  }

  public void deleteOrder(int itemId) {
    for (int i = 0; i < orderList.size(); i++) {
      if (orderList.get(i).getItemId() == itemId) {
        orderList.remove(i);
      }
    }
  }
}
