package jp.co.systena.tigerscave.springtest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ShoppingController {

  @GetMapping("/itemlist")
  public String list(Model model) {
    ListService listService = new ListService();

    // https://qiita.com/opengl-8080/items/eb3bf3b5301bae398cc2
    // https://qiita.com/opengl-8080/items/05d9490d6f0544e2351a
    // https://qiita.com/alpha_pz/items/d6009a61bd03e2d0769c
    //http://eclipse455.rssing.com/chan-12342211/all_p4.html

    model.addAttribute("map", listService.getItemList());
    return "ListView";
  }

  @RequestMapping(value="/order", method = RequestMethod.POST)
  public String order(@ModelAttribute ListForm listForm, Model model) {
//    listForm.getItemId();
//    model.addAttribute("form",listForm);
    return "order";
  }
}
