package jp.co.systena.tigerscave.springtest.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.springtest.model.display.Item;
import jp.co.systena.tigerscave.springtest.model.form.ListForm;
import jp.co.systena.tigerscave.springtest.service.ListService;

@Controller
public class ShoppingController {
  ListService mListService = new ListService();
  Map mItemList = null;

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
  public ModelAndView order(ModelAndView mav, ListForm listForm) {
    int itemId = listForm.getItemId();

    for (Object key : mItemList.keySet()) {
      Item cartItem = (Item) mItemList.get(key);
      if (itemId == cartItem.getItemId()) {
        mav.addObject("CartItem", cartItem);
        break;
      }
    }

    mav.setViewName("cartView");

    return mav;
  }
}
