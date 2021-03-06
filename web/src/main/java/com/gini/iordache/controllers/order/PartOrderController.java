package com.gini.iordache.controllers.order;

import com.gini.errors.order.BadIntegerNumberException;
import com.gini.errors.order.PartNotFoundException;
import com.gini.errors.order.SelectOrderException;
import com.gini.errors.order.SelectPartException;
import com.gini.iordache.cache.MiniCache;
import com.gini.iordache.entity.auto.Part;
import com.gini.iordache.entity.order.PartOrder;
import com.gini.iordache.entity.order.ServiceOrder;
import com.gini.iordache.services.interfaces.PartOrderService;
import com.gini.iordache.services.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/orderPart")
public class PartOrderController {

    private final PartOrderService partOrderService;
    private final OrderService serviceOrderService;
    private final MiniCache miniCache;




    @GetMapping("/addPart-page")
    public String addPartsToServiceOrder(Model model){

        if(miniCache.getCompleteServiceOrder() == null){
            throw new SelectOrderException("No order selected!");
        }

        int id = miniCache.getCompleteServiceOrder().getId();

        List<PartOrder> partServiceOrders = serviceOrderService.getPartsFormServiceOrder(id);

        Optional.ofNullable(miniCache.retrievePart())
                                .ifPresentOrElse
                                        (part -> model.addAttribute("part", part),
                                           () -> model.addAttribute("part", new Part()));


        Optional.ofNullable(miniCache.getCompleteServiceOrder())
                                .ifPresentOrElse(order -> model.addAttribute("order",order),  //pt javascript
                                                    () -> model.addAttribute("order",new ServiceOrder()));


        model.addAttribute("serviceOrderParts",partServiceOrders);


        return "order/partOrder-page";
    }


    @GetMapping("/findPart")
    public String findPart(HttpServletRequest request, Model model){

        var partNumber = request.getParameter("partNumber");
        Part part;

        try{
            part = miniCache.findPartByPartNumber(partNumber); //prind si rearunc exceptia ca sa resetez instanta de 'part'
        }catch(PartNotFoundException e){                         //ce face sa se goleasca field-urile din partOrder.html si
        e.printStackTrace();                                     //sa imi apara si 'part not found'

            throw new PartNotFoundException();
        }

        model.addAttribute("part", part);
        return "redirect:/orderPart/addPart-page";
    }


    @PostMapping("/addPartToOrder")
    public String addPartToOrder(HttpServletRequest request){

        if(miniCache.retrievePart() == null){
            throw new SelectPartException("No part was selected");
        }

        try{

            var count = Integer.parseInt(request.getParameter("count"));
            ServiceOrder serviceOrder = miniCache.getCompleteServiceOrder();
            partOrderService.addPartToServiceOrder(miniCache.retrievePart(), serviceOrder, count);

        }catch(NumberFormatException e){
            e.printStackTrace();
            throw new BadIntegerNumberException("The value is not an integer");

        }

        String partNumber = miniCache.retrievePart().getPartNumber();
        miniCache.findPartByPartNumber(partNumber); // -> sa imi scada si count-ul in pagina html de la piesa din magazie

        return "redirect:/orderPart/addPart-page";
    }


    @GetMapping("/deletePart")
    public String deletePartFromServiceOrder(@RequestParam("partNumber") String partNumber, @RequestParam("count") int count){


        partOrderService.deletePartFromServiceOrder(partNumber, count, miniCache.getCompleteServiceOrder());
        miniCache.findPartByPartNumber(partNumber); // -> sa imi creasca si count-ul in pagina html de la piesa din magazie
        return "redirect:/orderPart/addPart-page";
    }

}
