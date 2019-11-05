package jp.co.systena.tigerscave.springtest.controller;

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
import jp.co.systena.tigerscave.springtest.model.display.Order;
import jp.co.systena.tigerscave.springtest.model.form.DeleteForm;
import jp.co.systena.tigerscave.springtest.model.form.ListForm;
import jp.co.systena.tigerscave.springtest.service.ListService;

@Controller
public class ShoppingController {
  ListService mListService = new ListService();
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
      Map itemList = mListService.getItemList();
      model.addAttribute("map", itemList);
    }
    return "ListView";
  }

  @RequestMapping(value = "/order", method = RequestMethod.POST)
  public ModelAndView order(HttpSession session, ModelAndView mav, ListForm listForm) {
    // 押された商品のID
    int itemId = listForm.getItemId();
    int itemQuantity = listForm.getNum();
    Map itemList = null;
    if (mListService != null) {
      itemList = mListService.getItemList();
    }

    // 商品IDからOrderオブジェクト生成
    Order order = new Order();
    order.setItemId(itemId);
    order.setNum(itemQuantity);

    // CartのorderListにOrderオブジェクトをadd
    mCart.addOrder(order);

    // Cartで合計金額を設定
    mCart.setTotalAmount(itemList);

    // セッションにカートの情報を保存
    session.setAttribute("cartList", mCart);

    // セッションからカート情報を取得
    Cart cart = (Cart) session.getAttribute("cartList");

    mav.addObject("itemList", itemList);
    mav.addObject("cart", cart);

    // テンプレート名を設定
    mav.setViewName("cartView");

    return mav;
  }

  @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
  public ModelAndView deleteOrder(HttpSession session, ModelAndView mav, DeleteForm deleteForm) {
    // 押された商品のID
    int itemId = deleteForm.getItemId();
    Map itemList = null;
    if (mListService != null) {
      itemList = mListService.getItemList();
    }
    Cart cart = (Cart) session.getAttribute("cartList");
    cart.deleteOrder(itemId);
    cart.setTotalAmount(itemList);

    // カート画面を再生成しているため再表示とは違う気がしている
    mav.addObject("itemList", itemList);
    mav.addObject("cart", cart);
    mav.setViewName("cartView");

    return mav;
  }
}
