package com.gini.iordache.controllers.home;

import com.gini.errors.order.SelectOrderException;
import com.gini.iordache.cache.MiniCache;
import com.gini.iordache.cache.MiniCacheImpl;
import com.gini.iordache.entity.order.ServiceOrder;
import com.gini.iordache.services.interfaces.InvoiceService;
import com.gini.iordache.services.interfaces.OrderService;

import com.gini.iordache.utility.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/app")
public class HomeController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final MiniCache miniCache;


    @GetMapping("/main")
    public String showHomePage(Model model){

        if(miniCache.getCompleteServiceOrder() == null){
            allModelAtributes(new ServiceOrder(), model);
        }else{

            allModelAtributes(miniCache.getCompleteServiceOrder(), model);
        }

        model.addAttribute("serviceOrderIdAndStatus", orderService.allServiceOrderIdAndStatus());

        return "home/home-page";
    }


    @GetMapping("/order-stats")  //method 1
    public String findOrderStats(@RequestParam("orderId") int id, Model model){

        ServiceOrder serviceOrder = miniCache.loadCompleteServiceOrderById(id);
        allModelAtributes(serviceOrder,model);

        return "redirect:/app/main";
    }


    @PostMapping("/closeOrder")
    public String closeOrder(){

        var username = SecurityContextHolder.getContext().getAuthentication().getName();


        if(miniCache.getCompleteServiceOrder() == null){
            throw  new SelectOrderException("No order selected");
        }


        orderService.closeOrder(miniCache.getCompleteServiceOrder());
        miniCache.getCompleteServiceOrder().setOrderStatus(OrderStatus.CLOSE); //todo: nu e bine asta aici! trebuie bagata in service ca sa se faca pe tranzactie

        return "redirect:/app/main";
    }


    @GetMapping("/invoice")
    public String getInvoice(){

        if(miniCache.getCompleteServiceOrder() == null){
            throw  new SelectOrderException("No order selected");
        }

        invoiceService.getInvoiceFromDataBase(miniCache.getCompleteServiceOrder());
        return "redirect:/app/main";
    }




    private void allModelAtributes(ServiceOrder serviceOrder, Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("laborsOrder", serviceOrder.getLabors());
        model.addAttribute("partsOrder", serviceOrder.getParts());
        model.addAttribute("serviceOrder", serviceOrder);

        model.addAttribute("partsTotalPrice", serviceOrder.getPartsTotalPrice());
        model.addAttribute("partsTotalPriceWithVAT", serviceOrder.getPartsTotalPriceVAT());

        model.addAttribute("laborTotalPrice", serviceOrder.getLaborTotalPrice());
        model.addAttribute("laborTotalPriceWithVAT", serviceOrder.getLaborTotalPriceVAT());

        model.addAttribute("totalPrice", serviceOrder.getTotalPrice());
        model.addAttribute("totalPriceWithVAT", serviceOrder.getTotalPriceVAT());

        model.addAttribute("username", username);
    }

}
