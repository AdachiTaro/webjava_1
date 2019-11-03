package jp.co.systena.tigerscave.springtest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.springtest.model.display.Cart;
import jp.co.systena.tigerscave.springtest.model.display.Item;
import jp.co.systena.tigerscave.springtest.model.display.Order;
import jp.co.systena.tigerscave.springtest.model.form.ListForm;
import jp.co.systena.tigerscave.springtest.service.ListService;

@Controller
public class ShoppingController {
  ListService mListService = new ListService();
  Map mItemList = null;
  Cart mCart = new Cart();

  @Autowired
  HttpSession session;
  // https://www.memory-lovers.blog/entry/2018/02/04/171013
  // http://m-shige1979.hatenablog.com/entry/2016/11/30/080000

  @GetMapping("/itemlist")
  public String list(Model model) {

    // https://qiita.com/opengl-8080/items/eb3bf3b5301bae398cc2
    // https://qiita.com/opengl-8080/items/05d9490d6f0544e2351a
    // https://qiita.com/alpha_pz/items/d6009a61bd03e2d0769c
    // http://eclipse455.rssing.com/chan-12342211/all_p4.html
    if (mListService != null) {
      mItemList = mListService.getItemList();
      model.addAttribute("map", mItemList);
    }
    return "ListView";
  }

  @RequestMapping(value = "/order", method = RequestMethod.POST)
  public ModelAndView order(HttpSession session, ModelAndView mav, ListForm listForm) {
    // 押された商品のID
    int itemId = listForm.getItemId();
    int itemQuantity = listForm.getNum();

    // 商品IDからOrderオブジェクト生成
    Order order = new Order();
    order.setItemId(itemId);
    order.setNum(itemQuantity);

    // CartのorderListにOrderオブジェクトをadd
    mCart.addOrder(order);

    // セッションにカートの情報を保存
    session.setAttribute("cartList", mCart);

    // セッションからカート情報を取得
    Object cartList = session.getAttribute("cartList");

    // 注文番号の羅列
    List<Order> orderList = ((Cart) cartList).getOrderList();

    // 注文商品一覧
    List<Item> orderItemList = new ArrayList<>();

    // 注文番号と一致する商品を注文商品一覧に格納
    for (int i = 0; i < orderList.size(); i++) {
      int orderId = orderList.get(i).getItemId();
      Item cartItem = (Item) mItemList.get(Integer.toString(orderId));
      orderItemList.add(cartItem);
    }

    mav.addObject("CartItem", orderItemList);

    mav.setViewName("cartView");

    return mav;
  }
}
